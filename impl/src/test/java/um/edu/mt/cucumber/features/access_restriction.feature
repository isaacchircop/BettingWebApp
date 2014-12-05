Feature: Access Restriction on BettingApp

Scenario: Verify Access Restriction for Guest Users
Given I am a user who has not yet logged on
When I try to access the betting screen
Then I should be refused access