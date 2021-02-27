package assignment2;

import java.util.ArrayList;

public class Player {
    //put number of pegs here
    public int pegsInCode = GameConfiguration.pegNumber;

    //Code Peg array
    public ArrayList<Peg> userGuessArray = new ArrayList<Peg>();

    public void setUserGuessArray(char [] userGuess) {
        for(int i = 0; i < pegsInCode; i++){
            Peg tempPeg = new Peg(userGuess[i], i);
            userGuessArray.add(tempPeg);
        }
    }

    public void BackToValid(){
        for(int i = 0; i < pegsInCode; i++){
            userGuessArray.get(i).valid = true;
        }
    }
}
