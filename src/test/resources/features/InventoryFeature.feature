Feature: Add the ingredients to the coffee maker inventory
  For the initial ingredients users will only have 15 pers ingredients

  Background:
    Given coffee maker is on

  Scenario: add coffee to the inventory
    When add 20 coffee
    Then Now inventory has 35 coffee

  Scenario: add milk to the inventory
    When add 10 milk
    Then Now inventory has 25 milk

  Scenario: add chocolate to the inventory
    When add 20 chocolate
    Then Now inventory has 35 chocolate

  Scenario: add sugar to the inventory
    When add 10 sugar
    Then Now inventory has 25 sugar