

public class Move {
    private int startRow, startCol, targetRow, targetCol;
    private Piece capturedPiece;

    public Move(int startRow, int startCol, int targetRow, int targetCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.capturedPiece = null;
    }

    public void setCapturedPiece(Piece piece) {
        this.capturedPiece = piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }
}