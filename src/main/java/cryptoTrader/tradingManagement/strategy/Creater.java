package cryptoTrader.tradingManagement.strategy;

/**
 * This class uses the Factory method and is called by StrategyListImpl to check the instance
 * of each strategy being used by the broker
 */
public class Creater {

    /**
     * This class checks each possible strategy for their instance
     * @param strategyType the strategy type being checked
     * @return the strategy object
     */
    public static Strategy factoryMethod(String strategyType) {

        Strategy strategy;

        switch (strategyType) {
            case "A":
                strategy = StrategyImplA.getInstance();
                break;
            case "B":
                strategy = StrategyImplB.getInstance();
                break;
            case "C":
                strategy = StrategyImplC.getInstance();
                break;
            case "D":
                strategy = StrategyImplD.getInstance();
                break;
            case "E":
                strategy = StrategyImplE.getInstance();
                break;
        
            default:
                strategy = null;
                break;
        }

        return strategy;
    };
}
