Feature: Placing Bets on BettingApp

Scenario: Successful bet on free account
Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed

Scenario: Verify Maximum bets for Free Accounts
Given I am a user with a free account
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 5 euros
Then I should be told that I have reached the maximum number of bets

Scenario: Verify access restriction for guest users
Given I am a user with a premium account
When I try to place a bet of 5000 euros
Then I should be told the bet was successfully placed
When I try to place a bet of 1 euros
Then I should be told that I have reached the maximum cumulative betting amount

Scenario Outline: Verify that free users can only place low-risk bets
Given I am a user with a free account
When I try to place a <risk-level> bet of 5 euros
Then I should be <expected-result> to bet

Examples:
|risk-level|expected-result|
|Low|Allowed|
|Medium|Not Allowed|
|High|Not Allowed|
