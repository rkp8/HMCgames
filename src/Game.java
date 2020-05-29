public interface Game {
    /**
 * @param myAction action played by hero
 * @param otherAction action played by villain
 * @param player what is player number for player that played myAction * @return utility for the player of interest
 */
int utility(int myAction, int otherAction, int player);
    /**
     * Provide a description of an action
     *
     * @param actionIndex the integer representing the action * @return the description of the action
     */
    String actionAsString(int actionIndex);

    String getType();

}

