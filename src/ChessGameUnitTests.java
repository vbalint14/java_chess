
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;
// import game.King;

// public class ChessGameUnitTests {
    
//     @Test // 1
//     public static void testKingValidMove() {
//         Piece[][] board = new Piece[8][8];
//         King king = new King("white", 4, 4);
//         board[4][4] = king;

//         assertTrue(king.isValidMove(3, 4, board)); 
//         assertTrue(king.isValidMove(5, 4, board)); 
//         assertTrue(king.isValidMove(4, 3, board)); 
//         assertTrue(king.isValidMove(4, 5, board)); 
//         assertTrue(king.isValidMove(3, 3, board)); 
//         assertTrue(king.isValidMove(3, 5, board)); 
//         assertTrue(king.isValidMove(5, 3, board)); 
//         assertTrue(king.isValidMove(5, 5, board)); 

//         assertFalse(king.isValidMove(8, 4, board)); 

//         board[5][5] = new Pawn("white", 5, 5);
//         assertFalse(king.isValidMove(5, 5, board));

//         board[3][3] = new Pawn("black", 3, 3);
//         assertTrue(king.isValidMove(3, 3, board));

//         assertFalse(king.isValidMove(6, 4, board));

//         Rook rook = new Rook("white", 4, 7);
//         board[4][7] = rook;
//         assertTrue(king.isValidMove(4, 6, board)); 

//         board[4][5] = new Pawn("white", 4, 5);
//         assertFalse(king.isValidMove(4, 6, board)); 
//         board[4][5] = null; 
//         board[4][6] = null; 
//         board[4][3] = new Rook("black", 4, 3);
//         assertFalse(king.isValidMove(4, 6, board)); 
//     }

//     @Test // 2
//     public static void testFindKing() {
//         GameBoard gameBoard = new GameBoard(); 

//         King whiteKing = new King("white", 0, 4);
//         King blackKing = new King("black", 7, 4);
//         gameBoard.setPieceAt(0, 4, whiteKing);
//         gameBoard.setPieceAt(7, 4, blackKing);

//         Piece foundWhiteKing = gameBoard.findKing("white");
//         assertNotNull(foundWhiteKing);
//         assertTrue(foundWhiteKing instanceof King);
//         assertEquals("white", foundWhiteKing.getColor());
//         assertEquals(0, foundWhiteKing.getRow());
//         assertEquals(4, foundWhiteKing.getCol());

//         Piece foundBlackKing = gameBoard.findKing("black");
//         assertNotNull(foundBlackKing);
//         assertTrue(foundBlackKing instanceof King);
//         assertEquals("black", foundBlackKing.getColor());
//         assertEquals(7, foundBlackKing.getRow());
//         assertEquals(4, foundBlackKing.getCol());

//         gameBoard.setPieceAt(7, 4, null); 
//         assertNull(gameBoard.findKing("black"));

//         gameBoard.setPieceAt(0, 4, null);
//         assertNull(gameBoard.findKing("white"));
//     }

//     @Test // 3
//     public static void testGetPieceIcon() {
//         Piece whiteKing = new King("white", 0, 4);
//         Piece blackKing = new King("black", 7, 4);
//         Piece whiteQueen = new Queen("white", 0, 3);
//         Piece blackQueen = new Queen("black", 7, 3);
//         Piece whiteRook = new Rook("white", 0, 0);
//         Piece blackRook = new Rook("black", 7, 7);
//         Piece whiteBishop = new Bishop("white", 0, 2);
//         Piece blackBishop = new Bishop("black", 7, 2);
//         Piece whiteKnight = new Knight("white", 0, 1);
//         Piece blackKnight = new Knight("black", 7, 6);
//         Piece whitePawn = new Pawn("white", 1, 0);
//         Piece blackPawn = new Pawn("black", 6, 0);

//         assertEquals("images\\white_king.png", getPieceIcon(whiteKing).toString());
//         assertEquals("images\\black_king.png", getPieceIcon(blackKing).toString());
//         assertEquals("images\\white_queen.png", getPieceIcon(whiteQueen).toString());
//         assertEquals("images\\black_queen.png", getPieceIcon(blackQueen).toString());
//         assertEquals("images\\white_rook.png", getPieceIcon(whiteRook).toString());
//         assertEquals("images\\black_rook.png", getPieceIcon(blackRook).toString());
//         assertEquals("images\\white_bishop.png", getPieceIcon(whiteBishop).toString());
//         assertEquals("images\\black_bishop.png", getPieceIcon(blackBishop).toString());
//         assertEquals("images\\white_knight.png", getPieceIcon(whiteKnight).toString());
//         assertEquals("images\\black_knight.png", getPieceIcon(blackKnight).toString());
//         assertEquals("images\\white_pawn.png", getPieceIcon(whitePawn).toString());
//         assertEquals("images\\black_pawn.png", getPieceIcon(blackPawn).toString());

//         assertNull(getPieceIcon(null));
//     }

//     @Test // 4
//     public static void testOnlyKingsAlive() {
//         Board gameBoard = new GameBoard();

//         gameBoard.clearBoard();
//         Piece whiteKing = new King("white", 0, 4);
//         Piece blackKing = new King("black", 7, 4);
//         gameBoard.setPieceAt(0, 4, whiteKing);
//         gameBoard.setPieceAt(7, 4, blackKing);
//         assertTrue(onlyKingsAlive());

//         gameBoard.setPieceAt(1, 0, new Pawn("white", 1, 0));
//         assertFalse(onlyKingsAlive());

//         gameBoard.clearBoard();
//         gameBoard.setPieceAt(0, 4, whiteKing);
//         assertFalse(onlyKingsAlive());

//         gameBoard.clearBoard();
//         gameBoard.setPieceAt(0, 4, whiteKing);
//         gameBoard.setPieceAt(7, 4, blackKing);
//         gameBoard.setPieceAt(4, 4, new King("black", 4, 4));
//         assertFalse(onlyKingsAlive());

//     }

//     @Test // 5
//     public static void testUpdateStepCounter() {
//         int stepCounter = 0;
//         Jlabel stepCounterLabel = new JLabel("Steps: " + stepCounter);
//         updateStepCounter();
//         assertEquals(1, stepCounter);
//         assertEquals("Steps: 1", stepCounterLabel.getText());

//         updateStepCounter();
//         assertEquals(2, stepCounter);
//         assertEquals("Steps: 2", stepCounterLabel.getText());
//     }

//     @Test // 6
//     public static void testSquareUnderAttack() {
//         Piece[][] board = new Piece[8][8];
//         Queen whiteQueen = new Queen("white", 4, 4);
//         board[4][4] = whiteQueen;

//         assertTrue(isSquareUnderAttack(4, 7, "white")); 
//         assertTrue(isSquareUnderAttack(7, 4, "white")); 
//         assertTrue(isSquareUnderAttack(6, 6, "white")); 

//         assertFalse(isSquareUnderAttack(6, 5, "white")); 

//         Rook blackRook = new Rook("black", 0, 0);
//         board[0][0] = blackRook;
//         assertTrue(isSquareUnderAttack(0, 7, "black")); 
//         assertTrue(isSquareUnderAttack(7, 0, "black")); 

//         assertFalse(isSquareUnderAttack(-1, 0, "white"));
//         assertFalse(isSquareUnderAttack(8, 8, "black"));

//         Pawn blockingPawn = new Pawn("white", 4, 5);
//         board[4][5] = blockingPawn;
//         assertFalse(isSquareUnderAttack(4, 7, "white")); 
//     }

//     @Test // 7
//     public static void testPromotePawnToQueen() {
//         Piece[][] board = new Piece[8][8];
//         Pawn whitePawn = new Pawn("white", 6, 0);
//         board[6][0] = whitePawn;

//         promotePawn(whitePawn, 6, 0);

//         Piece promotedPiece = board[6][0];
//         assertNotNull(promotedPiece);
//         assertTrue(promotedPiece instanceof Queen);
//         assertEquals("white", promotedPiece.getColor());
//         assertEquals(6, promotedPiece.getRow());
//         assertEquals(0, promotedPiece.getCol());
//     }

//     @Test // 8
//     public static void testKingInCheck() {
//         Piece[][] board = new Piece[8][8];
//         King whiteKing = new King("white", 4, 4);
//         Rook blackRook = new Rook("black", 4, 7);
//         board[4][4] = whiteKing;
//         board[4][7] = blackRook;

//         assertTrue(isKingInCheck("white"));
//         assertFalse(isKingInCheck("black"));
//     }

//     @Test // 9
//     public static void testInitialBoardSetup() {
//         Piece[][] board = new Piece[8][8];
//         assertTrue(board[0][0] instanceof Rook);
//         assertTrue(board[0][1] instanceof Knight);
//         assertTrue(board[0][2] instanceof Bishop);
//         assertTrue(board[0][3] instanceof Queen);
//         assertTrue(board[0][4] instanceof King);
//         assertTrue(board[0][5] instanceof Bishop);
//         assertTrue(board[0][6] instanceof Knight);
//         assertTrue(board[0][7] instanceof Rook);
//         for (int i = 0; i < 8; i++) {
//             assertTrue(board[1][i] instanceof Pawn);
//             assertEquals("black", board[1][i].getColor());
//         }

//         assertTrue(board[7][0] instanceof Rook);
//         assertTrue(board[7][1] instanceof Knight);
//         assertTrue(board[7][2] instanceof Bishop);
//         assertTrue(board[7][3] instanceof Queen);
//         assertTrue(board[7][4] instanceof King);
//         assertTrue(board[7][5] instanceof Bishop);
//         assertTrue(board[7][6] instanceof Knight);
//         assertTrue(board[7][7] instanceof Rook);
//         for (int i = 0; i < 8; i++) {
//             assertTrue(board[6][i] instanceof Pawn);
//             assertEquals("white", board[6][i].getColor());
//         }
//     }

//     @Test // 10
//     public static void testGetPieceAtValidPosition() {
//         Piece[][] board = new Piece[8][8];
//         initializeBoard();
//         Piece pieceAt00 = board[0][0];
//         assertTrue(pieceAt00 instanceof Rook);
//         assertEquals("black", pieceAt00.getColor());

//         Piece pieceAt76 = board[7][6];
//         assertTrue(pieceAt76 instanceof Knight);
//         assertEquals("white", pieceAt76.getColor());

//         Piece pieceAt33 = board[3][3];
//         assertNull(pieceAt33); 
//     }
    
//     public static void main (String[] args) {
//         testKingValidMove();
//         testFindKing();
//         testGetPieceIcon();
//         testOnlyKingsAlive();
//         testUpdateStepCounter();
//         testSquareUnderAttack();
//         testPromotePawnToQueen();
//         testKingInCheck();
//         testInitialBoardSetup();
//         testGetPieceAtValidPosition();
//     }
// }
