public class GameRPS implements Game {
    static final int ROCK = 0, PAPER = 1, SCISSORS = 2, NUM_ACTIONS = 3;
    static String[] ACTIONS = {"Rock", "Paper", "Scissors"};
    /**
     * @param myAction action played by hero
     * @param otherAction action played by villain
     * @param player what is player number for player that played myAction
     *               * @return utility for the player of interest
     */
    public int utility(int myAction, int otherAction, int player) {
        if (myAction == otherAction) {
        return 0; }
    else if ((myAction == ROCK &&  otherAction == SCISSORS) || (myAction == SCISSORS &&   otherAction == PAPER) || (myAction == PAPER &&  otherAction == ROCK)) {
        return 1; }
else {
            return -1;
        } }
    /**
     * Provide a description of an action
     *
     * @param actionIndex the integer representing the action
     * @return the description of the action
     */
    public String actionAsString(int actionIndex) {
        return ACTIONS[actionIndex];
    }

    public String getType() {
        return "RPS";
    } }

