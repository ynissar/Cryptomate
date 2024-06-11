package cryptoTrader.tradingManagement.broker;

import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import cryptoTrader.tradingManagement.strategy.Strategy;
import cryptoTrader.tradingManagement.trading.Trade;

import java.util.List;
import java.util.Map;

/**
 * This interface is implemented by the BrokerImpl class. Each broker has an ID,
 * name,
 * coins, strategies, and trade history.
 * 
 * @author Group 14
 */
public interface Broker {

    // Adds a Trade to the Broker's Trade History
    public void addTrade(Trade trade);

    // Returns the Broker's id
    public int getID();

    // Returns the broker's name
    public String getName();

    // Returns the CoinMap of the broker
    // where the key is the iteration and the value is the crypto coin
    public Map<Integer, List<Cryptocoin>> getCoinMap();

    // Returns the StrategiesMap of the broker
    // where the key is the iteration and the value is the strategy
    public Map<Integer, Strategy> getStrategiesMap();

    // Adds a new Entry into the broker's StrategyMap and CoinMap
    public void updateStrategyAndCoin(Strategy strategy, List<Cryptocoin> coinList, int iteration);

    // Returns the broker's trade History
    public List<Trade> getBrokerTradeHistory();

}
