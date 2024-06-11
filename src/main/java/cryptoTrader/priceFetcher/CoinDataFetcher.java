package cryptoTrader.priceFetcher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class retrieves data for a cryptocoin through the use of an API.
 */
public class CoinDataFetcher implements CoinFetcherInterface{

    /**
     * Getter method retrieves data for the specific cryptocoin through an API call
     * 
     * @param id   the coin for which to retrieve the data
     * @param date the current date
     * @return the data for the specific coin
     */
    private JsonObject getDataForCrypto(String id, String date) {
        String urlString = String.format(

                "https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);
        System.out.println(urlString);

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                String inline = "";
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
                JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
                return jsonObject;
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with the API call.");
        }
        return null;
    }

    /**
     * Getter method retrieves the price for a specific coin
     * 
     * @param id   the coin for which to retrieve the price
     * @param date the current date
     * @return the price for the coin
     */
    public double getPriceForCoin(String id, String date) {
        double price = 0.0;
        JsonObject jsonObject = getDataForCrypto(id, date);
        if (jsonObject != null) {
            JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
            JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
            price = currentPrice.get("cad").getAsDouble();
        }
        return price;
    }

    /**
     * Getter method to retrieve the market cap for a specific coin
     * 
     * @param id   the coin for which to retrieve the price
     * @param date the current date
     * @return the market cap for the coin
     */
    public double getMarketCapForCoin(String id, String date) {
        double marketCap = 0.0;
        JsonObject jsonObject = getDataForCrypto(id, date);
        if (jsonObject != null) {
            JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
            JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
            marketCap = currentPrice.get("cad").getAsDouble();
        }
        return marketCap;
    }

    /**
     * Getter method to retrieve the volume for a specific coin
     * 
     * @param id   the coin for which to retrieve the price
     * @param date the current date
     * @return the volume for a specific coin
     */
    public double getVolumeForCoin(String id, String date) {
        double volume = 0.0;
        JsonObject jsonObject = getDataForCrypto(id, date);

        if (jsonObject != null) {
            JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
            JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
            volume = currentPrice.get("cad").getAsDouble();
        }
        return volume;
    }

}