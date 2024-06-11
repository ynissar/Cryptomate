# Trading Broker Project

This is the completed implementation of the Trading Broker Project.

## Strategies

- Strategy A: If the price of coin 1 is less or equal to $50,000 and the price of coin 2 is more than $2 then buy 10 of coin 3 (3 coins required)
- Strategy B: If the price of coin 1 is greater than $3,000 then buy 100 of coin 1 (1 coin required)
- Strategy C: If the price of coin 1 is greater than $1,000 then buy 25 of coin 2 (2 coins required)
- Strategy D: If the price of coin 1 is less than $2,000 then buy 50 of coin 1 (1 coin required)
- Strategy E: If the price of coin 1 is less than $1,000 and the price of coin 2 is between $2,000 and $5,000 inclusive, then buy 100 of coin 3 (3 coins required)

## Design Patterns

- Singleton: Appears in strategy implementations, login, strategy list implementation, mainUI
- Factory Method: Appears in strategy (involves creater and strategy list)
- Strategy: Appears in broker
- Observer: Appears in userSelectionImpl (broker and coins are observing it)
- Facade: Appears in MainUI
- Proxy: Appears in CoinFetcherProxy

## Run Locally

Steps:

1. Download Zip file and extract
2. Open the project as the current working directory
3. Run login.java in src\main\java\cryptoTrader\login\login.java and use the login info from database.txt in cwd
4. Read the strategies using the "Read Strategies" button in the mainUI
5. Experiment!
