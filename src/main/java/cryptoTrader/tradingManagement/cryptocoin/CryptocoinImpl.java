package cryptoTrader.tradingManagement.cryptocoin;

/**
 * This class implements the Cryptocoin Interface. Each cryptocoin has a name,
 * price and
 * date of which price was retrieved
 * 
 * @author Group 14
 */
public class CryptocoinImpl implements Cryptocoin {

    // name of the coin
    private final String coinName;

    // price of the coin
    private final double price;

    // date the coin price was retrieved
    private final String date;

    /**
     * Constructor creates a CryptocoinImpl object with a name, price and date
     * 
     * @param coinName the name of the cryptocoin
     * @param price    the price of the cryptocoin
     * @param date     the date the price of the cryptocoin was accessed
     */
    public CryptocoinImpl(String coinName, double price, String date) {

        // initializes coin name
        this.coinName = coinName;

        // initializes coin price
        this.price = price;

        // initializes date
        this.date = date;
    }

    // == getters ==

    /**
     * Getter method to return the name of the cryptocoin
     * 
     * @return name of the cryptocoin
     */
    @Override
    public String getCoinName() {
        return coinName;
    }

    /**
     * Getter method to return the coin price
     * 
     * @return the price of the cryptocoin
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Getter method to return the date the price was retrieved
     * 
     * @return the date the price of the cryptocoin was retrieved
     */
    @Override
    public String getDate() {
        return date;
    }

}
