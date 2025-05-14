Feature: Eva Pharma Assessment Test

  Scenario: Check that the number of records changes when adding and deleting admin users
    Given I log in as an Admin with username "Admin" and password "admin123"
    And I navigate to the Admin page
    When I get the current number of admin records
    And I add a new admin user with username "automatedAdmin123" and password "P@ssw0rd"
    Then the number of records should increase by 1
    When I search for the admin user "automatedAdmin123"
    And I delete the new admin user
    Then the number of records should decrease by 1