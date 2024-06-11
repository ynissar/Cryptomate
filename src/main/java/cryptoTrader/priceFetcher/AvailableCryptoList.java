package cryptoTrader.priceFetcher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class represents the available cryptocoins to trade. Its attributes
 * include a hashmap of the available cryptocoins and a list containing the
 * available cryptocoins.
 */
public class AvailableCryptoList {
	private static AvailableCryptoList instance = null;

	private Map<String, String> availableCryptosMap = new HashMap<>();
	private List<String> availableCryptosList = new ArrayList<>();

	/**
	 * Since this is a singleton strategy, there is only one instance allowed of
	 * AvailableCryptoList. This method checks if the instance is null, and if so,
	 * creates a
	 * new object of AvailableCryptoList.
	 * 
	 * @return the instance of AvailableCryptoList
	 */
	public static AvailableCryptoList getInstance() {
		if (instance == null)
			instance = new AvailableCryptoList();

		return instance;
	}

	/**
	 * Constructor method calls on the private helper method find available cryptos
	 */
	private AvailableCryptoList() {
		findAvailableCryptos();
	}

	public void call() {
		String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=VNEY4VV2AWF1EB51";
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
				System.out.println(inline);
				// JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				// int size = jsonArray.size();
				//
				// String name, id;
				// for (int i = 0; i < size; i++) {
				// JsonObject object = jsonArray.get(i).getAsJsonObject();
				// name = object.get("name").getAsString();
				// id = object.get("id").getAsString();
				//
				// availableCryptosMap.put(name, id);
				// availableCryptosList.add(name);
				// }
			}

		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block e.printStackTrace();
		}
	}

	/**
	 * This method uses an API call to find all of the available cryptocoins
	 */
	private void findAvailableCryptos() {

		String urlString = "https://api.coingecko.com/api/v3/coins/markets" +
				"?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false";
		// ALPHAVANTAGE API KEY = VNEY4VV2AWF1EB51
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
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();

				String id, symbol;
				for (int i = 0; i < size; i++) {
					JsonObject object = jsonArray.get(i).getAsJsonObject();
					id = object.get("id").getAsString();
					symbol = object.get("symbol").getAsString();

					availableCryptosMap.put(symbol, id);
					availableCryptosList.add(symbol);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
	}

	/**
	 * Getter method that retrieves the available cryptocoins list
	 * 
	 * @return the list of available cryptocoins
	 */
	public List<String> getAvailableCryptos() {
		return availableCryptosList;
	}

	/**
	 * This method retrieves the ID of a specified cryptocoin
	 * 
	 * @param cryptoName the name of the cryptocoin who's ID we are looking for
	 * @return the id of the specified cryptocoin
	 */
	public String getCryptoID(String cryptoName) {
		return availableCryptosMap.get(cryptoName);
	}

}
