package cryptoTrader.tradingManagement.trading;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import cryptoTrader.tradingManagement.strategy.Strategy;

/**
 * This interface is implemented by the TradeImpl class.
 * 
 * @author Group 14
 */
public interface Trade {

    // returns the broker attempting the trade
    public Broker getClient();

    // returns the strategy the broker is using
    // for this trade
    public Strategy getStrategy();

    // returns the coin attempting to be
    // bought/sold from the broker
    public Cryptocoin getCoinTraded();

    // returns the action of the trade
    // i.e. buy or sell
    public String getAction();

    // returns the quantity being bought/sold
    // from this trade
    public Integer getQuantity();

    // returns the price the coin was
    // bought/sold for
    public Double getUnitPrice();

    // returns the date the coin was
    // bought/sold for
    public String getTimeStamp();

    // returns the iteration
    // of when the trade was completed
    public int getIteration();

    // returns the reason for failure of
    // the trade
    public String getReasonForFailure();

}
