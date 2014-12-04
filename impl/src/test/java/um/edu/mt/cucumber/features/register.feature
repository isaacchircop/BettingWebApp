Feature: Register to BettingApp

Scenario: Successful Registration
Given I am a user trying to register
When I register providing correct information
Then I should be told that the registration was successful

Scenario Outline: Incorrect Registration Data
Given I am a user trying to register
When I fill in a form with correct data
And I change the <fieldname> field to have incorrect input
Then I should be told that the data in <fieldname> is incorrect


Examples:

|fieldname|
|Name|
|Surname|
|DOB|
|CreditCard|
|ExpiryDate|