package assignment2;

/**
 This class represents a Peg
 Includes the color, validity, and position of the peg
 @author Seana Aghili
 Class/Section: EE422C/17095
 Assignment 2
 @version 1.51 2021-02-28
 */

public class Peg
{
    public char color;
    public Integer position = -1;
    public boolean valid = true;

    /**
     * Creates a peg given the color and position
     * @param color character holding the color
     * @param position Integer holding the position of the peg
     */
    public Peg(char color, Integer position){
        this.color = color;
        this.position = position;
    }

    /**
     * Checks for equality of pegs
     * @param other The peg being compared to
     * @return returns a boolean (true for equal, false for not equal)
     */
    public boolean isEqual(Peg other){
        return other.position.equals(this.position) & other.color == this.color;
    }
}
