package assignment2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    boolean testingMode = false;
    boolean invalidGuess = false;
    Scanner scanner = new Scanner(System.in);
    Player player1 = new Player();
    Board board = new Board();
    private String gameStart = "Welcome to Mastermind. Here are the rules.\n\n"
            + "This is a text version of the classic board game Mastermind. "
            + "The computer will think of a secret code. The code consists of ";
    private String gameStart2 = " colored pegs.\nThe pegs MUST be one of six colors: "
            + "blue, green, orange, purple, red, or yellow. A color may appear more than "
            + "once in the code. You try to guess what colored pegs are in the code and w"
            + "hat order they are in. After you make a valid guess the result (feedback) "
            + "will be\ndisplayed.\nThe result consists of a black peg for each peg you "
            + "have guessed exactly correct (color and position) in your guess. For each "
            + "peg in the guess that is the correct color, but is out of position, you get "
            + "a white peg. For each peg that is fully incorrect, you get no feedback.\n"
            + "Only the first letter of the color is displayed. B for Blue, R for Red, "
            + "and so forth.\nWhen entering guesses you only need to enter the first "
            + "character of each color as a capital letter.\n\nYou have ";
    private String gameStart3 = " guesses to figure out the secret code or you "
            + "lose the game. Are you ready to play? (Y/N):";

    public Game(boolean testingMode){
        this.testingMode = testingMode;
    }

    public void runGame(){
        //Game initial greeting
        System.out.print(gameStart + GameConfiguration.pegNumber + gameStart2 + GameConfiguration.guessNumber + gameStart3);

        //Start the game input
        String readyCheck = scanner.nextLine();
        if(readyCheck.charAt(0) == 'N'){
            return;
        }
        else if(readyCheck.charAt(0) != 'Y'){
                System.out.println("Invalid input, try again!\n"+ "Are you ready to play? (Y/N):");
                while(readyCheck.charAt(0) != 'Y'){
                    readyCheck = scanner.nextLine();
                    if(readyCheck.charAt(0) == 'N'){
                        return;
                    }
                    if(readyCheck.charAt(0) == 'Y'){
                        break;
                    }
                    System.out.println("Invalid input, try again!\n"+ "Are you ready to play? (Y/N):");
                }
        }

        //Holds result pegs
        ArrayList<Integer> resultsList = new ArrayList<>();
        resultsList.add(0);
        resultsList.add(0);

        while(true) {
            board.SetCodePegList();
            GameLoop(board, resultsList);
            if(!NextGamePrompt(readyCheck)) {
              break;
            }
            board.guessesLeft = GameConfiguration.guessNumber;
            board.secretCode = SecretCodeGenerator.getInstance().getNewSecretCode().toCharArray();
            resultsList.set(0, 0);
            resultsList.set(1, 0);
            player1.history.setLength(0);
        }

    }



    public void GameLoop(Board board, ArrayList<Integer> resultsList){
        //Show secret code if in testing mode
        System.out.print("Generating secret code ...");
        if(testingMode){
            System.out.print(" The secret code is: ");
            System.out.println(board.secretCode);
        }
        System.out.print("\n");

        //Enter game loop
        while(board.guessesLeft != 0){
            invalidGuess = false;

            //Guessed right, game is finished
            if(resultsList.get(0) == board.pegsInCode){
                System.out.println("You win !!");
                return;
            }

            //Guess prompt
            System.out.println("You have " + board.guessesLeft + " guesses left\nWhat is your next guess?\nType in the characters for your guess and press enter.\nEnter guess: ");

            //User guess stored here
            String UGuess = scanner.nextLine();
            char [] userGuess = UGuess.toCharArray();

            if(UGuess.equals("HISTORY")){
                System.out.println(player1.history);
                continue;
            }
            //Check for invalid input
            if(UGuess.length() != board.pegsInCode){
                invalidGuess = true;
            }
            if(!invalidGuess) {
                for(int i = 0; i < board.pegsInCode; i++){
                    if (91 < userGuess[i] || userGuess[i] < 64) {
                        invalidGuess = true;
                        break;
                    }
                    for(int j = 0; j < GameConfiguration.colors.length; j++){
                        if(userGuess[i] == GameConfiguration.colors[j].charAt(0)){
                            invalidGuess = false;
                            break;
                        }
                        else{
                            invalidGuess = true;
                        }
                    }
                }
            }

            if(invalidGuess){
                String wrongGuess = new String(userGuess);
                System.out.println(wrongGuess + "  ->  INVALID GUESS");
            }
            else{
                //Convert user guess to list of pegs
                player1.setUserGuessArray(userGuess);

                //Calculate guess
                board.GuessCalculator(player1.userGuessArray, resultsList);

                player1.history.append(UGuess);
                player1.history.append("\t\tResult: ");
                player1.history.append(resultsList.get(0)).append("B").append("_").append(resultsList.get(1)).append("W").append("\n");
                System.out.print(userGuess);
                System.out.println("\t\t" + "Result: " + resultsList.get(0) + "B" + "_" + resultsList.get(1) + "W" + "\n");

                //set pegs back to valid for next round
                board.BackToValid();
                player1.BackToValid();
                board.guessesLeft -= 1;
            }
        }

        //Out of guesses
        System.out.println("Sorry, you are out of guesses. You lose, boo-hoo.");
    }

    public Boolean NextGamePrompt(String readyCheck){
        System.out.println("\nAre you ready for another game (Y/N): ");
        readyCheck = scanner.nextLine();
        while(!readyCheck.equals("Y")){
            if(readyCheck.equals("N")){
                return false;
            }
            else{
                System.out.println("Invalid input, try again");
                System.out.println("\nAre you ready for another game (Y/N): ");
            }
            readyCheck = scanner.nextLine();
        }
        return true;
    }

}