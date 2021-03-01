package assignment2;

/**
 This class is the driver for the mastermind game
 @author Seana Aghili
 Class/Section: EE422C/17095
 Assignment 2
 @version 1.51 2021-02-28
 */

public class Driver
{
    //First command line argument will specify whether the game will run in testing mode (1), or not (!1)
    public static void main(String [] args)
    {
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
