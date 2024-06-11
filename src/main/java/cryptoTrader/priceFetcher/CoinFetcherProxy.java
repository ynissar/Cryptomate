package cryptoTrader.priceFetcher;

/**
 * This class implements coin fetcher interface. Each CoinFetcherProxy has a coinDataFetcher object and
 * uses the proxy design pattern
 *
 * @author Group 14
 */
public class CoinFetcherProxy implements CoinFetcherInterface{

    /**
     * coin data fetcher object
     */
    private CoinDataFetcher coinDataFetcher;

    /**
     * This method creates a new coinDataFetcher if the instance variable is null, and retrieves the
     * price for a coin given its ID and date.
     * @param id the id for the coin we want the price of
     * @param date the current date
     * @return the price of the coin
     */
    @Override
    public double getPriceForCoin(String id, String date) {
        if (coinDataFetcher == null) {
            coinDataFetcher = new CoinDataFetcher();
        }

        return coinDataFetcher.getPriceForCoin(id, date);

        
    }

    /**
     * This method creates a new coinDataFetcher if the instance variable is null, and retrieves the
     * market cap for a coin given its ID and date.
     * @param id the id for the coin we want the market cap of
     * @param date the current date
     * @return the market of the coin
     */
    @Override
    public double getMarketCapForCoin(String id, String date) {
        if (coinDataFetcher == null) {
            coinDataFetcher = new CoinDataFetcher();
        }


        return coinDataFetcher.getMarketCapForCoin(id, date);
    }

    /**
     * This method creates a new coinDataFetcher if the instance variable is null, and retrieves the
     * volume for a coin given its ID and date.
     * @param id the id for the coin we want the price of
     * @param date the current date
     * @return the volume of the coin
     */
    @Override
    public double getVolumeForCoin(String id, String date) {
        if (coinDataFetcher == null) {
            coinDataFetcher = new CoinDataFetcher();
        }


        return coinDataFetcher.getVolumeForCoin(id, date);
    }
    
}
