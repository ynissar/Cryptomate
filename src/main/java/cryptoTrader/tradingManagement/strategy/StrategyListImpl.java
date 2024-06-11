package cryptoTrader.tradingManagement.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the StrategyList interface. Each StrategyListImpl has
 * an instance
 * and a list of strategies
 * 
 * @author Group 14
 */
public class StrategyListImpl implements StrategyList {

    // singleton
    private static StrategyListImpl instance;

    // list of strategies
    List<Strategy> strategyList;

    /**
     * Since this uses the singleton design stattern, there is only one instance
     * allowed of
     * StrategyListImpl. This method checks if the instance is null, and if so,
     * creates
     * a
     * new object of StrategyListImpl.
     * 
     * @return the instance of StrategyListImpl
     */
    public static StrategyListImpl getInstance() {
        if (instance == null)
            instance = new StrategyListImpl();
        return instance;
    }

    // == constructor ==

    /**
     * Constructor class creates a new strategy list of type ArrayList and adds the
     * instance of each of the different types of strategies to this strategyList
     * using the factory method through the creator class.
     */
    private StrategyListImpl() {
        strategyList = new ArrayList<>();

        Strategy strategyA = Creater.factoryMethod("A");
        Strategy strategyB = Creater.factoryMethod("B");
        Strategy strategyC = Creater.factoryMethod("C");
        Strategy strategyD = Creater.factoryMethod("D");
        Strategy strategyE = Creater.factoryMethod("E");

        // adding all strategies
        strategyList.add(strategyA);
        strategyList.add(strategyB);
        strategyList.add(strategyC);
        strategyList.add(strategyD);
        strategyList.add(strategyE);
    }

    /**
     * Getter method returns the strategy list
     * 
     * @return strategy list
     */
    @Override
    public List<Strategy> getStrategyList() {
        return strategyList;
    }

}
