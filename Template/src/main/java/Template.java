public class Template {
    public static void main(String[] args) {
        new Chess().run();
    }
}


abstract class Game {
    protected int currentPlayer;
    protected final int numberOfPLayers;

    public Game(int numberOfPLayers) {
        this.numberOfPLayers = numberOfPLayers;
    }

    public void run() {
        start();

        while (!haveWinner()) {
            takeTurn();
        }

        System.out.println("Player " + getWinningPlayer() + " wins");
    }

    protected abstract int getWinningPlayer();
    protected abstract void takeTurn();
    protected abstract boolean haveWinner();
    protected abstract void start();
}

class Chess extends Game {
    private int maxTurns = 10;
    private int turn = 1;

    public Chess() {
        super(2);
    }

    protected int getWinningPlayer() {
        return 0;
    }

    protected void takeTurn() {
        System.out.println("Turn " + turn++ + " taken by player " + currentPlayer);
        currentPlayer = (currentPlayer + 1)% numberOfPLayers;
    }

    protected boolean haveWinner() {
        return turn == maxTurns;
    }

    protected void start() {
        System.out.println("Starting a game of chess.");
    }
}