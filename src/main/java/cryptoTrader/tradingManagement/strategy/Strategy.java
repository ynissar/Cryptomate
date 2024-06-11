package cryptoTrader.tradingManagement.strategy;

import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;

import java.util.List;

/**
 * This interface is implemented by the StrategyImpl classes.
 * 
 * @author Group 14
 */
public interface Strategy {

    // returns the strategy name
    public abstract String getStrategyName();

    // returns the action of the strategy
    // e.g. buy or sell
    public abstract String getAction();

    // returns the quantity of the strategy
    // eg 500 if buy 500
    public abstract int getQuantity();

    // returns the String of what the
    // strategy does
    public abstract String getConditionString();

    // invokes the strategy given a list of cryptocoins
    // will return the coin being bought/sold if it was a success
    // will return null if failure
    public abstract Cryptocoin invokeStrategy(List<Cryptocoin> cryptocoinList);

    // The reason why a trade failed, if it failed
    public abstract String reasonForFailure(List<Cryptocoin> cryptocoinList);

    // returns an Example of how to use the Strategy
    public abstract String getExample();

    // returns the number of coins required for this
    // Strategy
    public abstract int getCoinsRequired();

}
