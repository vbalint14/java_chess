
import java.io.Serializable;

public abstract class Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    
    protected String color;  
    protected int row;       
    protected int col;       
    protected boolean justMovedTwoSteps = false;

    public Piece(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public boolean hasJustMovedTwoSteps() {
        return justMovedTwoSteps;
    }

    public void setJustMovedTwoSteps(boolean justMovedTwoSteps) {
        this.justMovedTwoSteps = justMovedTwoSteps;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isOutOfBounds(int targetRow, int targetCol){
        return (targetRow < 0 || targetRow >= 8 || targetCol < 0 || targetCol >= 8);
    }

    public abstract boolean isValidMove(int targetRow, int targetCol, Piece[][] board);

}
