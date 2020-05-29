public interface Strategy {
    /**
     * @return the selected action */
    public int chooseAction();
    /**
     * @param game the game that is being played
     * @param p1Action the action that player 1 selected * @param p2Action the action that player 2 selected * @param player what player am I (1 or 2)
     */
    public void update(Game game, int p1Action, int p2Action, int player);
    /**
     * Get current mixed strategy
     * @return array of probabilities, p, where p[i] = prob. that action i will be selected */
    public double[] getStrategy(); }


