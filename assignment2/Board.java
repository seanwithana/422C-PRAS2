package assignment2;

import java.util.ArrayList;

public class Board {
    //put secret code here
    char [] secretCode = SecretCodeGenerator.getInstance().getNewSecretCode().toCharArray();

    //put guesses left here
    public int guessesLeft = GameConfiguration.guessNumber;

    //put number of pegs here
    public int pegsInCode = GameConfiguration.pegNumber;

    //Code Peg array
    ArrayList<Peg> codeArray = new ArrayList<Peg>();

    public Board(){
        for(int i = 0; i < pegsInCode; i++){
            Peg tempPeg = new Peg('c', i);
            codeArray.add(tempPeg);
        }
    }

    public void SetCodePegList(){
        //Convert secret code to list of pegs
        for(int i = 0;i < pegsInCode; i++){
            Peg tempPeg = new Peg(secretCode[i], i);
            codeArray.set(i, tempPeg);
        }
    }


    public void GuessCalculator(ArrayList<Peg> usersGuess, ArrayList<Integer> returnList){
        int black = 0;
        int white = 0;

        //check for correct color and position (black)
        for(int i = 0; i < GameConfiguration.pegNumber; i++){
            if(usersGuess.get(i).isEqual(codeArray.get(i))){
                black += 1;
                usersGuess.get(i).valid = false;
                codeArray.get(i).valid = false;
            }
        }

        //check for correct color wrong position (white)
        for(int i = 0; i < GameConfiguration.pegNumber; i++){
            for(int j = 0; j < GameConfiguration.pegNumber; j++){
                if(usersGuess.get(i).color == codeArray.get(j).color & usersGuess.get(i).valid & codeArray.get(j).valid){
                    white += 1;
                    usersGuess.get(i).valid = false;
                    codeArray.get(j).valid = false;
                }
            }
        }
        returnList.set(0, black);
        returnList.set(1, white);
    }

    public void BackToValid(){
        for(int i = 0; i < pegsInCode; i++){
            codeArray.get(i).valid = true;
        }
    }
}
