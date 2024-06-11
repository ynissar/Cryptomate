package cryptoTrader.tradingManagement.strategy;

import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import java.util.List;

/**
 * This class implements the Strategy interface. Each strategy has an instance,
 * a name,
 * an explanation, an action, a quantity, a limit of the conditions for the
 * coins associated
 * with the strategy, the correct length of the coins that should be passed and
 * an
 * example of how to use the strategy.
 * 
 * @author Group 14
 */
// Singleton
class StrategyImplA implements Strategy {

    // singleton
    private static StrategyImplA instance;

    // name of the strategy
    private final String strategyName;

    // strategy explained
    private final String conditionString;

    // action of the strategy
    private final String action;

    // quantity of the strategy
    private final int quantity;

    // limit of condition for coin1 and coin2
    private final double coin1PriceLimit;
    private final double coin2PriceLimit;

    // the correct length of coins
    // that should be passed
    private final int coinListSize;

    // example of how to use the strategy
    private final String example;

    /**
     * Since this is a singleton strategy, there is only one instance allowed of
     * StrategyImpl. This method checks if the instance is null, and if so, creates
     * a
     * new object of StrategyImplA.
     * 
     * @return the instance of StrategyImplA
     */
    public static StrategyImplA getInstance() {
        if (instance == null)
            instance = new StrategyImplA();
        return instance;
    }

    // == constructor ==

    /**
     * Constructor class initializes the instance variables.
     */
    private StrategyImplA() {

        // initialize strategy name
        strategyName = "Strategy-A";

        // initialize strategy condition
        conditionString = "If the price of coin1 is less or equal than $50,000 " +
                "and the price of coin2 is more than $2 then" +
                " buy 10 coin3 coins";

        // initialize strategy action
        action = "Buy";

        // initialize strategy quantity
        quantity = 10;

        // initializing limits for the coins
        coin1PriceLimit = 50000;
        coin2PriceLimit = 2;

        // size the coin list has to be
        coinListSize = 3;

        // example of how the user should input it
        example = "eth, btc, btc";
    }

    /**
     * Method invokes the strategy given a list of cryptocoins.
     * 
     * @return coin being bought or sold if the strategy was a success
     * @return null if the strategy failed
     */
    public Cryptocoin invokeStrategy(List<Cryptocoin> cryptocoinList) {

        // needs 3 coins
        if (cryptocoinList.size() != coinListSize)
            return null;

        Cryptocoin coin1 = cryptocoinList.get(0);
        Cryptocoin coin2 = cryptocoinList.get(1);
        Cryptocoin coin3 = cryptocoinList.get(2);

        if ((coin1.getPrice() <= coin1PriceLimit) && (coin2.getPrice() > coin2PriceLimit))
            return coin3;

        return null;
    }

    /**
     * Method checks for the reason for failure. However, the use of this method
     * does not guarantee that the strategy failed.
     * 
     * @return 'IncorrectList' if the reason for failure is because of an incorrect
     *         coin list
     * @return an empty string if the reason for failure is because of something
     *         other than an incorrect coin list
     */
    public String reasonForFailure(List<Cryptocoin> cryptocoinList) {
        if (cryptocoinList.size() != coinListSize)
            return "IncorrectList";

        return "";
    }

    // == getters ==

    /**
     * Getter method returns the name of the strategy.
     * 
     * @return name of the strategy
     */
    @Override
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * Getter method returns the action of the strategy.
     * 
     * @return action of the strategy (buy or sell)
     */
    @Override
    public String getAction() {
        return action;
    }

    /**
     * Getter method returns the quantity of the strategy.
     * 
     * @return quantity of the strategy (eg if the strategy is buy 500, it will
     *         return 500)
     */
    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Getter method returns the condition string of the strategy.
     * 
     * @return condition string of the strategy explaining what it does
     */
    @Override
    public String getConditionString() {
        return conditionString;
    }

    /**
     * Getter method returns an example of how to use the strategy.
     * 
     * @return example of the strategy
     */
    @Override
    public String getExample() {
        return example;
    }

    /**
     * Getter method returns the number of coins required for this strategy.
     * 
     * @return number of coins required for the strategy
     */
    @Override
    public int getCoinsRequired() {
        return coinListSize;
    }

}
