import java.util.Arrays;
import java.util.Random;

public class StrategyFixed implements Strategy {
    private double[] strategy;
    private Random random = new Random();

    public StrategyFixed(double[] initStrategy) {
        double strategySum = 0;
        strategy = Arrays.copyOf(initStrategy, initStrategy.length);
        for (double w : strategy) {
            if (w < 0) throw new IllegalArgumentException(
                    "The weights in the strategy array must be non-negative");
            strategySum += w;
        }
        if (strategySum == 0.0) throw new IllegalArgumentException(
                "The weights in the strategy array must sum to a positive value");
        // Normalize weights to get probabilities
        for (int i = 0; i < strategy.length; i++) {
            strategy[i] /= strategySum;
        }
    }
    @Override
    public int chooseAction() {
        double selector = random.nextDouble();

        int action = 0;
        while (action < strategy.length) {
            selector -= strategy[action];
            if (selector <= 0)
                return action;
            action++;
        }
        return strategy.length - 1; }
    @Override
    public void update(Game game, int myAction, int otherAction, int player) { // No update needed for a fixed strategy


    }
    @Override
    public double[] getStrategy() { return strategy;
    } }
