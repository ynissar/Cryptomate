package cryptoTrader.tradingManagement.strategy;

import java.util.List;

/**
 * This interface is implemented by the StrategyListImpl classes.
 * 
 * @author Group 14
 */
public interface StrategyList {

    // returns the list of Strategies
    public List<Strategy> getStrategyList();

}
