package cryptoTrader.tradingManagement.cryptocoin;

/**
 * This interface is implemented by the CryptocoinImpl class. Each cryptocoin
 * has a name,
 * price and date of which price was retrieved.
 * 
 * @author Group 14
 */
public interface Cryptocoin {

    // returns the coin name
    public String getCoinName();

    // returns the coin price
    public double getPrice();

    // returns the date of when the price was retrieved
    // for the coin
    public String getDate();
}