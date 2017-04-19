@TOOLS  @ACCEPTANCE
Feature: Verify marketing pixels on different POS.
  Owner: Oleksandr Zelenov

  Scenario Outline: Verify marketing pixels on different POS.
    Given <country> application is accessible
    Then there is <negation> pointroll pixel on index page
    Then there is <negation> triggit pixel on index page

  Examples:
    | country        | negation |
    | Ireland        | no       |
    | United Kingdom |          |

