
import java.io.Serializable;

public class Rook extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private boolean hasMoved;

    public Rook(String color, int row, int col) {
        super(color, row, col);
        this.hasMoved = false;
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) {
        
        if (isOutOfBounds(targetRow, targetCol))
            return false;

        if (row == targetRow || col == targetCol) {
            if (isPathClear(targetRow, targetCol, board)) {
                Piece targetPiece = board[targetRow][targetCol];
                return targetPiece == null || !this.color.equals(targetPiece.getColor());
            }
        }
        return false;
    }


    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
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
