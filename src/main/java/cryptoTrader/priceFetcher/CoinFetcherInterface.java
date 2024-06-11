package cryptoTrader.priceFetcher;

/**
 * This is an interface implemented by coin fetcher proxy.
 */
public interface CoinFetcherInterface {
    
    public double getPriceForCoin(String id, String date);

    public double getMarketCapForCoin(String id, String date);

    public double getVolumeForCoin(String id, String date);

}
