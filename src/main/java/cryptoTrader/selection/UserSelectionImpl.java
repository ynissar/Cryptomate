package cryptoTrader.selection;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.broker.BrokerImpl;
import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import cryptoTrader.tradingManagement.cryptocoin.CryptocoinImpl;
import cryptoTrader.tradingManagement.strategy.Strategy;
import cryptoTrader.tradingManagement.strategy.StrategyList;
import cryptoTrader.tradingManagement.strategy.StrategyListImpl;
import cryptoTrader.priceFetcher.CoinFetcherProxy;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class implements the UserSelection interface. Each UserSelectionImpl
 * object has an instance, a broker list, an overall cryptocoin list, and a
 * strategy list
 * 
 * @author Group 14
 */
public class UserSelectionImpl implements UserSelection {

    // singleton
    private static UserSelectionImpl instance = null;

    // list of brokers with all their attributes
    private List<Broker> brokersList;

    // list of all the coins with their associated prices
    private List<Cryptocoin> overallCryptoList;

    // list of all the strategies
    private final StrategyList strategyList = StrategyListImpl.getInstance();

    // Singleton
    /**
     * Since this is a singleton strategy, there is only one instance allowed of
     * UserSelectionImpl. This method checks if the instance is null, and if so,
     * creates a new object of UserSelectionImpl
     * 
     * @param brokerNames       names of the brokers to be created
     * @param brokersCoinList   map which connects the broker names and the list of
     *                          interested coins
     * @param brokersStrategies map which connects broker names with their strategy
     * @param overallCryptoSet  set of cryptocoin names which are all the coins that
     *                          every broker is interested in (minimize API calls)
     * @param iteration         which strategy or cryptocoin list to use for each
     *                          broker
     * @return the instance of UserSelectionImpl
     */
    public static UserSelectionImpl getInstance(List<String> brokerNames, Map<String, List<String>> brokersCoinList,
            Map<String, String> brokersStrategies, Set<String> overallCryptoSet, int iteration) {
        if (instance == null)
            instance = new UserSelectionImpl(brokerNames, brokersCoinList, brokersStrategies, overallCryptoSet,
                    iteration);
        else {
            // updates the list of prices with the prices for today
            instance.overallCryptoList = instance.createOverallCryptoList(overallCryptoSet);

            // updates the new brokers
            instance.createBrokers(brokerNames, brokersCoinList, brokersStrategies, iteration);
        }
        return instance;
    }
    // == constructor ==

    /**
     * Constructor method initializes the overall crypto list and all the brokers
     * with their interested coins and strategies
     * 
     * @param brokerNames       names of the brokers to be created
     * @param brokersCoinList   map which connects the broker names and the list of
     *                          interested coins
     * @param brokersStrategies map which connects broker names with their strategy
     * @param overallCryptoSet  set of cryptocoin names which are all the coins that
     *                          every broker is interested in (minimize API calls)
     * @param iteration         which strategy or cryptocoin list to use for each
     *                          broker
     * @return
     */
    private UserSelectionImpl(List<String> brokerNames, Map<String, List<String>> brokersCoinList,
            Map<String, String> brokersStrategies, Set<String> overallCryptoSet, int iteration) {

        // initialize all the prices of all interested coins
        this.overallCryptoList = createOverallCryptoList(overallCryptoSet);

        // initializes all brokers with their interested coins and strategies
        createBrokers(brokerNames, brokersCoinList, brokersStrategies, iteration);
    }

    /**
     * This method creates an overall crypto list by taking in a current overall
     * crypto set, creates coins for all the coin names in the overall crypto list
     * and adds them if they aren't already in the list
     * 
     * @param overallCryptoSet the initial list of coins with their attributes
     * @return the list of all the cryptocoins
     */
    // todo
    // 1) optimize this into needing just 1 call (need to change DataFetcher class)
    private List<Cryptocoin> createOverallCryptoList(Set<String> overallCryptoSet) {

        // turning the set into an ArrayList
        List<String> overallCryptoList = new ArrayList<>(overallCryptoSet);

        // the list of all the coins with their attributes
        List<Cryptocoin> list = new ArrayList<>();

        // object used to get the prices of the coins
        CoinFetcherProxy coinDataFetcher = new CoinFetcherProxy();

        // the coin to be added
        Cryptocoin coinToAdd;

        // formats date to today's date
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = formatter.format(today);

        // create coins for all the coin names in overallCryptoList
        for (String coinName : overallCryptoList) {

            coinToAdd = new CryptocoinImpl(coinName, coinDataFetcher.getPriceForCoin(coinName, strDate), strDate);
            if (!list.contains(coinToAdd))
                list.add(coinToAdd);
        }

        return list;
    }

    /**
     * This method creates brokers given a list of their names, map of each list of
     * each of the broker's coins and map of the brokers strategy.
     * 
     * @param brokerNameList  the list of broker names
     * @param brokersCoinList the map of each list of each brokers coins
     * @param strategyMap     the map of the brokers strategy
     * @param iteration       the iteration of the trades to be performed
     */
    private void createBrokers(List<String> brokerNameList, Map<String, List<String>> brokersCoinList,
            Map<String, String> strategyMap, int iteration) {

        // broker list
        List<Broker> brokerList;

        // if the singleton is being called for the first time
        // create a new list, otherwise, use the preexisting list
        if (this.brokersList == null)
            brokerList = new ArrayList<>();
        else
            brokerList = this.brokersList;

        // broker to be added to the list
        Broker brokerToAdd;

        // brokers coin list to be added
        List<Cryptocoin> coinListToBeAdded;

        // individual coins to be added
        Cryptocoin coinToAdd;

        // creating each broker
        for (String brokerName : brokerNameList) {

            // instantiating each broker's coin list
            coinListToBeAdded = new ArrayList<>();

            // finding the coin from the overall coin list and adding
            // them to each broker's list
            for (String coinName : brokersCoinList.get(brokerName)) {
                coinToAdd = overallCryptoList.stream().filter(coin -> coinName.equals(coin.getCoinName())).findAny()
                        .orElse(null);
                coinListToBeAdded.add(coinToAdd);
            }

            // finding each broker's strategy
            Strategy strategyToAdd = strategyList.getStrategyList().stream()
                    .filter(strategy -> strategy.getStrategyName().equals(strategyMap.get(brokerName)))
                    .findAny()
                    .orElse(null);

            // if there is already an existing list (not the first call to
            // UserSelectionImpl)
            if (this.brokersList != null)
                brokerToAdd = brokersList.stream().filter(broker -> broker.getName().equals(brokerName)).findAny()
                        .orElse(null);
            // there is no existing list (first call to UserSelectionImpl)
            else
                brokerToAdd = null;

            // if the broker was already in the list
            if (brokerToAdd != null)
                brokerToAdd.updateStrategyAndCoin(strategyToAdd, coinListToBeAdded, iteration);

            // broker was not in the list and needs to be created
            else {
                brokerToAdd = new BrokerImpl(brokerName, coinListToBeAdded, strategyToAdd, iteration);
                brokerList.add(brokerToAdd);
            }
        }

        this.brokersList = brokerList;
    }

    // == getters ==

    /**
     * Getter method returns the list of brokers
     * 
     * @return brokers list
     */
    @Override
    public List<Broker> getBrokersList() {
        return brokersList;
    }

    /**
     * Getter method returns the overall crypto list
     * 
     * @return overall crypto list
     */
    @Override
    public List<Cryptocoin> getOverallCryptoList() {
        return overallCryptoList;
    }

    /**
     * Getter method returns the list of strategies
     * 
     * @return list of strategies
     */
    // returns the list of strategies
    @Override
    public StrategyList getStrategyList() {
        return strategyList;
    }

}
