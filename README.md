# Chess Game

## 1. Game Modes
The user can try different game modes in the chess program, namely:

- **0-player mode**: Two AIs* play against each other.
- **1-player mode**: One AI and one human player play against each other.
- **2-player mode**: Two human players play against each other.

> *(AI = computer-controlled player)*

## 2. Game Rules

### a) Objective of the Game
Chess is a strategic game between two players, aiming to checkmate the opponent’s king.  
Checkmate is a situation where the king is under attack and there is no legal move to escape the attack.

### b) Gameplay
- Players take turns during the game. White moves first, then Black follows.
- Each turn, a player can move one piece on the board according to the rules.

### c) Pieces and Their Movement
The game is played between two players, one controlling the white pieces and the other the black pieces.  
Each player has the following pieces:
- 1 King
- 1 Queen
- 2 Rooks
- 2 Bishops
- 2 Knights
- 8 Pawns

The pieces are placed on an 8x8 chessboard in the starting position: the white pieces are on the bottom two ranks, and the black pieces are on the top two ranks.

The kings are placed in the center, with queens next to them, and the other pieces positioned outward toward the board’s edges.

#### i. King:
- The king can move one square in any direction (vertically, horizontally, or diagonally).
- The king cannot move to a square that is under attack (i.e., would place itself in check).
- **Castling**: A special move involving the king and a rook. It can only be performed if neither the king nor the rook has moved before, the king is not in check, and there are no pieces between them. The king moves two squares toward the rook, and the rook moves next to the king.

#### ii. Queen:
- The queen can move any number of squares in any direction (vertically, horizontally, or diagonally).
- It cannot jump over other pieces.

#### iii. Rook:
- The rook can move any number of squares horizontally or vertically.
- It cannot jump over other pieces.
- The rook participates in castling with the king.

#### iv. Bishop:
- The bishop moves diagonally any number of squares.
- It only moves on squares of its starting color (one bishop on light squares, the other on dark squares).
- It cannot jump over other pieces.

#### v. Knight:
- The knight moves in an "L" shape: two squares in one direction (vertically or horizontally) and then one square perpendicularly.
- The knight is the only piece that can jump over other pieces.

#### vi. Pawn:
- The pawn moves forward one square (or two squares on its first move).
- It captures diagonally (one square forward to the left or right).
- **En passant**: If an opposing pawn moves two squares forward from its starting position and lands next to the player’s pawn, it can be captured as if it had moved only one square forward.
- **Promotion**: When a pawn reaches the opposite side of the board, it can be promoted to any other piece (queen, rook, bishop, or knight).

### d) Check and Checkmate
- **Check**: A player places the opponent’s king under attack. The opponent must immediately make a move to remove the threat (by moving the king, capturing the attacking piece, or blocking the attack).
- **Checkmate**: If the king is in check and there is no legal move to escape it, the game ends, and the player suffering checkmate loses.

### e) Draws
- **Stalemate**: When a player has no legal moves but is not in check, the game ends in a draw.
- **Draw Offer**: If only the two kings remain, an automatic draw can be declared.
- **Agreement**: Players can agree to a draw by mutual consent.

### f) Resignation
- A player can resign at any time if they believe they cannot win. In this case, the opponent wins.

## 3. Program Features

### a) Graphical User Interface (GUI)
- **Board and Pieces Display**: The chessboard and pieces are color-coded (light and dark squares, light and dark pieces).
- **Highlighting Movable Pieces**: The program visually highlights the possible moves for a selected piece, for example, with dots or colored squares.
- **Game Mode Selection**: The start menu allows choosing the game mode (0-, 1-, or 2-player) and starting a new game or loading a saved game.

### b) Saving and Loading
- **File Format and Save Structure**: The game state is saved in a file (e.g., `.json` or `.txt`) containing the current board state, move count, and player types.
- **Loading**: Saved games can be loaded and continued from where they left off.

### c) AI Gameplay
- **AI Levels and Settings**: The AI can perform simple random moves or optionally use more complex strategies.

### d) Error Handling
- **Handling Invalid Moves**: The program warns the player if an invalid move is attempted and does not execute it.
- **Automatic Checkmate and Stalemate Detection**: The program automatically checks for checkmate or stalemate after every move and notifies the players if such a situation occurs.

### e) Move Counter and Move History
- **Recording Moves**: Moves are recorded in a side panel or a separate menu, allowing players to review previous moves, which is useful for practice and learning.

---
