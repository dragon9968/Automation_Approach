@database
Feature: PostgreSQL Database Practice with 3-Column Table

  Scenario: Practice CRUD operations on users_test table
    # 1. CLEAN
    Given the database is cleared of email "test_user@gmail.com"

    # 2. INSERT
    #When I insert a new user with email "test_user@gmail.com", password "pass123" and status "active"

    When I insert a new user with details:
      | email               | pass    | status |
      | test_user1@gmail.com | pass123 | active |

    # 3. SELECT
    #Then I verify user with email "test_user@gmail.com" has password "pass123" in the database

    Then I verify user exists with details:
      | email               | pass    | status |
      | test_user1@gmail.com | pass123 | active |
    # 4. UPDATE
    When I update status of user with email "test_user@gmail.com" to "inactive"
    Then I verify status of user with email "test_user@gmail.com" is "inactive" in the database

    # 5. DELETE
    #When I delete user with email "test_user@gmail.com" from the database
    #Then I verify user with email "test_user@gmail.com" no longer exists in the database