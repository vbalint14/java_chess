import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.Serializable;

import java.util.*;

public class Board implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }

    private void initializeBoard() {
        board[0][0] = new Rook("black", 0, 0);
        board[0][1] = new Knight("black", 0, 1);
        board[0][2] = new Bishop("black", 0, 2);
        board[0][3] = new Queen("black", 0, 3);
        board[0][4] = new King("black", 0, 4);
        board[0][5] = new Bishop("black", 0, 5);
        board[0][6] = new Knight("black", 0, 6);
        board[0][7] = new Rook("black", 0, 7);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("black", 1, i);
        }

        board[7][0] = new Rook("white", 7, 0);
        board[7][1] = new Knight("white", 7, 1);
        board[7][2] = new Bishop("white", 7, 2);
        board[7][3] = new Queen("white", 7, 3);
        board[7][4] = new King("white", 7, 4);
        board[7][5] = new Bishop("white", 7, 5);
        board[7][6] = new Knight("white", 7, 6);
        board[7][7] = new Rook("white", 7, 7);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn("white", 6, i);
        }
    }

    public void displayBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(getPieceSymbol(board[row][col]) + " ");
                }
            }
            System.out.println();
        }
    }

    private String getPieceSymbol(Piece piece) {
        String symbol = "";
        if (piece instanceof King) symbol = "K";
        else if (piece instanceof Queen) symbol = "Q";
        else if (piece instanceof Rook) symbol = "R";
        else if (piece instanceof Bishop) symbol = "B";
        else if (piece instanceof Knight) symbol = "N";
        else if (piece instanceof Pawn) symbol = "P";
        return (piece.getColor().equals("white") ? symbol : symbol.toLowerCase());
    }

    public boolean movePiece(int startRow, int startCol, int targetRow, int targetCol) {        
        if (startRow < 0 || startRow >= 8 || startCol < 0 || startCol >= 8 ||
            targetRow < 0 || targetRow >= 8 || targetCol < 0 || targetCol >= 8) {
            return false; 
        }
        Piece piece = board[startRow][startCol];
        if (piece == null || !piece.isValidMove(targetRow, targetCol, board)) {
            return false;
        }
    
        if ((piece instanceof Rook) && (startCol == targetCol && startRow == targetRow)) {
            return false;
        }

        // En passant kezelés
        if (piece instanceof Pawn) {
            Pawn pawn = (Pawn) piece;
            if (Math.abs(targetCol - startCol) == 1 && board[targetRow][targetCol] == null) {
                int capturedRow = (pawn.getColor().equals("white")) ? targetRow + 1 : targetRow - 1;
                Piece capturedPiece = board[capturedRow][targetCol];
                if (capturedPiece instanceof Pawn && ((Pawn) capturedPiece).hasJustMovedTwoSteps()) {
                    board[capturedRow][targetCol] = null;
                }
            }
            pawn.setJustMovedTwoSteps(Math.abs(targetRow - startRow) == 2);
        }
    
        // Sáncolás kezelés
        if (piece instanceof King && Math.abs(targetCol - startCol) == 2) {
            int rookCol = targetCol > startCol ? 7 : 0;
            int newRookCol = targetCol > startCol ? 5 : 3;
            board[targetRow][newRookCol] = board[targetRow][rookCol];
            board[targetRow][rookCol] = null;
            board[targetRow][newRookCol].setPosition(targetRow, newRookCol);
        }
    
        board[targetRow][targetCol] = piece;
        board[startRow][startCol] = null;
        piece.setPosition(targetRow, targetCol);
    
        // Gyalog előléptetése
        if (piece instanceof Pawn && (targetRow == 0 || targetRow == 7)) {
            promotePawn((Pawn) piece, targetRow, targetCol);
        }
    
        resetPawnEnPassantStatus(targetRow, targetCol);
        return true;
    }
    
    private void resetPawnEnPassantStatus(int exceptRow, int exceptCol) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] instanceof Pawn && (row != exceptRow || col != exceptCol)) {
                    ((Pawn) board[row][col]).setJustMovedTwoSteps(false);
                }
            }
        }
    }    
    
    public boolean isSquareUnderAttack(int row, int col, String attackerColor) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return false; 
        }
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board[r][c];
                if (piece != null && piece.getColor().equals(attackerColor) &&
                    piece.isValidMove(row, col, board)) {
                    return true;
                }
            }
        }
        return false;
    }    

    public Piece getPieceAt(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return null;
        }
        return board[row][col];
    }

    private void promotePawn(Pawn pawn, int row, int col) {    
        Piece newPiece = new Queen(pawn.getColor(), row, col);
        board[row][col] = newPiece; 
    }    

    public boolean isKingInCheck(String kingColor) {
        int kingRow = -1, kingCol = -1;
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor().equals(kingColor)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
    
        return isSquareUnderAttack(kingRow, kingCol, kingColor.equals("white") ? "black" : "white");
    }
    
}