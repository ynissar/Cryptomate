package cryptoTrader.dataResult;

/**
 * This interface is implemented by the TableDataOrganizerImpl class.
 * 
 * @author Group 14
 */
public interface TableDataOrganizer {

    // formats column header for table
    public String[] columnNames();

    // formats trade data for Table
    public String[][] dataForTable();
}
