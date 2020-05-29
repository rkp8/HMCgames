public class Driver {
    static boolean DEBUG = false;
    static int totalRounds = 1_000_000;
    public static void main(String[] args) {
        Game game = new GameRPS();
        Player player1 = new Player(new StrategyHMC(new double[] {0.33,0.33,0.33}, game, 1), game, 1);
        Player player2 = new Player(new StrategyHMC(new double[] {0.33,0.33,0.33}, game, 2), game, 2);
        int p1Wins = 0, p2Wins = 0;
        double p1TotalUtility = 0, p2TotalUtility = 0;
        for (int i = 0; i < totalRounds; i++) {
            int p1Choice = player1.chooseAction();
            int p2Choice = player2.chooseAction();
            double p1Utility = game.utility(p1Choice, p2Choice, 1);
            double p2Utility = game.utility(p2Choice, p1Choice, 2);
            p1TotalUtility += p1Utility;
            p2TotalUtility += p2Utility;
            if (DEBUG) System.out.println("\nPlayer 1 plays " + game.actionAsString(p1Choice) + ", player 2 plays " + game.actionAsString(p2Choice));
            if (p1Utility > p2Utility) {
                p1Wins++;
                if (DEBUG) System.out.println("Player 1 wins");
            }
            else if (p1Utility < p2Utility) {
                p2Wins++;
                if (DEBUG) System.out.println("Player 2 wins"); }
            else {
                if (DEBUG) System.out.println("Tie");
            }
            player1.update(p1Choice, p2Choice);
            player2.update(p2Choice, p1Choice);


        }
        System.out.println("Player1 wins: " + p1Wins + " Player2 wins: " + p2Wins); System.out.println("Player1 utility per game: " + (p1TotalUtility/totalRounds) +
                " Player2 utility per game: " + (p2TotalUtility/totalRounds)); System.out.println("Player 1 Strategy:");
        double[] s= player1.getStrategy().getStrategy(); for (int i = 0; i < s.length; i++) {
            System.out.print(" " + s[i]); }
        System.out.println("\nPlayer 2 Strategy:"); s= player2.getStrategy().getStrategy(); for (int i = 0; i < s.length; i++) {
            System.out.print(" " + s[i]); }
        System.out.println(); }
}