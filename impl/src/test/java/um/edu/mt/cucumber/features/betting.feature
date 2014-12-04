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
