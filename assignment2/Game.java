package assignment2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 This class represents the bulk of the game
 Includes the the actual running of the game, the console outputs to the user,
 and necessary inits for the other classes
 @author Seana Aghili
 Class/Section: EE422C/17095
 Assignment 2
 @version 1.51 2021-02-28
 */

public class Game
{
    public Player player1 = new Player();
    public Board board = new Board();

    private final String GAMESTART = "Welcome to Mastermind. Here are the rules.\n\n"
            + "This is a text version of the classic board game Mastermind. "
            + "The computer will think of a secret code. The code consists of ";
    private final String GAMESTART2 = " colored pegs.\nThe pegs MUST be one of six colors: "
            + "blue, green, orange, purple, red, or yellow. A color may appear more than "
            + "once in the code. You try to guess what colored pegs are in the code and w"
            + "hat order they are in. After you make a valid guess the result (feedback) "
            + "will be displayed.\nThe result consists of a black peg for each peg you "
            + "have guessed exactly correct (color and position) in your guess. For each "
            + "peg in the guess that is the correct color, but is out of position, you get "
            + "a white peg. For each peg that is fully incorrect, you get no feedback.\n"
            + "Only the first letter of the color is displayed. B for Blue, R for Red, "
            + "and so forth.\nWhen entering guesses you only need to enter the first "
            + "character of each color as a capital letter.\n\nYou have ";
    private final String GAMESTART3 = " guesses to figure out the secret code or you "
            + "lose the game. Are you ready to play? (Y/N):";


    private boolean testingMode = false;
    private boolean invalidGuess = false;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for game, sets the testingMode based on command line args
     * @param testingMode holds boolean for whether to enter testing mode or not
     */
    public Game(boolean testingMode){
        this.testingMode = testingMode;
    }

    /**
     * Begins the mastermind game and enters the game loop
     */
    public void runGame()
    {
        //Game initial greeting
        System.out.print(GAMESTART + GameConfiguration.pegNumber + GAMESTART2 + GameConfiguration.guessNumber +
                GAMESTART3);

        //Start the game input
        String readyCheck = scanner.nextLine();
        //if no
        if (readyCheck.charAt(0) == 'N')
        {
            return;
        }
        //check for invalid input
        else if (readyCheck.charAt(0) != 'Y')
        {
            System.out.println("Invalid input, try again!\n"+ "Are you ready to play? (Y/N):");
            while (readyCheck.charAt(0) != 'Y')
            {
                readyCheck = scanner.nextLine();
                if (readyCheck.charAt(0) == 'N')
                {
                    return;
                }
                if (readyCheck.charAt(0) == 'Y')
                {
                    break;
                }
                System.out.println("Invalid input, try again!\n"+ "Are you ready to play? (Y/N):");
            }
        }

        //Holds result pegs
        ArrayList<Integer> resultsList = new ArrayList<>();
        resultsList.add(0);
        resultsList.add(0);

        //Enter broader game loop
        while (true)
        {
            board.setCodePegList();
            GameLoop(board, resultsList);
            if (!NextGamePrompt(readyCheck))
            {
              break;
            }
            //reset secret code and other variables for next game
            board.guessesLeft = GameConfiguration.guessNumber;
            board.secretCode = SecretCodeGenerator.getInstance().getNewSecretCode().toCharArray();
            resultsList.set(0, 0);
            resultsList.set(1, 0);
            player1.history.setLength(0);
        }

    }

    /**
     * This is the main game loop where each instance of a game is played
     * @param board this is the board that holds important variables and the guess calculator for results outputs
     * @param resultsList Holds the results for each guess
     */
    public void GameLoop(Board board, ArrayList<Integer> resultsList)
    {
        //Show secret code if in testing mode
        System.out.print("\nGenerating secret code ...");
        if (this.testingMode)
        {
            System.out.print(" The secret code is: ");
            System.out.print(board.secretCode);
        }
        System.out.print("\n");

        //Enter game loop
        while (board.guessesLeft != 0)
        {
            //Guessed right, game is finished
            if (resultsList.get(0) == board.pegsInCode)
            {
                System.out.print(" - You win !!\n");
                return;
            }

            System.out.print("\n");
            //Guess prompt
            if(!invalidGuess){
                System.out.println("You have " + board.guessesLeft + " guesses left");
            }
            System.out.print("What is your next guess?" +
                    "\nType in the characters for your guess and press enter.\nEnter guess: ");

            //User guess stored here
            String UGuess = scanner.nextLine();
            char [] userGuess = UGuess.toCharArray();
            System.out.println();

            invalidGuess = false;

            if (UGuess.equals("HISTORY"))
            {
                System.out.println(player1.history);
                continue;
            }
            //Check for invalid input
            if (UGuess.length() != board.pegsInCode)
            {
                invalidGuess = true;
            }

            boolean rightColor = false;
            if (!invalidGuess)
            {
                for (int i = 0; i < board.pegsInCode; i++)
                {
                    if (91 < userGuess[i] || userGuess[i] < 64)
                    {
                        invalidGuess = true;
                        break;
                    }
                    for (int j = 0; j < GameConfiguration.colors.length; j++)
                    {
                        if (userGuess[i] == GameConfiguration.colors[j].charAt(0))
                        {
                            rightColor = true;
                            break;
                        }
                    }
                    if (!rightColor)
                    {
                        invalidGuess = true;
                        break;
                    }
                }
            }

            if (invalidGuess)
            {
                String wrongGuess = new String(userGuess);
                System.out.println(wrongGuess + "  ->  INVALID GUESS\n");
            }
            else
            {
                //Convert user guess to list of pegs
                player1.setUserGuessArray(userGuess);

                //Calculate guess
                board.guessCalculator(player1.userGuessArray, resultsList);

                player1.history.append(UGuess);
                player1.history.append("\t\tResult: ");
                player1.history.append(resultsList.get(0)).append("B").append("_").append(resultsList.get(1)).
                        append("W").append("\n");
                System.out.print(userGuess);
                System.out.print(" -> " + "Result: " + resultsList.get(0) + "B" + "_" + resultsList.get(1)
                        + "W");

                //set pegs back to valid for next round
                board.backToValid();
                player1.backToValid();
                board.guessesLeft -= 1;
            }
        }

        //Out of guesses
        System.out.println("\nSorry, you are out of guesses. You lose, boo-hoo.");
    }

    /**
     * After each game, this method asks if user wants to play again
     * @param readyCheck holds the user inputs
     * @return returns boolean (true for Yes and false for no)
     */
    public Boolean NextGamePrompt(String readyCheck)
    {
        System.out.println("\nAre you ready for another game (Y/N): ");
        readyCheck = scanner.nextLine();
        while (!readyCheck.equals("Y"))
        {
            if (readyCheck.equals("N"))
            {
                return false;
            }
            else
            {
                System.out.println("Invalid input, try again");
                System.out.println("\nAre you ready for another game (Y/N): ");
            }
            readyCheck = scanner.nextLine();
        }
        return true;
    }

}