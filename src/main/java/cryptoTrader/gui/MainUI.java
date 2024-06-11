package cryptoTrader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cryptoTrader.dataResult.HistoDataOrganizerImpl;
import cryptoTrader.dataResult.TableDataOrganizer;
import cryptoTrader.dataResult.TableDataOrganizerImpl;
import cryptoTrader.selection.UserSelection;
import cryptoTrader.selection.UserSelectionImpl;
import cryptoTrader.tradingManagement.strategy.Strategy;
import cryptoTrader.tradingManagement.strategy.StrategyList;
import cryptoTrader.tradingManagement.strategy.StrategyListImpl;
import cryptoTrader.tradingManagement.trading.Trade;
import cryptoTrader.tradingManagement.trading.TradeList;
import cryptoTrader.tradingManagement.trading.TradeListImpl;
import cryptoTrader.priceFetcher.AvailableCryptoList;
import cryptoTrader.viewer.DataVisualizationCreator;

/**
 * This class is an extension of JFrame and implements the ActionListener
 * interface.
 */
public class MainUI extends JFrame implements ActionListener {
    /**
     *
     */

    private static final long serialVersionUID = 1L;

    private static MainUI instance;
    private JPanel stats, chartPanel, tablePanel;

    // Should be a reference to a separate object in actual implementation
    private List<String> selectedList;

    private JTextArea selectedTickerList;
    // private JTextArea tickerList;
    private JTextArea tickerText;
    private JTextArea BrokerText;
    private JComboBox<String> strategyList;
    private Map<String, List<String>> brokersTickers = new HashMap<>();
    // private Map<String, String> brokersStrategies = new HashMap<>();
    private List<String> selectedTickers = new ArrayList<>();
    private String selectedStrategy = "";
    private DefaultTableModel dtm;
    private JTable table;

    // counter to keep track of which time
    // "Perform Trade" button was clicked
    private int iteration = 0;

    /**
     * Since this is implementing the singleton design pattern, there is only one
     * instance allowed of
     * the MainUI. This method checks if the instance is null, and if so, creates
     * a
     * new object of MainUI.
     * 
     * @return the instance of MainUI
     */
    public static MainUI getInstance() {
        if (instance == null)
            instance = new MainUI();

        return instance;
    }

    /**
     * Constructor method creates the MainUI
     */
    public MainUI() {

        // Set window title
        super("Crypto Trading Tool");

        // Set top bar

        JPanel north = new JPanel();

        JButton trade = new JButton("Perform Trade");
        trade.setActionCommand("refresh");
        trade.addActionListener(this);

        JPanel south = new JPanel();

        south.add(trade);

        dtm = new DefaultTableModel(new Object[] { "Trading Client", "Coin List", "Strategy Name" }, 1);
        table = new JTable(dtm);
        // table.setPreferredSize(new Dimension(600, 300));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trading Client Actions",
                        TitledBorder.CENTER, TitledBorder.TOP));
        Vector<String> strategyNames = new Vector<String>();

        // allows for user to pick which strategy they want
        // by adding each strategy name to the dropdown
        strategyNames.add("None");
        StrategyList strategyList = StrategyListImpl.getInstance();

        for (Strategy strategy : strategyList.getStrategyList()) {
            strategyNames.add(strategy.getStrategyName());
        }

        TableColumn strategyColumn = table.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox(strategyNames);
        strategyColumn.setCellEditor(new DefaultCellEditor(comboBox));
        JButton addRow = new JButton("Add Row");
        JButton remRow = new JButton("Remove Row");
        addRow.setActionCommand("addTableRow");
        addRow.addActionListener(this);
        remRow.setActionCommand("remTableRow");
        remRow.addActionListener(this);

        JButton stratExplain = new JButton("Explain Strategies");
        stratExplain.setActionCommand("stratExaplain");
        stratExplain.addActionListener(this);

        scrollPane.setPreferredSize(new Dimension(800, 300));
        table.setFillsViewportHeight(true);

        JPanel east = new JPanel();
        // east.setLayout();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        // east.add(table);
        east.add(scrollPane);
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(addRow);
        buttons.add(remRow);
        buttons.add(stratExplain);
        east.add(buttons);
        // east.add(selectedTickerListLabel);
        // east.add(selectedTickersScrollPane);

        // Set charts region
        JPanel west = new JPanel();
        west.setPreferredSize(new Dimension(1250, 650));
        stats = new JPanel();
        stats.setLayout(new GridLayout(2, 2));

        west.add(stats);

        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(east, BorderLayout.EAST);
        getContentPane().add(west, BorderLayout.CENTER);
        getContentPane().add(south, BorderLayout.SOUTH);
        // getContentPane().add(west, BorderLayout.WEST);
        
    }

    /**
     * This method updates the stats using a given component
     * 
     * @param component the component being used to update the stats
     */
    public void updateStats(JComponent component) {
        stats.add(component);
        stats.revalidate();
    }

    public static void main(String[] args) {
        JFrame frame = MainUI.getInstance();
        frame.setSize(900, 600);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void resetCell(int row, int column) {
        dtm.setValueAt("", row, column);
    }

    /**
     * This method resets the table
     */
    // resets the table
    public void resetTable() {
        dtm.setRowCount(0);
        dtm.setRowCount(1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // names of brokers (to be used to ensure there are no brokers
        // with two names)
        List<String> brokerNames = new ArrayList<>();

        // to keep track of the coins from all brokers
        Set<String> allCoinNames = new HashSet<>();

        // takes the user input of interested coins as a String
        String coinListStr;

        // list of each coin name (coinListStr split)
        List<String> coinList;

        // all coins available to trade
        AvailableCryptoList availableCryptoList = AvailableCryptoList.getInstance();

        // list of all available cryptocurrencies
        List<String> availableCryptos = AvailableCryptoList.getInstance().getAvailableCryptos();

        // Map which maps brokerName(K) and their coinList(V)
        Map<String, List<String>> brokersCoinList = new HashMap<>();

        // Map which maps brokerName(K) and their strategy(V)
        Map<String, String> brokersStrategies = new HashMap<>();

        String command = e.getActionCommand();

        if ("refresh".equals(command)) {

            // looping through all rows
            for (int count = 0; count < dtm.getRowCount(); count++) {

                Object traderObject = dtm.getValueAt(count, 0);
                // if user left trader name blank
                if (traderObject == null) {
                    JOptionPane.showMessageDialog(this, "please fill in Trader name on line " + (count + 1));
                    resetCell(count, 0);
                    return;
                } else {

                    // if another broker with that name exists
                    if (brokerNames.contains(traderObject.toString())) {
                        String message = traderObject + " is already a broker";
                        JOptionPane.showMessageDialog(this, message);
                        resetCell(count, 0);
                        return;
                    }
                    // adds broker name to the list
                    else
                        brokerNames.add(traderObject.toString());
                }

                Object coinObject = dtm.getValueAt(count, 1);

                // if the user left the coin list blank
                if (coinObject == null) {
                    JOptionPane.showMessageDialog(this, "please fill in cryptocoin list on line " + (count + 1));
                    resetCell(count, 1);
                    return;
                }

                // trims coin list and adds to the hash table
                else {
                    coinListStr = coinObject.toString();

                    // breaks the coinListStr into an ArrayList for each coin
                    coinList = Arrays.asList(coinListStr.split(","));

                    // loops through each coin symbol
                    for (int i = 0; i < coinList.size(); i++) {

                        // validates that the coin symbol is a valid one
                        if (!availableCryptos.contains(coinList.get(i).trim().toLowerCase())) {
                            String message = coinList.get(i) + " is not a valid coin id on line " + (count + 1);
                            JOptionPane.showMessageDialog(this, message);
                            resetCell(count, 1);
                            return;
                        }

                        // adds the valid coin to the coinList
                        coinList.set(i, coinList.get(i).trim().toLowerCase());
                    }

                    // converts coin symbol to coin id in coinList
                    // i.e. BTC to bitcoin
                    for (int i = 0; i < coinList.size(); i++) {
                        coinList.set(i, availableCryptoList.getCryptoID(coinList.get(i)));
                    }

                    // adds all the coins to a Set
                    allCoinNames.addAll(coinList);

                    // adds the brokerName, coinList pair to the brokersCoinList Map
                    brokersCoinList.put(brokerNames.get(count), coinList);
                }

                Object strategyObject = dtm.getValueAt(count, 2);

                // if the user leaves the strategy line empty
                if (strategyObject == null) {
                    JOptionPane.showMessageDialog(this, "please fill in strategy name on line " + (count + 1));
                    resetCell(count, 2);
                    return;
                } else {
                    // adds the brokerName(K) and strategy(V) to the brokersStrategies Map
                    brokersStrategies.put(brokerNames.get(count), strategyObject.toString());
                }
            }
            stats.removeAll();

            // keeps track of the user's broker choices
            UserSelection userSelection = UserSelectionImpl.getInstance(brokerNames, brokersCoinList, brokersStrategies,
                    allCoinNames, iteration);

            // keeps track of the all trades done
            TradeList tradeList = TradeListImpl.getInstance(userSelection.getBrokersList(), iteration);

            // gets the List of all trades
            List<Trade> trades = tradeList.getTradeHistory();
            String message;

            // if a trade failed because of an incorrect coin list, then
            // output a message to the user explaining this to them
            for (Trade trade : trades) {
                if ((trade.getIteration() == iteration) && (trade.getReasonForFailure() != null)) {
                    if (!trade.getReasonForFailure().equals("")) {
                        message = "Trade by " + trade.getClient().getName() + " with strategy "
                                + trade.getStrategy().getStrategyName()
                                + " failed because of an incorrect coin list being given";
                        JOptionPane.showMessageDialog(this, message);
                    }
                }
            }

            // resets the table
            resetTable();

            // == creates the visualization of the data ==

            // organizes trade data for the histogram
            HistoDataOrganizerImpl histoDataOrganizer = new HistoDataOrganizerImpl(userSelection.getBrokersList());

            // organizes trade data for the table
            TableDataOrganizer dataOrganizer = new TableDataOrganizerImpl(tradeList);

            // creates the data renders
            DataVisualizationCreator creator = new DataVisualizationCreator();
            creator.createCharts(dataOrganizer.columnNames(), dataOrganizer.dataForTable(),
                    histoDataOrganizer.organizeForHisto(), userSelection.getBrokersList());

            // increments the counter for number of times
            // "Perform Trade" has been clicked
            iteration++;

        } else if ("addTableRow".equals(command)) {
            dtm.addRow(new String[3]);
        } else if ("remTableRow".equals(command)) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1)
                dtm.removeRow(selectedRow);
        }
        // if clicked, displays Table to user explaining what each strategy does
        else if ("stratExaplain".equals(command)) {

            // creates a new JFrame
            JFrame jFrame = new JFrame();

            // gets the List of strategies
            List<Strategy> strategies = StrategyListImpl.getInstance().getStrategyList();

            // initializing the column headers
            String[] columnHeaders = { "Name", "Description", "Coins Required", "Example" };
            int entries = columnHeaders.length;

            // initializing the 2d array for the table
            String[][] data = new String[strategies.size()][entries];
            Strategy strategy;

            // assigning the data
            for (int i = 0; i < strategies.size(); i++) {
                strategy = strategies.get(i);
                data[i][0] = strategy.getStrategyName();
                data[i][1] = strategy.getConditionString();
                data[i][2] = String.valueOf(strategy.getCoinsRequired());
                data[i][3] = strategy.getExample();
            }

            // creating a new table
            JTable jTable = new JTable(data, columnHeaders);
            jTable.setBounds(10, 300, 10, 10);

            // width for the description column
            jTable.getColumnModel().getColumn(1).setPreferredWidth(750);

            JScrollPane jScrollPane = new JScrollPane(jTable);
            jFrame.add(jScrollPane);
            jScrollPane.setSize(2000, 500);

            jFrame.setSize(1000, 400);
            jFrame.setVisible(true);

        }
    }

}
