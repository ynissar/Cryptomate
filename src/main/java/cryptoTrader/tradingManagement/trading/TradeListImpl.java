package cryptoTrader.tradingManagement.trading;

import cryptoTrader.tradingManagement.broker.Broker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the TradeList class. Each Trade List has an instance
 * and a list of all the trades performed
 */
public class TradeListImpl implements TradeList {

    // list of all Trades
    private List<Trade> tradeHistory;

    // singleton
    private static TradeListImpl instance = null;

    /**
     * Since this is a singleton class, there is only one instance allowed of
     * TradeListImpl. This method checks if the instance is null, and if so, creates
     * a
     * new object of TradeListImpl.
     * 
     * @return the instance of TradeListImpl
     */
    public static TradeListImpl getInstance(List<Broker> brokerList, int iteration) {
        if (instance == null)
            instance = new TradeListImpl(brokerList, iteration);
        else
            instance.executeTradesForAllBrokers(brokerList, iteration);
        return instance;
    }

    // == constructor ==

    /**
     * Constructor class initializes the trade history list, executes the trades for
     * all the brokers in the broker list and stores all of these trades in the
     * trade history list
     * 
     * @param brokerList the list of brokers that are involved in the trades to be
     *                   executed
     * @param iteration  the iterations of the trades to be executed
     */
    private TradeListImpl(List<Broker> brokerList, int iteration) {

        // instantiates the List
        tradeHistory = new ArrayList<>();

        // execute Trades for all brokers and store in the tradeHistory list
        executeTradesForAllBrokers(brokerList, iteration);
    }

    /**
     * This method executes the trades for all brokers in a given broker list
     * 
     * @param brokerList the list of the brokers involved in the trades to be
     *                   executed
     * @param iteration  the iterations of the trades to be executed
     */
    private void executeTradesForAllBrokers(List<Broker> brokerList, int iteration) {
        Trade trade;

        for (Broker broker : brokerList) {

            // if there is a trade in this iteration
            if (broker.getStrategiesMap().get(iteration) != null) {

                // creates the trade
                trade = new TradeImpl(broker, iteration);

                // adds trade to broker's trade history
                broker.addTrade(trade);

                // adds trade to overall trade history
                tradeHistory.add(trade);
            }
        }
    }

    // == getters ==

    /**
     * Getter method retrieves the list of all past trades from all brokers
     * 
     * @return a list of all past trades
     */
    public List<Trade> getTradeHistory() {
        return tradeHistory;
    }

}
