package cryptoTrader.tradingManagement.trading;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import cryptoTrader.tradingManagement.strategy.Strategy;

import java.util.List;

/**
 * This class implements the Trade interface. Each trade has a broker, a
 * strategy they are using to trade with, the coin being bought or sold in the
 * trade, the trade action, the date the trade was completed, the quantity of
 * coins being bought/sold, the price that the coins were bought/sold at,
 * the iteration of when the trade was completed, and the reason for failure if
 * the trade failed.
 * 
 * @author Group 14
 */
class TradeImpl implements Trade {

    // broker attempting to complete a trade
    private final Broker client;

    // strategy broker is using to trade with
    private final Strategy strategy;

    // coin being bought/sold in this trade
    private final Cryptocoin coinTraded;

    // trade action (i.e. buy or sell)
    private final String action;

    // date trade was completed
    private final String timeStamp;

    // quantity of coins bought/sold
    private Integer quantity;

    // price the coin was bought/sold
    private Double unitPrice;

    // the iteration of when the trade was completed
    private final int iteration;

    // if the trade failed, the reasoning for it
    // Either:
    // i) Incorrect List
    // ii) "" (empty String)
    private String reasonForFailure;

    // == constructor ==

    /**
     * Constructor method intializes the broker and the iteration of the trade.
     * 
     * @param broker    the broker involved in the trade
     * @param iteration the iteration of the trade being performed
     */
    public TradeImpl(Broker broker, int iteration) {

        // the broker attempting the trade
        client = broker;

        // the strategy for this operation for this broker
        Strategy strategy = broker.getStrategiesMap().get(iteration);

        // the coinList for this operation for this broker
        List<Cryptocoin> cryptocoinList = broker.getCoinMap().get(iteration);

        // initializing the iteration of the trade
        this.iteration = iteration;

        // if there is no strategy (user chose "None"), then
        if (strategy == null) {
            this.strategy = null;
            action = "None";

        } else {

            // broker's strategy for this trade
            this.strategy = broker.getStrategiesMap().get(iteration);

            // executing the trade
            Cryptocoin coin = executeTrade(broker, iteration);

            // if the trade failed
            if (coin == null) {
                quantity = null;
                unitPrice = null;
                action = "Fail";
                reasonForFailure = getReasonForFailure(broker, iteration);
            }
            // if trade was success
            else {
                quantity = strategy.getQuantity();
                unitPrice = coin.getPrice();
                action = strategy.getAction();
            }
        }

        // length of coinList
        int brokerCoinListLength = broker.getCoinMap().get(iteration).size();

        // the coin being traded is always the last coin in the list
        this.coinTraded = cryptocoinList.get(brokerCoinListLength - 1);

        // time stamp for one coin is the same as the time stamp for the trade
        this.timeStamp = cryptocoinList.get(0).getDate();
    }

    /**
     * Method attempts to execute the trade for a broker.
     * 
     * @param broker    the broker involved in the trade
     * @param iteration the iteration of the trade being performed
     * @return the coin traded if the trade was successful
     * @return null if the trade failed
     */
    private Cryptocoin executeTrade(Broker broker, int iteration) {

        Strategy strategy = broker.getStrategiesMap().get(iteration);

        // invokes the strategy for the broker
        return strategy.invokeStrategy(broker.getCoinMap().get(iteration));
    }

    /**
     * Returns the reason for failure if the trade has filed
     * 
     * @param broker    the broker involved in the trade
     * @param iteration the iteration of the trade being performed
     * @return the reason for failure
     */
    private String getReasonForFailure(Broker broker, int iteration) {
        Strategy strategy = broker.getStrategiesMap().get(iteration);

        return strategy.reasonForFailure(broker.getCoinMap().get(iteration));
    }

    // == getters ==

    /**
     * Getter method gets the broker attempting to perform the trade
     * 
     * @return broker attempting the trade
     */
    @Override
    public Broker getClient() {
        return client;
    }

    /**
     * Getter method gets the strategy used by the broker for this trade
     * 
     * @return the strategy being used for this trade
     */
    @Override
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * Getter method returns the coin attempting to be bought or sold from the
     * broker
     * 
     * @return the coin being bought or sold from the broker
     */
    @Override
    public Cryptocoin getCoinTraded() {
        return coinTraded;
    }

    /**
     * Getter method returns the action of the trade being performed
     * 
     * @return whether the trade involves buying or selling
     */
    @Override
    public String getAction() {
        return action;
    }

    /**
     * Getter method returns the quantity being bought or sold in the trade
     * 
     * @return quantity being bought/sold in the trade
     */
    @Override
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Getter method returns the price the coin was bought or sold for
     * 
     * @return the price the coin was bought/sold for
     */
    @Override
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Getter method gets the date the coin was bought or sold
     * 
     * @return the date the coin was bought/sold
     */
    @Override
    public String getTimeStamp() {
        return timeStamp;
    }

    // returns the iteration
    // of when the trade was completed
    @Override
    public int getIteration() {
        return this.iteration;
    }

    /**
     * Getter method returns the reason for failure of the trade if the trade fails
     * 
     * @return reason for failure of the trade
     */
    @Override
    public String getReasonForFailure() {
        return reasonForFailure;
    }

}
