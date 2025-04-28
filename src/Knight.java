
import java.io.Serializable;

public class Knight extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public Knight(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) {

        Piece targetPiece = board[targetRow][targetCol];
        int rowDiff = Math.abs(targetRow - row);
        int colDiff = Math.abs(targetCol - col);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return targetPiece == null || !targetPiece.getColor().equals(color);
        }
        return false;
    }

}