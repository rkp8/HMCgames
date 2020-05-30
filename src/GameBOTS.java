public class GameBOTS implements Game {
    static final int LW = 0, WL = 1;
    static String[] ACTIONS = {"Lethal Weapon", "Wondrous Love"};
    /**
     * @param myAction action played by hero
     * @param otherAction action played by villain
     * @param player what is player number for player that played myAction * @return utility for the player of interest
     */
    public int utility(int myAction, int otherAction, int player) {
        if (myAction != otherAction)
            return 0;
        if (player == 1) {
            if (myAction == 0)
                return 2;
            else return 1;
        } else {
            if (myAction == 1)
                return 2;
            else return 1; }
    }
    /**
     * Provide a description of an action
     *
     * @param actionIndex the integer representing the action * @return the description of the action
     */
    public String actionAsString(int actionIndex) {
        return ACTIONS[actionIndex];
    }

}



