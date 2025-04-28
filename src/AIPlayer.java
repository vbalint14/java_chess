import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AIPlayer implements Serializable{
    private static final long serialVersionUID = 1L;

    private String color;
    private Board gameBoard;

    public AIPlayer(String color, Board gameBoard) {
        this.color = color;
        this.gameBoard = gameBoard;
    }

    public String getColor() {
        return color;
    }

    public Move generateMove() {
        List<Move> validMoves = getAllValidMoves();
        if (validMoves.isEmpty()) return null;

        Map<Class<?>, Integer> pieceValues = Map.of(
            King.class, 1000,
            Queen.class, 9,
            Rook.class, 5,
            Bishop.class, 4,
            Knight.class, 3,
            Pawn.class, 1
        );

        Move bestMove = null;
        int highestValue = 0;

        for (Move move : validMoves) {
            Piece targetPiece = gameBoard.getPieceAt(move.getTargetRow(), move.getTargetCol());
            if (targetPiece != null && !targetPiece.getColor().equals(color)) {
                int value = pieceValues.getOrDefault(targetPiece.getClass(), 0);
                if (value > highestValue) {
                    highestValue = value;
                    bestMove = move;
                }
            }
        }

        if (bestMove != null) return bestMove;

        Random random = new Random();
        return validMoves.get(random.nextInt(validMoves.size()));
    }

    public List<Move> getAllValidMoves() {
        List<Move> validMoves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = gameBoard.getPieceAt(row, col);

                if (piece != null && piece.getColor().equals(color)) {
                    for (int targetRow = 0; targetRow < 8; targetRow++) {
                        for (int targetCol = 0; targetCol < 8; targetCol++) {
                            if (isWithinBounds(targetRow, targetCol)) {
                                Piece targetPiece = gameBoard.getPieceAt(targetRow, targetCol);

                                if (targetPiece != null && targetPiece.getColor().equals(color)) {
                                    continue;
                                }

                                if (piece.isValidMove(targetRow, targetCol, gameBoard.getBoard())) {
                                    validMoves.add(new Move(row, col, targetRow, targetCol));
                                }
                            }
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}