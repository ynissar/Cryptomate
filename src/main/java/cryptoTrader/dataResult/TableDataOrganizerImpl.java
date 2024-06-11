package cryptoTrader.dataResult;

import cryptoTrader.tradingManagement.trading.Trade;
import cryptoTrader.tradingManagement.trading.TradeList;

import java.text.DecimalFormat;
import java.util.List;

/**
 * This class implements the TableDataOrganizer interface. Each object has a
 * list of all trades
 * 
 * @author Group 14
 */
public class TableDataOrganizerImpl implements TableDataOrganizer {

    private final List<Trade> allTrades;

    /**
     * Constructor method initializes the all trades variable.
     * 
     * @param tradeList the list of all trades
     */
    public TableDataOrganizerImpl(TradeList tradeList) {
        this.allTrades = tradeList.getTradeHistory();
    }

    /**
     * This method formats the column header for the table.
     */
    public String[] columnNames() {
        return new String[] { "Trader", "Strategy", "CryptoCoin", "Action", "Quantity", "Price (CAD)", "Date" };
    }

    /**
     * This method formats the data for the table
     */
    public String[][] dataForTable() {

        // format to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        List<Trade> tradeArrayList = allTrades;
        String[][] data = new String[tradeArrayList.size()][columnNames().length];
        Trade trade;

        for (int i = 0; i < tradeArrayList.size(); i++) {

            trade = tradeArrayList.get(i);

            // if the user did not give a Strategy
            if (trade.getStrategy() == null) {
                data[i][1] = "None";
                data[i][4] = "null";
                data[i][5] = "null";
            }

            // if the trade failed
            else if (trade.getAction().equals("Fail")) {
                data[i][1] = trade.getStrategy().getStrategyName();
                data[i][4] = "null";
                data[i][5] = "null";
            }

            // if the trade succeeded
            else {
                data[i][1] = trade.getStrategy().getStrategyName();
                data[i][4] = String.valueOf(trade.getQuantity());
                data[i][5] = decimalFormat.format(trade.getUnitPrice());
            }

            data[i][0] = trade.getClient().getName();
            data[i][2] = trade.getCoinTraded().getCoinName();
            data[i][3] = trade.getAction();
            data[i][6] = trade.getTimeStamp();
        }

        return data;
    }

}
