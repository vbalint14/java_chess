import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChessBoardGUI extends JFrame {
    public JPanel boardPanel;
    public JLabel[][] boardLabels;
    public Board gameBoard; 
    public int selectedRow = -1, selectedCol = -1;
    public String currentPlayer = "white"; 
    public boolean isSinglePlayerMode = false; 
    public boolean isZeroPlayerMode = false;  
    public AIPlayer aiPlayerWhite, aiPlayerBlack; 
    private boolean isAITurn = false;
    public JLabel stepCounterLabel; 
    public int stepCounter = 0;     
    public DefaultListModel<String> moveHistoryModel; 
    public JList<String> moveHistoryList;            

    public ChessBoardGUI() {
        this(false, false);  
    }

    public ChessBoardGUI(boolean isSinglePlayerMode, boolean isZeroPlayerMode) {
        this.isSinglePlayerMode = isSinglePlayerMode;
        this.isZeroPlayerMode = isZeroPlayerMode;
        gameBoard = new Board(); 

        setTitle("Chess Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true); 

        JPanel stepCounterPanel = new JPanel();
        stepCounterPanel.setPreferredSize(new Dimension(200, 800)); 
        stepCounterPanel.setLayout(new BorderLayout());
        stepCounterPanel.setBackground(Color.LIGHT_GRAY);

        stepCounterLabel = new JLabel("Steps: 0", SwingConstants.CENTER);
        stepCounterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        stepCounterPanel.add(stepCounterLabel, BorderLayout.CENTER);

        add(stepCounterPanel, BorderLayout.WEST); 

        JPanel historyPanel = new JPanel();
        historyPanel.setPreferredSize(new Dimension(200, 800));
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBackground(Color.LIGHT_GRAY);

        JLabel historyLabel = new JLabel("Move History", SwingConstants.CENTER);
        historyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        historyPanel.add(historyLabel, BorderLayout.NORTH);

        moveHistoryModel = new DefaultListModel<>();
        moveHistoryList = new JList<>(moveHistoryModel);
        moveHistoryList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane historyScrollPane = new JScrollPane(moveHistoryList);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        add(historyPanel, BorderLayout.EAST); 

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); 

        JButton forfeitButton = new JButton("Forfeit Game");
        forfeitButton.setPreferredSize(new Dimension(60, 20));
        forfeitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        forfeitButton.addActionListener(e -> handleForfeit());
        buttonPanel.add(forfeitButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton saveGameButton = new JButton("Save Game");
        saveGameButton.setPreferredSize(new Dimension(60, 20));
        saveGameButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        saveGameButton.addActionListener(e -> handleSaveGame());
        buttonPanel.add(saveGameButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton drawButton = new JButton("Offer Draw");
        drawButton.setPreferredSize(new Dimension(60, 20));
        drawButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        drawButton.addActionListener(e -> handleDrawOffer());
        if (!isSinglePlayerMode && !isZeroPlayerMode)
            buttonPanel.add(drawButton);
        

        add(buttonPanel, BorderLayout.SOUTH); 

        
        if (isZeroPlayerMode) {
            aiPlayerWhite = new AIPlayer("white", gameBoard); 
            aiPlayerBlack = new AIPlayer("black", gameBoard); 
        } else if (isSinglePlayerMode) {
            aiPlayerBlack = new AIPlayer("black", gameBoard); 
        }

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setSize(800, 800); 
        containerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 

        boardPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChessBoard(g);
            }
        };
        boardPanel.setLayout(null);
        boardPanel.setPreferredSize(new Dimension(640, 640)); 

        boardLabels = new JLabel[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardLabels[row][col] = new JLabel();
                boardLabels[row][col].setBounds(col * 80, row * 80, 80, 80); 
                boardLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                boardLabels[row][col].setVerticalAlignment(SwingConstants.CENTER);
                boardLabels[row][col].setOpaque(true);

                if ((row + col) % 2 == 0) {
                    boardLabels[row][col].setBackground(Color.WHITE);
                } else {
                    boardLabels[row][col].setBackground(new Color(139, 69, 19));
                }

                int finalRow = row;
                int finalCol = col;
                boardLabels[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleCellClick(finalRow, finalCol);
                    }
                });

                boardPanel.add(boardLabels[row][col]);
            }
        }

        containerPanel.add(boardPanel, BorderLayout.CENTER);
        add(containerPanel, BorderLayout.CENTER); 

        refreshBoard(); 
        setVisible(true);

        if (isZeroPlayerMode) {
            startAIvsAI();
        }
    }

    private void startAIvsAI() {
        new Thread(() -> {
            while (true) {
                if (currentPlayer.equals("white")) {
                    aiMove(aiPlayerWhite);
                } else {
                    aiMove(aiPlayerBlack);
                }
                checkGameState();
                if (onlyKingsAlive() && stepCounter % 5 == 0) {
                    handleDrawOffer();
                }
                currentPlayer = currentPlayer.equals("white") ? "black" : "white";
                try {
                    Thread.sleep(1500); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void aiMove(AIPlayer aiPlayer) {
        String aiColor = aiPlayer.getColor();
        if (gameBoard.isKingInCheck(aiColor)) {
            Piece king = findKing(aiColor);
            int kingRow = king.getRow();
            int kingCol = king.getCol();
            boolean moveFound = false;
            String attackerColor = aiColor.equals("white") ? "black" : "white";
            for (int row = 0; row < 8 && !moveFound; row++) {
                for (int col = 0; col < 8 && !moveFound; col++) {
                    if (king.isValidMove(row, col, gameBoard.getBoard()) &&
                        !gameBoard.isSquareUnderAttack(row, col, attackerColor)) {
                        gameBoard.movePiece(kingRow, kingCol, row, col);
                        addMoveToHistory(row + 1, col + 1, king);
                        moveFound = true; 
                    }
                }
            }
            if (moveFound == false) {
                JOptionPane.showMessageDialog(this, aiColor.toUpperCase() + " wins! " + attackerColor.toUpperCase() + "'s king has been Check-mated!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }        
        else {
            Move aiMove = aiPlayer.generateMove();
            if (aiMove != null) {
                Piece piece = gameBoard.getPieceAt(aiMove.getStartRow(), aiMove.getStartCol());
                gameBoard.movePiece(aiMove.getStartRow(), aiMove.getStartCol(), aiMove.getTargetRow(), aiMove.getTargetCol());
                addMoveToHistory(aiMove.getTargetRow()+1, aiMove.getTargetCol()+1, piece);
            }
        }
        refreshBoard();
        updateStepCounter();
    }

    private void handleCellClick(int row, int col) {
        if (isZeroPlayerMode) {
            return; 
        }
    
        if (selectedRow == -1 && selectedCol == -1) {
            Piece selectedPiece = gameBoard.getPieceAt(row, col);
    
            if (selectedPiece != null && selectedPiece.getColor().equals(currentPlayer)) {
                selectedRow = row;
                selectedCol = col;
                boardLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 10)); 
                highlightValidMoves(row, col); 
            } else {
                if (selectedPiece != null && !selectedPiece.getColor().equals(currentPlayer)) {
                    JOptionPane.showMessageDialog(this, "It's " + currentPlayer + "'s turn!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            clearHighlights(); 
            if (row == selectedRow && col == selectedCol) {
                boardLabels[selectedRow][selectedCol].setBorder(null);
                selectedRow = -1;
                selectedCol = -1;
            } else {
                if (gameBoard.movePiece(selectedRow, selectedCol, row, col)) {
                    Piece piece = gameBoard.getPieceAt(row, col); 
                    addMoveToHistory(row+1, col+1, piece); 
                    updateStepCounter();
                    refreshBoard();
                    checkGameState();
                    if (onlyKingsAlive() && stepCounter % 5 == 0) {
                        handleDrawOffer();
                    }
                    boardLabels[selectedRow][selectedCol].setBorder(null);
                    selectedRow = -1;
                    selectedCol = -1;
    
                    if (isSinglePlayerMode && currentPlayer.equals("white")) {
                        currentPlayer = "black";
                        new Thread(() -> {
                            try {
                                Thread.sleep(1500); 
                                aiMove(aiPlayerBlack); 
                                SwingUtilities.invokeLater(() -> {
                                    refreshBoard(); 
                                    checkGameState();
                                    if (onlyKingsAlive() && stepCounter % 5 == 0) {
                                        handleDrawOffer();
                                    }
                                    currentPlayer = "white"; 
                                });
                            } catch (Exception e) {
                                System.err.println("Error during AI move: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }).start();
                    } else {
                        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid move!", "Error", JOptionPane.ERROR_MESSAGE);
                    selectedCol = -1;
                    selectedRow = -1;
                }
            }
        }
    }
    
    public void checkGameState() {
        String opponent = currentPlayer.equals("white") ? "black" : "white";
        Piece opponentKing = findKing(opponent);
        if (opponentKing == null) {
            JOptionPane.showMessageDialog(this, currentPlayer.toUpperCase() + " wins! " + opponent.toUpperCase() + "'s king has been defeated!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (gameBoard.isKingInCheck(opponent)) {
            JOptionPane.showMessageDialog(this, opponent.toUpperCase() + "'s king is in check!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        } else if (isStalemate(currentPlayer)) {
            JOptionPane.showMessageDialog(this, "The game is a draw by stalemate!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); 
        } 
    }

    public void refreshBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = gameBoard.getPieceAt(row, col);
                if (piece != null) {
                    boardLabels[row][col].setIcon(getPieceIcon(piece));
                } else {
                    boardLabels[row][col].setIcon(null);
                }
            }
        }
    }

    private ImageIcon getPieceIcon(Piece piece) {
        if (piece instanceof King) {
            return piece.getColor().equals("white") ? new ImageIcon("images\\white_king.png")
                    : new ImageIcon("images\\black_king.png");
        } else if (piece instanceof Queen) {
            return piece.getColor().equals("white") ? new ImageIcon("images\\white_queen.png")
                    : new ImageIcon("images\\black_queen.png");
        } else if (piece instanceof Rook) {
            return piece.getColor().equals("white") ? new ImageIcon("images\\white_rook.png")
                    : new ImageIcon("images\\black_rook.png");
        } else if (piece instanceof Bishop) {
            return piece.getColor().equals("white") ? new ImageIcon("images\\white_bishop.png")
                    : new ImageIcon("images\\black_bishop.png");
        } else if (piece instanceof Knight) {
            return piece.getColor().equals("white") ? new ImageIcon("images\\white_knight.png")
                    : new ImageIcon("images\\black_knight.png");
        } else if (piece instanceof Pawn) {
            return piece.getColor().equals("white") ? new ImageIcon("images\\white_pawn.png")
                    : new ImageIcon("images\\black_pawn.png");
        }
        return null;
    }

    public Piece findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = gameBoard.getPieceAt(row, col);
                if (piece != null && piece instanceof King && piece.getColor().equals(color)) {
                    return piece; 
                }
            }
        }
        return null; 
    }

    private void drawChessBoard(Graphics g) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                g.setColor((row + col) % 2 == 0 ? Color.WHITE : new Color(139, 69, 19));
                g.fillRect(col * 80, row * 80, 80, 80);
            }
        }
    }

    private void handleForfeit() {
        String opponent = currentPlayer.equals("white") ? "black" : "white";
        JOptionPane.showMessageDialog(this, currentPlayer.toUpperCase() + " has forfeited! " +
                opponent.toUpperCase() + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); 
    }
    
    private void handleSaveGame() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saved_game.dat"));
            out.writeObject(gameBoard);           
            out.writeObject(currentPlayer);       
            out.writeBoolean(isSinglePlayerMode); 
            out.writeBoolean(isZeroPlayerMode);   
            out.writeInt(stepCounter);            
            out.writeObject(moveHistoryModel);    
            out.close();
    
            JOptionPane.showMessageDialog(this, "Game saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to save the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void updateStepCounter() {
        stepCounter++;
        stepCounterLabel.setText("Steps: " + stepCounter);
    }

    private void addMoveToHistory(int targetRow, int targetCol, Piece piece) {
        String move = piece.getColor().toUpperCase() + " " + piece.getClass().getSimpleName() + " " + "-> (" + targetRow + ", " + targetCol + ")";
        moveHistoryModel.addElement(move); 
        moveHistoryList.ensureIndexIsVisible(moveHistoryModel.getSize() - 1); 
    }

    public void handleLoadGame() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("saved_game.dat"));
            gameBoard = (Board) in.readObject();
            currentPlayer = (String) in.readObject();
            isSinglePlayerMode = in.readBoolean();
            isZeroPlayerMode = in.readBoolean();
            stepCounter = in.readInt();
            moveHistoryModel = (DefaultListModel<String>) in.readObject();
            in.close();

            refreshBoard();
            if (isZeroPlayerMode) {
                aiPlayerWhite = new AIPlayer("white", gameBoard);
                aiPlayerBlack = new AIPlayer("black", gameBoard);
            } else if (isSinglePlayerMode) {
                aiPlayerBlack = new AIPlayer("black", gameBoard);
            }
            JOptionPane.showMessageDialog(this, "Game loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private boolean isStalemate(String playerColor) {
        if (gameBoard.isKingInCheck(playerColor)) {
            return false; 
        }
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = gameBoard.getPieceAt(row, col);
                if (piece != null && piece.getColor().equals(playerColor)) {
                    for (int targetRow = 0; targetRow < 8; targetRow++) {
                        for (int targetCol = 0; targetCol < 8; targetCol++) {
                            if (piece.isValidMove(targetRow, targetCol, gameBoard.getBoard())) {
                                return false; 
                            }
                        }
                    }
                }
            }
        }
    
        return true; 
    }    

    private void highlightValidMoves(int row, int col) {
        Piece piece = gameBoard.getPieceAt(row, col);
        if (piece == null) return;
        for (int targetRow = 0; targetRow < 8; targetRow++) {
            for (int targetCol = 0; targetCol < 8; targetCol++) {
                if (piece.isValidMove(targetRow, targetCol, gameBoard.getBoard())) {
                    boardLabels[targetRow][targetCol].setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                }
            }
        }
    }
    
    private void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardLabels[row][col].setBorder(null); 
            }
        }
    }

    private void handleDrawOffer() {
        int response = JOptionPane.showConfirmDialog(
            this, 
            "Do you accept the draw offer?", 
            "Draw Offer", 
            JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "The game is a draw!");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(this, "Draw offer declined.");
        }
    }

    private boolean onlyKingsAlive() {
        int kingCount = 0;
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = gameBoard.getPieceAt(row, col); 
                if (piece != null) {
                    if (piece instanceof King) { 
                        kingCount++;
                    } else {
                        return false;
                    }
                }
            }
        }
    
        return kingCount == 2;
    }    

    public static void main(String[] args) {
        new ChessBoardGUI();
    }
}
