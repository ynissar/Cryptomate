package cryptoTrader.dataResult;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.strategy.Strategy;

import java.util.Map;

/**
 * This interface is implemented by the HistoDataOrganizerImpl class.
 * 
 * @author Group 14
 */
public interface HistoDataOrganizer {

    // organizes all the successful trades for all brokers
    // and returns a Map. The Key is the broker and the Value is another Map
    // This second Map's key is a strategy, whose value is an Integer.
    // This integer represents the amount of times that a broker
    // has had successful trades with this strategy
    public Map<Broker, Map<Strategy, Integer>> organizeForHisto();

}
