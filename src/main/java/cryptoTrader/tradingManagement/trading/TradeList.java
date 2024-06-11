package cryptoTrader.tradingManagement.trading;

import java.util.List;

/**
 * This interface is implemented by the TradeListImpl class.
 * 
 * @author Group 14
 */
public interface TradeList {

    // returns a List of Trades of every Trade from
    // all brokers
    public List<Trade> getTradeHistory();

}
