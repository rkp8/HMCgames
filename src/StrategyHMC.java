/** Answers to Questions:
        *
        * 1 and 2)
        * The 
        *
        * 3)
        *
 */



import java.util.Arrays;
import java.util.Random;

public class StrategyHMC implements Strategy {

    //two strategies, one for player 1 and one for player 2
    private double[] strategy1, strategy2;
    private double[] SUM_STRATEGY1, SUM_STRATEGY2;

    private Random random = new Random();

    //t stores timestamp
    int t = 0;

    //p stores player number
    int p =0;

    //convert to arrays later

    //stores regret based on current action choice of player
    private double P1RockRegret, P1PaperRegret, P1ScissorsRegret = 0;
    private double P2RockRegret, P2PaperRegret, P2ScissorsRegret = 0;

    //stores cumulative regret for each action choice
    private double P1prevRockRegret1, P1prevPaperRegret2, P1prevScissorsRegret3 = 0;
    private double P2prevRockRegret, P2prevPaperRegret, P2prevScissorRegret = 0;

    //stores utility of given action choice based on the current value of otherAction
    private double P1RockUtility, P1PaperUtility, P1ScissorsUtility;
    private double P2RockUtility, P2PaperUtility, P2ScissorsUtility;

    //value for mu, can be adjusted as needed
    private double mu = 1;

    //number of possible actions
    private int StrategyArraySize = 0;

    /**
     * @param initStrategy - an initial set of probabilities for each strategy
     * @param game - the game being played
     * @param player       - whether I am player 1 or player 2
     */
    public StrategyHMC(double[] initStrategy, Game game, int player) { // TO DO: Complete this constructor


//initialize all regrets to 0
        P1RockRegret =0;
        P1PaperRegret =0;
        P1ScissorsRegret = 0;
        P1prevRockRegret1 =0;
        P1prevPaperRegret2 =0;
        P1prevScissorsRegret3 = 0;

        P2RockRegret =0;
        P2PaperRegret =0;
        P2ScissorsRegret = 0;
        P2prevRockRegret =0;
        P2prevPaperRegret =0;
        P2prevScissorRegret = 0;



        StrategyArraySize = initStrategy.length -1;

        //get player number
        p = player;


        double strategySum = 0;

        //setup array based on player number

        if(p==1) {
            strategy1 = Arrays.copyOf(initStrategy, initStrategy.length);
            SUM_STRATEGY1 = Arrays.copyOf(initStrategy, initStrategy.length);



            for (double w : strategy1) {
                if (w < 0) throw new IllegalArgumentException(
                        "The weights in the strategy array must be non-negative");
                strategySum += w;
            }
            if (strategySum == 0.0) throw new IllegalArgumentException(
                    "The weights in the strategy array must sum to a positive value");
            // Normalize weights to get probabilities
            for (int i = 0; i < strategy1.length; i++) {
                strategy1[i] /= strategySum;
            }

        }


        if(p==2) {
            strategy2 = Arrays.copyOf(initStrategy, initStrategy.length);
            SUM_STRATEGY2 = Arrays.copyOf(initStrategy, initStrategy.length);


            strategySum = 0;

            for (double w : strategy2) {
                if (w < 0) throw new IllegalArgumentException(
                        "The weights in the strategy array must be non-negative");
                strategySum += w;
            }
            if (strategySum == 0.0) throw new IllegalArgumentException(
                    "The weights in the strategy array must sum to a positive value");
            // Normalize weights to get probabilities
            for (int i = 0; i < strategy2.length; i++) {
                strategy2[i] /= strategySum;
            }

        }



    }

    @Override
    public int chooseAction() {
// TO DO: Complete this method



        if(p == 1) {
            double selector = random.nextDouble();
          //  System.out.println("current selector: " + selector);

            int action = 0;
            while (action < strategy1.length) {
                selector -= strategy1[action];
                if (selector <= 0)
                    return action;
                action++;
            }

        }

        if(p == 2) {
            double selector = random.nextDouble();
            int action = 0;
            while (action < strategy2.length) {
                selector -= strategy2[action];
                if (selector <= 0)
                    return action;
                action++;
            }
        }


        return StrategyArraySize - 1;


    }


    @Override
    public void update(Game game, int myAction, int otherAction, int player) { // TO DO: Complete this method

        p = player;

        if(p == 1) {

            //if we are playing RPS, i.e there are 3 strategies available maps to indices 0,1,2
            if (StrategyArraySize == 2) {

                //if we chose rock
                if (myAction == 0) {

                    //find utility of our choice (Rock)
                    P1RockUtility = game.utility(myAction, otherAction, player);

                    //Alternate utilities:
                    //paper
                    P1PaperUtility = game.utility(1, otherAction, player);

                    //scissors
                    P1ScissorsUtility = game.utility(2, otherAction, player);

                    //Find regret for each alternate
                    P1PaperRegret = (P1PaperUtility - P1RockUtility);
                    P1ScissorsRegret = (P1ScissorsUtility - P1RockUtility);


                    //update Strategy
                    if (t > 1) {
                        strategy1[1] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P1PaperRegret, 0)) * (1 / mu));
                        strategy1[2] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P1ScissorsRegret, 0)) * (1 / mu));
                    } else {
                        strategy1[1] = (Math.max(P1PaperRegret, 0)) * (1 / mu);
                        strategy1[2] = (Math.max(P1ScissorsRegret, 0)) * (1 / mu);
                    }


                    //myAction = 0
                    strategy1[0] = 0;


                }
                //same for if we choose Paper:
                if (myAction == 1) {
                    P1PaperUtility = game.utility(myAction, otherAction, player);
                    P1RockUtility = game.utility(0, otherAction, player);
                    P1ScissorsUtility = game.utility(2, otherAction, player);


                    P1RockRegret = (P1RockUtility - P1PaperUtility);
                    P1ScissorsRegret = (P1ScissorsUtility - P1PaperUtility);


                    //update Strategy
                    if (t > 1) {
                        strategy1[0] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P1RockRegret, 0)) * (1 / mu));
                        strategy1[2] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P1ScissorsRegret, 0)) * (1 / mu));
                    } else {
                        strategy1[0] = (Math.max(P1RockRegret, 0)) * (1 / mu);
                        strategy1[2] = (Math.max(P1ScissorsRegret, 0)) * (1 / mu);
                    }


                    strategy1[1] = 0;

                }
                //Finally Scissors
                if (myAction == 2) {
                    P1ScissorsUtility = game.utility(myAction, otherAction, player);
                    P1RockUtility = game.utility(0, otherAction, player);
                    P1PaperUtility = game.utility(1, otherAction, player);


                    P1RockRegret = (P1RockUtility - P1ScissorsUtility);
                    P1PaperRegret = (P1PaperUtility - P1ScissorsUtility);


                    //update Strategy
                    if (t > 1) {
                        strategy1[0] = (((((double)1 / ((double)(t - 1)))) * (Math.max(P1RockRegret, 0)) * (1 / mu)));
                        strategy1[1] = (((((double)1 / ((double)(t - 1)))) * (Math.max(P1PaperRegret, 0)) * (1 / mu)));
                    } else {
                        strategy1[0] = (Math.max(P1RockRegret, 0)) * (1 / mu);
                        strategy1[1] = (Math.max(P1PaperRegret, 0)) * (1 / mu);
                    }


                    strategy1[2] = 0;

                }


            }

            //If we are playing BOTS instead, only 2 choices, maps to 0 and 1 indices
            if (StrategyArraySize == 1) {

                if (myAction == 0) {

                    //find utilities of all alternate actions
                    P1RockUtility = game.utility(myAction, otherAction, player);
                    P1PaperUtility = game.utility(1, otherAction, player);


                    P1PaperRegret = (P1PaperUtility - P1RockUtility);


                    //update Strategy
                    if (t > 1) {
                        strategy1[1] = (((((double)1 / ((double)(t - 1)))) * (Math.max(P1PaperRegret, 0)) * (1 / mu)));
                    } else {
                        strategy1[1] = (Math.max(P1PaperRegret, 0)) * (1 / mu);
                    }


                    strategy1[0] = 0;


                }
                if (myAction == 1) {
                    P1RockUtility = game.utility(myAction, otherAction, player);
                    P1PaperUtility = game.utility(0, otherAction, player);


                    P1RockRegret = (P1PaperUtility - P1RockUtility);


                    strategy1[0] = (Math.max(P1RockRegret, 0)) * (1 / mu);

                    //update Strategy
                    if (t > 1) {
                        strategy1[0] = (((((double)1 / ((double)(t - 1)))) * (Math.max(P1RockRegret, 0)) * (1 / mu)));
                    } else {
                        strategy1[0] = (Math.max(P1RockRegret, 0)) * (1 / mu);
                    }


                    strategy1[1] = 0;

                }


            }

            //store these regrets for next time
            P1prevRockRegret1 += P1RockRegret;
            P1prevPaperRegret2 += P1PaperRegret;
            P1prevScissorsRegret3 += P1ScissorsRegret;


          //  System.out.println("t: " + t);



            for(int i = 0; i<SUM_STRATEGY1.length; i++) {
           //     System.out.println("Value in SUM Before at index " + i+ " : " + SUM_STRATEGY1[i]);

                SUM_STRATEGY1[i] += strategy1[i];

           //     System.out.println("Value in SUM After at index " + i+ " : " + SUM_STRATEGY1[i]);


             //   System.out.println("Regret for index " + i+ " : " + strategy1[i]);


                if(t>1)
                    strategy1[i] = ((SUM_STRATEGY1[i]));
                else
                    strategy1[i] = (SUM_STRATEGY1[i]);

           //    System.out.println("Value in Strategy After at index " + i+ " : " + strategy1[i]);




            }

            //increment time
            t++;



        }



//Repeat all but in different variables for player 2
if(p == 2) {

//if we are playing RPS, i.e there are 3 strategies available
    if (StrategyArraySize == 2) {

        //if we chose rock
        if (myAction == 0) {
            //find utilities of our choice
            P2RockUtility = game.utility(0, otherAction, player);

            //find utilities of other choices:
                //paper
                P2PaperUtility = game.utility(1, otherAction, player);

                //scissors
                P2ScissorsUtility = game.utility(2, otherAction, player);

            //Find regret of not choosing each alternate
            P2PaperRegret = (P2PaperUtility - P2RockUtility);
            P2ScissorsRegret = (P2ScissorsUtility - P2RockUtility);


            //update Strategy
            if (t > 1) {
                strategy2[1] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2PaperRegret, 0)) * (1 / mu));
                strategy2[2] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2ScissorsRegret, 0)) * (1 / mu));
            } else {
                strategy2[1] = (Math.max(P2PaperRegret, 0)) * (1 / mu);
                strategy2[2] = (Math.max(P2ScissorsRegret, 0)) * (1 / mu);
            }


            //myAction = 0
            strategy2[myAction] = 0;


        }
        if (myAction == 1) {
            P2PaperUtility = game.utility(myAction, otherAction, player);
            P2RockUtility = game.utility(0, otherAction, player);
            P2ScissorsUtility = game.utility(2, otherAction, player);


            P2RockRegret = (P2RockUtility - P2PaperUtility);
            P2ScissorsRegret = (P2ScissorsUtility - P2PaperUtility);


            //update Strategy
            if (t > 1) {
                strategy2[0] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2RockRegret, 0)) * (1 / mu));
                strategy2[2] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2ScissorsRegret, 0)) * (1 / mu));
            } else {
                strategy2[0] = (Math.max(P2RockRegret, 0)) * (1 / mu);
                strategy2[2] = (Math.max(P2ScissorsRegret, 0)) * (1 / mu);
            }


            strategy2[myAction] = 0;

        }
        if (myAction == 2) {
            P2ScissorsUtility = game.utility(myAction, otherAction, player);
            P2RockUtility = game.utility(0, otherAction, player);
            P2PaperUtility = game.utility(1, otherAction, player);


            P2RockRegret = (P2RockUtility - P2ScissorsUtility);
            P2PaperRegret = (P2PaperUtility - P2ScissorsUtility);


            //update Strategy
            if (t > 1) {
                strategy2[0] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2RockRegret, 0)) * (1 / mu));
                strategy2[1] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2PaperRegret, 0)) * (1 / mu));
            } else {
                strategy2[0] = (Math.max(P2RockRegret, 0)) * (1 / mu);
                strategy2[1] = (Math.max(P2PaperRegret, 0)) * (1 / mu);
            }


            strategy2[myAction] = 0;

        }


    }

    if (StrategyArraySize == 1) {

        if (myAction == 0) {

            //find utilities of all alternate actions
            P2RockUtility = game.utility(myAction, otherAction, player);
            P2PaperUtility = game.utility(1, otherAction, player);


            P2PaperRegret = (P2PaperUtility - P2RockUtility);


            //update Strategy
            if (t > 1) {
                strategy2[1] = (Math.max(((((double)1 / ((double)(t - 1)))))*P2PaperRegret, 0)) * (1 / mu);
            } else {
                strategy2[1] = (Math.max(P2PaperRegret, 0)) * (1 / mu);
            }


            strategy2[myAction] = 0;


        }
        if (myAction == 1) {
            P2RockUtility = game.utility(myAction, otherAction, player);
            P2PaperUtility = game.utility(0, otherAction, player);


            P2RockRegret = (P2PaperUtility - P2RockUtility);


            //update Strategy
            if (t > 1) {
                strategy2[0] = ((((double)1 / ((double)(t - 1)))) * (Math.max(P2RockRegret, 0)) * (1 / mu));
            } else {
                strategy2[0] = (Math.max(P2RockRegret, 0)) * (1 / mu);
            }


            strategy2[myAction] = 0;

        }


    }

    //store these regrets for next time
    P2prevRockRegret += P2RockRegret;
    P2prevPaperRegret += P2PaperRegret;
    P2prevScissorRegret += P2ScissorsRegret;





    for(int i = 0; i<SUM_STRATEGY2.length; i++) {
        //     System.out.println("Value in SUM Before at index " + i+ " : " + SUM_STRATEGY1[i]);

        SUM_STRATEGY2[i] += strategy2[i];

        //     System.out.println("Value in SUM After at index " + i+ " : " + SUM_STRATEGY1[i]);


        //   System.out.println("Regret for index " + i+ " : " + strategy1[i]);


        if(t>1)
            strategy2[i] = ((SUM_STRATEGY2[i]));
        else
            strategy2[i] = (SUM_STRATEGY2[i]);

        //    System.out.println("Value in Strategy After at index " + i+ " : " + strategy1[i]);




    }


//increment time
    t++;




}

        double strategySum = 0;

        //setup array based on player number
//for(int j =0; j<strategy1.length; j++)
        //System.out.println("Value in SUM After at index " + j+ " : " + strategy1[j]);

        if(p==1) {

            for (double w : strategy1) {
                if (w < 0) throw new IllegalArgumentException(
                        "The weights in the strategy array must be non-negative");
                strategySum += w;
            }
            if (strategySum == 0.0) throw new IllegalArgumentException(
                    "The weights in the strategy array must sum to a positive value");
            // Normalize weights to get probabilities
            for (int i = 0; i < strategy1.length; i++) {
                strategy1[i] /= strategySum;
                //System.out.println("\nNew strategy for index " + i+ " : " + strategy1[i]);

            }

        }


        if(p==2) {

            strategySum = 0;

            for (double w : strategy2) {
                if (w < 0) throw new IllegalArgumentException(
                        "The weights in the strategy array must be non-negative");
                strategySum += w;
            }
            if (strategySum == 0.0) throw new IllegalArgumentException(
                    "The weights in the strategy array must sum to a positive value");
            // Normalize weights to get probabilities
            for (int i = 0; i < strategy2.length; i++) {
                strategy2[i] /= strategySum;
                //  System.out.println("\nNew strategy for index " + i+ " : " + strategy1[i]);

            }

        }
    }


    @Override
    public double[] getStrategy() { // TO DO: Complete this method
        if (p == 1)
            return strategy1;
        else
            return strategy2;
    }

}