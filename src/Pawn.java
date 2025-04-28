
import java.io.Serializable;

import javax.swing.JOptionPane;

public class Pawn extends Piece implements Serializable{ 
    private static final long serialVersionUID = 1L;

    public Pawn(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) {
        
        if (isOutOfBounds(targetRow, targetCol))
             return false;
        int rowDiff = targetRow - row;
        int colDiff = Math.abs(targetCol - col);

        // Gyalog előre léphet egyet
        if (color.equals("white") && rowDiff == -1 && colDiff == 0 && board[targetRow][targetCol] == null) {
            return true;
        } else if (color.equals("black") && rowDiff == 1 && colDiff == 0 && board[targetRow][targetCol] == null) {
            return true;
        }

        // Gyalog első lépésként kettőt léphet előre
        if (color.equals("white") && row == 6 && rowDiff == -2 && colDiff == 0 && 
            board[targetRow][targetCol] == null && board[row - 1][col] == null) {
            return true;
        } else if (color.equals("black") && row == 1 && rowDiff == 2 && colDiff == 0 && 
                board[targetRow][targetCol] == null && board[row + 1][col] == null) {
            return true;
        }

        // Gyalog átlósan üt
        if (colDiff == 1 && board[targetRow][targetCol] != null &&
            !board[targetRow][targetCol].getColor().equals(color)) {
            if (color.equals("white") && rowDiff == -1) {
                return true;
            } else if (color.equals("black") && rowDiff == 1) {
                return true;
            }
        }

        // En passant 
        if (colDiff == 1 && board[targetRow][targetCol] == null) {
            int capturedRow = (color.equals("white")) ? targetRow + 1 : targetRow - 1;
            if (capturedRow >= 0 && capturedRow < 8) { 
                Piece capturedPawn = board[capturedRow][targetCol];
                if (capturedPawn instanceof Pawn && !capturedPawn.getColor().equals(color) && ((Pawn) capturedPawn).hasJustMovedTwoSteps()) {
                    return (color.equals("white") && rowDiff == -1) || (color.equals("black") && rowDiff == 1);
                }
            }
        }
   

        return false;
    }

}
