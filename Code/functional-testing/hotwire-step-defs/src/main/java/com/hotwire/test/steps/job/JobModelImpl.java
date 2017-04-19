/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.job;

import static com.hotwire.qa.client.db.DbWb.alias;
import static com.hotwire.qa.client.db.DbWb.and;
import static com.hotwire.qa.client.db.DbWb.between;
import static com.hotwire.qa.client.db.DbWb.cols;
import static com.hotwire.qa.client.db.DbWb.cond;
import static com.hotwire.qa.client.db.DbWb.eq;
import static com.hotwire.qa.client.db.DbWb.from;
import static com.hotwire.qa.client.db.DbWb.gt;
import static com.hotwire.qa.client.db.DbWb.in;
import static com.hotwire.qa.client.db.DbWb.is_not_null;
import static com.hotwire.qa.client.db.DbWb.lt;
import static com.hotwire.qa.client.db.DbWb.lt_eq;
import static com.hotwire.qa.client.db.DbWb.select;
import static com.hotwire.qa.client.db.DbWb.where;
import static org.fest.assertions.Assertions.assertThat;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.qa.client.db.Db;
import com.hotwire.qa.client.db.ResRow;
import com.hotwire.qa.client.db.Select;
import com.hotwire.qa.client.db.Selected;
import com.hotwire.qa.client.http.HttpWb;
import com.hotwire.qa.client.hw.HwTaskJobrun;
import com.hotwire.qa.client.hw.HwTaskSingleCommand;
import com.hotwire.qa.client.jenkins.JenkinsBuild;
import com.hotwire.qa.data.Period;
import com.hotwire.qa.data.UEnum;
import com.hotwire.qa.systems.HwRunEnv;
import com.hotwire.qa.text.Streaming;
import com.hotwire.qa.text.TextLine;
import com.hotwire.qa.text.TextLines;
import com.hotwire.qa.util.Block;
import com.hotwire.qa.util.Block.Client;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * @author abareli
 *
 */
public class JobModelImpl extends WebdriverAwareModel implements JobModel {

    private static Logger LOGGER = LoggerFactory.getLogger(JobModelImpl.class);
    private String runEnv_name;
    private HwTaskJobrun jobrun;
    private String ref;
    private List<String> jobParams;
    private String jobLogDirectory;
    private String refer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public final void runJob(List<String> params, String dir) {
        // setRunEnv_name(HwRunEnv.DEV01.name());
        LOGGER.info("runEnv_name...*** " + getRunEnv_name());
        HttpWb.disableAuth();
        String[] command = params.toArray(new String[params.size()]);
        StringBuilder str = new StringBuilder();

        if (null != command) {
            for (String s : command) {
                str.append(s).append(" ");
            }

            LOGGER.info("Trigerring job...*** " + str);
            jobrun = new HwTaskJobrun(command, dir, null, Period.minutes(20), getEnv().wc.getJenkinsSlave());
            String block5099 = getEnv() + "5099";
            Block.setEcho(block5099, System.out);
            Client blockClient = Block.get(block5099, command[0], Period.hours(1), System.out, Period.minutes(1));

            try {
                jobrun.launch();
                LOGGER.info("wait for job to finish..********" + str);
                jobrun.waitForComplete();
            }
            catch (Exception ex) {
                LOGGER.info(ex.getMessage());
            }
            finally {
                LOGGER.info("releaseBlock..********");
                blockClient.releaseBlock();
                killJob();
            }
        }
    }

    @Override
    public void runJob(String port, Integer timeout) {
        HttpWb.disableAuth();
        String[] command = jobParams.toArray(new String[jobParams.size()]);
        jobrun = new HwTaskJobrun(command,
                                  jobLogDirectory,
                                  null,
                                  Period.seconds(timeout),
                                  getEnv().wc.getJenkinsSlave(),
                                  refer);
        LOGGER.info("Trigerring Job on " + jobrun.getJenkinsBuildLink() +
            "Jenkins slave and against " + getRunEnv_name());
        String block = getEnv() + port;
        Block.setEcho(block, System.out);
        Client blockClient = Block.get(block, command[0], Period.hours(1), System.out, Period.minutes(1));
        try {
            jobrun.launch();
            jobrun.waitForComplete();
        }
        catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        finally {
            LOGGER.info("releaseBlock");
            blockClient.releaseBlock();
            killJob();
        }
    }

    @Override
    public boolean runJob(String port, String success) {
        HttpWb.disableAuth();
        String[] command;
        if (jobParams.contains("fn/AuthReversalJob")) {
            command = addDateParams().toArray(new String[jobParams.size()]);
        }
        else {
            command = jobParams.toArray(new String[jobParams.size()]);
        }
        jobrun = new HwTaskJobrun(command,
                                  jobLogDirectory,
                                  null,
                                  Period.minutes(20),
                                  getEnv().wc.getJenkinsSlave(),
                                  refer);
        LOGGER.info("Trigerring Job on " + jobrun.getJenkinsBuildLink() +
            "Jenkins slave and against " + getRunEnv_name());
        String block = getEnv() + port;
        Block.setEcho(block, System.out);
        Client blockClient = Block.get(block, command[0], Period.hours(1), System.out, Period.minutes(1));
        try {
            jobrun.launch();
            jobrun.waitForComplete();
            String[] files = jobrun.getLogFileNames();
            String filePick = files[files.length - 1];
            LOGGER.info("Looking for: " + success + " in " + filePick);
            BufferedReader reader = jobrun.getFileReader(filePick);
            TextLines lines = new TextLines(reader);
            for (TextLine line : lines) {
                if (line.line.contains(success)) {
                    LOGGER.info("\"" + success + "\" found in file \"" + filePick + "\" on line - " + line);
                    return true;
                }
            }
        }
        catch (Exception ex) {
            LOGGER.info("Job didn't run");
            LOGGER.info(ex.getMessage());
        }
        finally {
            LOGGER.info("releaseBlock");
            blockClient.releaseBlock();
            killJob();
        }
        LOGGER.info("Success message wasn't found in the joblogs");
        return false;
    }

    @Override
    public void checkJobOutput() {
        BufferedReader br;
        LOGGER.info("Check if job has output");
        if (jobrun != null) {
            br = jobrun.getResultReader();
            if (br != null) {
                LOGGER.info("Check output and log file");
                int count = 0;
                String s;
                while (count < 50) {
                    count++;
                    try {
                        s = br.readLine();
                        if (s != null) {
                            LOGGER.info(s);
                        }
                    }
                    catch (Exception e) {
                        LOGGER.info(e.getMessage());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void waitForLoadingLayer() {
        boolean loadingLayerVisible;
        By loadingImage = By.cssSelector("img[src*='hotel_search_loadingLayer.gif']");
        WebDriver wd = getWebdriverInstance();
        waitForElementVisible(wd, loadingImage, true, 20);
        waitForElementVisible(wd, loadingImage, false, 20);
        List<WebElement> elements = wd.findElements(loadingImage);
        loadingLayerVisible = elements.isEmpty();
        assertThat(loadingLayerVisible).as("loading layer was visible; shouldn't be visible").isTrue();

    }

    @Override
    public void retrieveReferenceNumber() {
        By refNumber = By.cssSelector(".customerCareRefNumber");
        WebDriver wd = getWebdriverInstance();
        waitForElementVisible(wd, refNumber, true, 20);
        HotelResultsPage resultsPage = new HotelResultsPage(wd);
        // retrieve customer care reference.
        setRef(resultsPage.getTopCustomerCareReferenceNumber().replace("Reference: 2", ""));
        LOGGER.info("Reference: " + getRef());
    }

    @Override
    public void validateTimeInDB() {
        String reference = getRef();
        if (reference == null) {
            LOGGER.info("search reference is null... skipping db validation");
        }
        else {
            Db db = getEnv().dbCfg.getDb("rpt_user", "rpt_user");
            Select sql = select(
                cols("count(*)", alias("avg((processing_end_time - crs_query_start_time)/1000)", "total_time"),
                    alias("avg((processing_start_time - crs_query_start_time)/1000)", "crs_total_time"),
                    alias("avg((processing_end_time - processing_start_time)/1000)", "processing_time")),
                from("search s", "city c"),
                where(and(between("s.SEARCH_DATE", "sysdate-15/1440", "sysdate --5 minutes"),
                    eq("s.SEARCH_ID", reference), eq("s.pcr_state", "'N'"),
                    gt("s.processing_start_time - s.crs_query_start_time", 0),
                    gt("(s.processing_end_time - s.crs_query_start_time)", 0),
                    gt("(s.processing_end_time - s.processing_start_time)", 0), is_not_null("s.processing_end_time"),
                    is_not_null("s.crs_query_start_time"), is_not_null("s.processing_start_time"),
                    lt("(s.processing_start_time - s.crs_query_start_time)/1000", 120),
                    gt("(s.processing_start_time - s.crs_query_start_time)/1000", 0), cond("s.class", in("'H'")),
                    between("trunc(s.start_date) - trunc(s.search_date)", 0, 10),
                    between("trunc(s.end_date) - trunc(s.start_date)", 1, 5), lt_eq("s.number_of_adults", 2),
                    eq("s.number_of_children", 0), eq("(s.quantity/(trunc(s.end_date) - trunc(s.start_date)))", 1),
                    eq("s.latitude", "c.latitude"), eq("s.longitude", "c.longitude"), eq("c.city_id", 24239))));

            sql.echo();
            Selected selected = db.select(sql);
            ResRow[] rows = selected.getRes(1);
            BigDecimal total_time;
            Integer comparedTime = 2;
            if (rows.length < 1) {
                LOGGER.info("No results from DB check.");
            }
            else {
                total_time = (BigDecimal) rows[0].get("total_time");
                if (total_time == null) {
                    LOGGER.info("total_time is null");
                }
                else if (total_time.signum() != 1) {
                    LOGGER.info("total time: " + total_time + " is not positive number");
                }
                else {
                    if (total_time.compareTo(BigDecimal.valueOf(comparedTime)) == -1) {
                        LOGGER.info("PASS: total time in sec: " + total_time + " is less than " + comparedTime);
                    }
                    else {
                        LOGGER.info("FAILURE: total_time: " + total_time + " is is grater than " + comparedTime);
                    }
                    //assertThat(total_time.compareTo(BigDecimal.valueOf(comparedTime)) ==
                    //    -1).as("Search TOTAL_TIME is less than 2 seconds").isTrue();
                }
            }
        }
    }

    @Override
    public void setJobParams(List<String> jobParams) {
        LOGGER.info("Setting JOB Params");
        this.jobParams = jobParams;
    }

    @Override
    public void setJobDirectory(String jobDirectory) {
        LOGGER.info("Job Directory: " + jobDirectory);
        this.jobLogDirectory = jobDirectory;
    }

    @Override
    public void setReference(String refer) {
        this.refer = refer;
    }

    @Override
    public void runShellScript(String path) {
        HttpWb.disableAuth();
        HwTaskSingleCommand commandRun = new HwTaskSingleCommand(
            new String[] {path},
            null,
            null,
            Period.minutes(10),
            getEnv().wc.getJenkinsSlave()
        );
        commandRun.run();
        Streaming.consume(commandRun.getFileReader(HwTaskSingleCommand.SINGLE_COMMAND_CONSOLE_TXT), System.out);
    }

    private void waitForElementVisible(WebDriver wd, final By by, boolean visible, long... time) {
        long timeWait = 20;
        if (time.length > 0) {
            timeWait = time[0];
        }
        try {
            new WebDriverWait(wd, timeWait).until(PageObjectUtils.webElementVisibleTestFunction(by, visible));
        }
        catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    /**
     * @param runEnv_name
     *            the runEnv_name to set
     */
    public void setRunEnv_name(String runEnv_name) {
        this.runEnv_name = runEnv_name;
    }

    public String getRunEnv_name() {
        return runEnv_name;
    }

    /**
     * This method allows you to set the environment. runEnv_name is a machine where the tests run
     */
    private HwRunEnv getEnv() {
        return UEnum.match(HwRunEnv.class, getRunEnv_name(), false);
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }



    private void killJob() {
        LOGGER.info("check if job is still running...");
        if (jobrun != null) {
            JenkinsBuild jb = jobrun.getJenkinsBuild();
            if (jb != null) {
                if (jb.isBuilding()) {
                    LOGGER.info("about to kill the job...");
                    jb.kill();
                    boolean stopped = jb.waitFor(Period.minutes(0.5), 1);
                    if (!stopped) {
                        LOGGER.info("unable to stop the job...");
                    }
                    else {
                        LOGGER.info("Killed the job..");
                    }
                }
            }
        }
    }

    private List<String> addDateParams() {
        List<String> additionalJobParams = new ArrayList<String>();
        for (String s : jobParams) {
            additionalJobParams.add(s);
        }
        additionalJobParams.add(calculateDateInFormat(0, "MM/dd/yyyy"));
        additionalJobParams.add(calculateDateInFormat(1, "MM/dd/yyyy"));
        return additionalJobParams;
    }

    private String calculateDateInFormat(Integer daysFromNow, String format) {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, daysFromNow);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        return dateFormatter.format(startDate.getTime());
    }

}
