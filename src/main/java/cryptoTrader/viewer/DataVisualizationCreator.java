package cryptoTrader.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import cryptoTrader.tradingManagement.broker.Broker;
import cryptoTrader.tradingManagement.strategy.Strategy;
import cryptoTrader.tradingManagement.strategy.StrategyListImpl;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import cryptoTrader.gui.MainUI;

/**
 * This class represents a Data Visualization Creator.
 */
public class DataVisualizationCreator {

    /**
     * This method creates charts to visualize the data on
     * 
     * @param tableColumnNames a list of the names of the columns of the tables
     * @param tableData        an array of the data in the tables
     * @param brokerMapMap     the map containing the brokers and their strategies
     * @param brokerList       the list of brokers that will need to be visualized
     */
    public void createCharts(String[] tableColumnNames, String[][] tableData,
            Map<Broker, Map<Strategy, Integer>> brokerMapMap, List<Broker> brokerList) {
        // createTextualOutput();
        createTableOutput(tableColumnNames, tableData);
        // createTimeSeries();
        // createScatter();
        createBar(brokerMapMap, brokerList);
    }

    private void createTextualOutput() {
        // DefaultTableModel dtm = new DefaultTableModel(new Object[] {"Broker Name",
        // "Ticker List", "Strategy Name"}, 1);
        // JTable table = new JTable(dtm);
        // //table.setPreferredSize(new Dimension(600, 300));
        // dtm.e
        // JScrollPane scrollPane = new JScrollPane(table);
        // scrollPane.setBorder (BorderFactory.createTitledBorder
        // (BorderFactory.createEtchedBorder (),
        // "Broker Actions",
        // TitledBorder.CENTER,
        // TitledBorder.TOP));
        //
        //
        //
        // scrollPane.setPreferredSize(new Dimension(800, 300));
        // table.setFillsViewportHeight(true);;

        // MainUI.getInstance().updateStats(scrollPane);
    }

    /**
     * This method creates table output.
     * 
     * @param tableColumnnames the names of the columns on the table
     * @param tableData        the data in the table
     */
    private void createTableOutput(String[] tableColumnnames, String[][] tableData) {

        String[] columnNames = tableColumnnames;
        String[][] data = tableData;

        JTable table = new JTable(data, columnNames);
        // table.setPreferredSize(new Dimension(600, 300));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Trader Actions",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        scrollPane.setPreferredSize(new Dimension(800, 300));
        table.setFillsViewportHeight(true);

        MainUI.getInstance().updateStats(scrollPane);
    }

    /**
     * This method creates a time series for various cryptocoins
     */
    private void createTimeSeries() {
        TimeSeries series1 = new TimeSeries("Bitcoin - Daily");
        series1.add(new Day(13, 9, 2021), 50368.67);
        series1.add(new Day(14, 9, 2021), 51552.05);
        series1.add(new Day(15, 9, 2021), 47228.30);
        series1.add(new Day(16, 9, 2021), 45263.90);
        series1.add(new Day(17, 9, 2021), 46955.41);

        TimeSeries series2 = new TimeSeries("Ethereum - Daily");
        series2.add(new Day(13, 9, 2021), 3912.28);
        series2.add(new Day(14, 9, 2021), 3927.27);
        series2.add(new Day(15, 9, 2021), 3460.48);
        series2.add(new Day(16, 9, 2021), 3486.09);
        series2.add(new Day(17, 9, 2021), 3550.29);

        TimeSeries series3 = new TimeSeries("Cardano - Daily");
        series3.add(new Day(13, 9, 2021), 2.87);
        series3.add(new Day(14, 9, 2021), 2.84);
        series3.add(new Day(15, 9, 2021), 2.41);
        series3.add(new Day(16, 9, 2021), 2.43);
        series3.add(new Day(17, 9, 2021), 2.59);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, splinerenderer1);
        DateAxis domainAxis = new DateAxis("");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new LogAxis("Price(USD)"));

        // plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        // plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
        // plot.mapDatasetToRangeAxis(2, 2);// 3rd dataset to 3rd y-axis

        JFreeChart chart = new JFreeChart("Daily Price Line Chart", new Font("Serif", Font.BOLD, 18), plot,
                true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);

        MainUI.getInstance().updateStats(chartPanel);
    }

    /**
     * This method creates a scatter plot.
     */
    private void createScatter() {
        TimeSeries series1 = new TimeSeries("Bitcoin - Daily");
        series1.add(new Day(13, 9, 2021), 50368.67);
        series1.add(new Day(14, 9, 2021), 51552.05);
        series1.add(new Day(15, 9, 2021), 47228.30);
        series1.add(new Day(16, 9, 2021), 45263.90);
        series1.add(new Day(17, 9, 2021), 46955.41);

        TimeSeries series2 = new TimeSeries("Ethereum - Daily");
        series2.add(new Day(13, 9, 2021), 3912.28);
        series2.add(new Day(14, 9, 2021), 3927.27);
        series2.add(new Day(15, 9, 2021), 3460.48);
        series2.add(new Day(16, 9, 2021), 3486.09);
        series2.add(new Day(17, 9, 2021), 3550.29);

        TimeSeries series3 = new TimeSeries("Cardano - Daily");
        series3.add(new Day(13, 9, 2021), 2.87);
        series3.add(new Day(14, 9, 2021), 2.84);
        series3.add(new Day(15, 9, 2021), 2.41);
        series3.add(new Day(16, 9, 2021), 2.43);
        series3.add(new Day(17, 9, 2021), 2.59);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, dataset);
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new LogAxis("Price(USD)"));

        // plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        // plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart("Daily Price Scatter Chart",
                new Font("Serif", Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);
        MainUI.getInstance().updateStats(chartPanel);
    }

    /**
     * This method displays data using a bar chart.
     * 
     * @param brokerMapMap the map of the broker's data to be displayed
     * @param brokerList   the brokers who's data will be displayed
     */
    private void createBar(Map<Broker, Map<Strategy, Integer>> brokerMapMap, List<Broker> brokerList) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Strategy> strategyList = StrategyListImpl.getInstance().getStrategyList();

        // initializing the data to be in the bar graph
        for (Broker broker : brokerList) {
            for (Strategy strategy : strategyList) {
                dataset.setValue(brokerMapMap.get(broker).get(strategy), broker.getName(), strategy.getStrategyName());
            }
        }

        CategoryPlot plot = new CategoryPlot();
        BarRenderer barrenderer1 = new BarRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, barrenderer1);
        CategoryAxis domainAxis = new CategoryAxis("Strategy");
        plot.setDomainAxis(domainAxis);
        LogAxis rangeAxis = new LogAxis("Actions(Buys or Sells)");
        rangeAxis.setRange(new Range(1.0, 10));
        plot.setRangeAxis(rangeAxis);

        // plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        // plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new Font("Serif", Font.BOLD, 18),
                plot,
                true);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);
        MainUI.getInstance().updateStats(chartPanel);
    }

}
