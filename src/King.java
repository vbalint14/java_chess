import java.io.Serializable;

public class King extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private boolean hasMoved;

    public King(String color, int row, int col) {
        super(color, row, col);
        this.hasMoved = false;
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) {
        Piece targetPiece = board[targetRow][targetCol];
        if (targetPiece!=null && targetPiece.getColor().equals(color)) {
            return false;
        } 

        if (isOutOfBounds(targetRow, targetCol))
             return false;
        
        int rowDiff = Math.abs(targetRow - row);
        int colDiff = Math.abs(targetCol - col);

        if (rowDiff <= 1 && colDiff <= 1) {
            return targetPiece == null || !targetPiece.getColor().equals(color);
        }

        // Sáncolás ellenőrzése
        if (!hasMoved && row == targetRow && Math.abs(targetCol - col) == 2) {
            int rookCol = targetCol > col ? 7 : 0; 
            Piece rook = board[row][rookCol];
            if (rook instanceof Rook && !((Rook) rook).hasMoved() && isPathClear(targetRow, targetCol, board)) {
                return !isSquareUnderAttack(row, col, board) &&
                        !isSquareUnderAttack(row, col + (targetCol > col ? 1 : -1), board);
            }
        }

        return false;
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        this.hasMoved = true;
    }

    private boolean isPathClear(int targetRow, int targetCol, Piece[][] board) {
        int step = (targetCol - col) / Math.abs(targetCol - col);
        for (int i = col + step; i != targetCol; i += step) {
            if (board[row][i] != null) return false;
        }
        return true;
    }

    private boolean isSquareUnderAttack(int row, int col, Piece[][] board) {
        String attackerColor = color.equals("white") ? "black" : "white";
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board[r][c];
                if (piece != null && piece.getColor().equals(attackerColor) && piece.isValidMove(row, col, board)) {
                    return true;
                }
            }
        }
        return false;
    }

}
