package cryptoTrader.dataResult;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.strategy.Strategy;
import cryptoTrader.tradingManagement.strategy.StrategyListImpl;
import cryptoTrader.tradingManagement.trading.Trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the HistoDataOrganizer interface. Each one of the
 * objects of this class has a brokerList
 * 
 * @author Group 14
 */
public class HistoDataOrganizerImpl implements HistoDataOrganizer {

    // list of brokers
    List<Broker> brokerList;

    // == constructor ==

    /**
     * Constructor method initializes the broker list
     * 
     * @param brokerList the list of brokers
     */
    public HistoDataOrganizerImpl(List<Broker> brokerList) {
        this.brokerList = brokerList;
    }

    /**
     * This method organizes all of the successful trades for all the brokers and
     * returns a map, where the key is the broker and the value is another map. The
     * second map's key is a strategy and value is an integer representing the
     * number of times a broker has had successful trades with this strategy
     * 
     * @return the map of maps
     */
    public Map<Broker, Map<Strategy, Integer>> organizeForHisto() {

        // retrieving the list of strategies
        List<Strategy> strategyList = StrategyListImpl.getInstance().getStrategyList();

        // initializing the Map of Maps to return
        Map<Broker, Map<Strategy, Integer>> brokerMapMap = new HashMap<>();

        // initialize each broker(K) in brokerMapMap with an empty HashMap (V)
        for (Broker broker : brokerList) {
            brokerMapMap.put(broker, new HashMap<>());
        }

        // The value to be added to brokerMapMap (which is another Map)
        Map<Strategy, Integer> brokerStrategy;

        // initialize each V in brokerMapMap with another Map
        // whose key is a strategy and value is an Integer
        // representing the amount of times that broker has
        // had successful trades with that strategy
        for (int i = 0; i < brokerList.size(); i++) {
            brokerStrategy = new HashMap<>();
            for (Strategy strategy : strategyList) {
                brokerStrategy.put(strategy, 0);
            }
            brokerMapMap.put(brokerList.get(i), brokerStrategy);
        }

        // list of each broker's trade history
        List<Trade> brokerTradeHistory;

        // creating an entry for each broker in brokerMapMap where
        // the broker is the key
        for (Broker broker : brokerList) {

            // retrieving the broker's trade history
            brokerTradeHistory = broker.getBrokerTradeHistory();

            // looping through each broker's trade
            for (int i = 0; i < brokerTradeHistory.size(); i++) {

                Trade currentTrade = brokerTradeHistory.get(i);

                // does not include failed trades in the bar graph
                if (currentTrade.getAction().equals("Fail"))
                    continue;

                // retrieves the Strategy that the broker is using in this trade
                Strategy currentStrategy = strategyList.stream()
                        .filter(strategy -> currentTrade.getStrategy().getStrategyName()
                                .equals(strategy.getStrategyName()))
                        .findAny()
                        .orElse(null);

                // if the strategy exists (i.e. this trade does not have a "None" strategy)
                if (currentStrategy != null) {

                    // incrementing the counter for this broker's strategy use
                    Map<Strategy, Integer> brokerStrategyOccurrences = brokerMapMap.get(broker);
                    brokerStrategyOccurrences.put(currentStrategy, brokerStrategyOccurrences.get(currentStrategy) + 1);
                }

            }
        }

        // returning the Map of Maps
        return brokerMapMap;
    }

}
