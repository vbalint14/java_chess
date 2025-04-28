

import java.io.Serializable;

public class Queen extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public Queen(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) {
        
        if (isOutOfBounds(targetRow, targetCol))
            return false;

        if (targetRow == row && targetCol == col)
            return false; 

        int rowDiff = Math.abs(targetRow - row);
        int colDiff = Math.abs(targetCol - col);

        if (rowDiff == colDiff || targetRow == row || targetCol == col) { 
            if (isPathClear(targetRow, targetCol, board)) {
                Piece targetPiece = board[targetRow][targetCol];
                return targetPiece == null || !this.color.equals(targetPiece.getColor());
            }
        }
        return false;
    }


    private boolean isPathClear(int targetRow, int targetCol, Piece[][] board) {
        int rowStep = Integer.signum(targetRow - row);
        int colStep = Integer.signum(targetCol - col);
        int currentRow = row + rowStep;
        int currentCol = col + colStep;

        while (currentRow != targetRow || currentCol != targetCol) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        return true;
    }

}
