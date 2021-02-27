package assignment2;

public class Peg {
    public char color;
    public Integer position = -1;
    public boolean valid = true;
    public Peg(char color, Integer position){
        this.color = color;
        this.position = position;
    }
    public boolean isEqual(Peg other){
        return other.position.equals(this.position) & other.color == this.color;
    }
}
