public class Player {
    private Strategy strategy; private Game game; private int player;
    public Player(Strategy strategy, Game game, int player) { this.strategy = strategy;
        this.game = game;
        this.player = player;
    }
    public int chooseAction() {
        return strategy.chooseAction();
    }
    public void update(int myAction, int otherAction) { strategy.update(game, myAction, otherAction, player);
    }
    public Strategy getStrategy() { return strategy;
    } }
