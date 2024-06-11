package cryptoTrader.tradingManagement.broker;

import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import cryptoTrader.tradingManagement.strategy.Strategy;
import cryptoTrader.tradingManagement.trading.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the Broker Interface. Each broker has an ID, name,
 * coins, strategies, and trade history.
 * 
 * @author Group 14
 */
public class BrokerImpl implements Broker {

    // static int for unique id's for each broker
    private static int idCount = 0;

    // broker id
    private int id;

    // broker name
    private String name;

    // Map with <K,V> being <iteration, List<CryptoCoin>>
    private Map<Integer, List<Cryptocoin>> coinMap;

    // Map with <K,V> being <iteration, Strategy>
    private Map<Integer, Strategy> strategiesMap;

    // List of broker's trade history
    private List<Trade> brokerTradeHistory;

    /**
     * Constructor creates a BrokerImpl object with a given name, coin list and
     * strategy.
     * 
     * @param name      name of the broker
     * @param coinList  cryptocoin list held by the broker
     * @param strategy  strategy followed by the broker
     * @param iteration the key of the hashMap which will hold this specific
     *                  broker's strategy and coin list
     */
    public BrokerImpl(String name, List<Cryptocoin> coinList, Strategy strategy, int iteration) {

        // initializes broker's id number
        this.id = idCount++;

        // initializes broker name
        this.name = name;

        // initializes broker's coinMap
        this.coinMap = new HashMap<>();
        this.coinMap.put(iteration, coinList);

        // initializes broker's strategiesMap
        this.strategiesMap = new HashMap<>();
        this.strategiesMap.put(iteration, strategy);

        // initializes broker's Trade History
        this.brokerTradeHistory = new ArrayList<>();
    }

    // == public methods ==

    /**
     * Adds a new entry into the broker's strategy map and
     * coin map based on user input.
     * 
     * @param strategy  the new strategy to be added into the broker's strategy map
     * @param coinList  the new entry to be added into the broker's coin map
     * @param iteration the key of the strategy map and coin map to which the new
     *                  entry should be added
     */
    @Override
    public void updateStrategyAndCoin(Strategy strategy, List<Cryptocoin> coinList, int iteration) {
        strategiesMap.put(iteration, strategy);
        coinMap.put(iteration, coinList);
    }

    /**
     * Adds a trade to the broker's trade history
     * 
     * @param trade the trade to be added to the broker's trade history
     */
    @Override
    public void addTrade(Trade trade) {
        brokerTradeHistory.add(trade);
    }

    // == getters ==

    /**
     * Getter method returns the broker's id
     * 
     * @return broker id
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Getter method returns the brokers name
     * 
     * @return name of broker
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter method to return the coin map of the broker
     * 
     * @return coin map, where the key is the iteration and the value is the
     *         cryptocoin
     */
    @Override
    public Map<Integer, List<Cryptocoin>> getCoinMap() {
        return coinMap;
    }

    /**
     * Getter method to return the strategies map of the broker
     * 
     * @return strategies map, where the key is the iteration and the value is the
     *         strategy
     */
    @Override
    public Map<Integer, Strategy> getStrategiesMap() {
        return strategiesMap;
    }

    /**
     * Getter method to return the broker's trade history
     * 
     * @return broker's trade history
     */
    @Override
    public List<Trade> getBrokerTradeHistory() {
        return brokerTradeHistory;
    }
}
