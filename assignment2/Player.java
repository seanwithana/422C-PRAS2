package assignment2;

import java.util.ArrayList;

/**
 This class represents the human player
 Includes the guess history and maintains user guesses
 @author Seana Aghili
 Class/Section: EE422C/17095
 Assignment 2
 @version 1.51 2021-02-28
 */

public class Player
{
    //Holds the history of guesses + result
    public StringBuilder history = new StringBuilder();

    //Code Peg array
    public ArrayList<Peg> userGuessArray = new ArrayList<Peg>();

    //number of pegs
    private final int pegsInCode = GameConfiguration.pegNumber;

    /**
     * Initializes the length of the "Peg" list for holding the user's guess
     */
    public Player(){
        for (int i = 0; i < pegsInCode; i++)
        {
            Peg tempPeg = new Peg('c', i);
            userGuessArray.add(tempPeg);
        }
    }

    /**
     * Given a character array, convert to list of pegs to store it in
     * @param userGuess holds the user's guess as a character array to be converted to Peg list
     */
    public void setUserGuessArray(char [] userGuess) {
        for (int i = 0; i < pegsInCode; i++)
        {
            Peg tempPeg = new Peg(userGuess[i], i);
            userGuessArray.set(i, tempPeg);
        }
    }

    /**
     * Reset validity of Peg list
     */
    public void backToValid(){
        for (int i = 0; i < pegsInCode; i++)
        {
            userGuessArray.get(i).valid = true;
        }
    }
}
