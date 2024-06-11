package cryptoTrader.selection;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.cryptocoin.Cryptocoin;
import cryptoTrader.tradingManagement.strategy.StrategyList;

import java.util.List;

/**
 * This interface is implemented by the user selection impl class.
 */
public interface UserSelection {

    // returns the list of brokers
    public List<Broker> getBrokersList();

    // returns the list of all coins
    public List<Cryptocoin> getOverallCryptoList();

    // returns the list of strategies
    public StrategyList getStrategyList();
}
