package assignment2;

import java.util.ArrayList;

/**
 This class represents the "board" portion of the game
 It will hold the secret code, the guesses left, and also calculate the output per user guess
 @author Seana Aghili
 Class/Section: EE422C/17095
 Assignment 2
 @version 1.51 2021-02-28
 */

public class Board
{
    //secret code
    public char [] secretCode = SecretCodeGenerator.getInstance().getNewSecretCode().toCharArray();

    //guesses left
    public int guessesLeft = GameConfiguration.guessNumber;

    //number of pegs
    public int pegsInCode = GameConfiguration.pegNumber;

    //Code Peg array
    public ArrayList<Peg> codeArray = new ArrayList<Peg>();

    /**
     * Initializes the length of the "Peg" list for holding the secret code
     */
    public Board()
    {
        for (int i = 0; i < pegsInCode; i++)
        {
            Peg tempPeg = new Peg('c', i);
            codeArray.add(tempPeg);
        }
    }

    /**
     * Given a character array, convert to list of pegs to store it in
     */
    public void setCodePegList()
    {
        //Convert secret code to list of pegs
        for (int i = 0;i < pegsInCode; i++)
        {
            Peg tempPeg = new Peg(secretCode[i], i);
            codeArray.set(i, tempPeg);
        }
    }

    /**
     * Calculates the result of the user's guess
     * @param usersGuess holds the user's guess for processing
     * @param returnList holds the return array for the result
     */
    public void guessCalculator(ArrayList<Peg> usersGuess, ArrayList<Integer> returnList)
    {
        int black = 0;
        int white = 0;

        //check for correct color and position (black)
        for (int i = 0; i < GameConfiguration.pegNumber; i++)
        {
            if (usersGuess.get(i).isEqual(codeArray.get(i)))
            {
                black += 1;
                usersGuess.get(i).valid = false;
                codeArray.get(i).valid = false;
            }
        }

        //check for correct color wrong position (white)
        for (int i = 0; i < GameConfiguration.pegNumber; i++)
        {
            for (int j = 0; j < GameConfiguration.pegNumber; j++)
            {
                if (usersGuess.get(i).color == codeArray.get(j).color & usersGuess.get(i).valid
                        & codeArray.get(j).valid)
                {
                    white += 1;
                    usersGuess.get(i).valid = false;
                    codeArray.get(j).valid = false;
                }
            }
        }
        //Sets results
        returnList.set(0, black);
        returnList.set(1, white);
    }

    /**
     * Resets validity of Peg list
     */
    public void backToValid()
    {
        for (int i = 0; i < pegsInCode; i++)
        {
            codeArray.get(i).valid = true;
        }
    }
}
