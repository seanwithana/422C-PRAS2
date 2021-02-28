package assignment2;

public class Driver {
    public static void main(String [] args) {
        boolean testingMode = false;
        int firstArg;
        if (args.length > 0) {
            try {
                firstArg = Integer.parseInt(args[0]);
                if(firstArg == 1){
                    testingMode = true;
                }
            }
            catch (NumberFormatException ignored) {
            }
        }

        Game test = new Game(testingMode);
        test.runGame();
    }
}
