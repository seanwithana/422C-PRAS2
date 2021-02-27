package assignment2;

public class Driver {
    public static void main(String [] args) {
        Game test = new Game(true);
        Board board = new Board();
        test.runGame(board);
    }
}
