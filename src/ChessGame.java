import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ChessGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Traditional Chess Game Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setMinimumSize(new Dimension(400, 300));
            frame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.NONE; 
            gbc.insets = new Insets(10, 10, 10, 10); 

            JLabel title = new JLabel("Traditional Chess Game", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 28)); 
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0.2; 
            frame.add(title, gbc);

            JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

            Dimension buttonSize = new Dimension(200, 40);

            JButton newGameButton = new JButton("New Game");
            newGameButton.setFont(new Font("Arial", Font.PLAIN, 16));
            newGameButton.setPreferredSize(buttonSize);
            newGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] options = {"0 player mode (AI vs AI)", "1 player mode (AI vs Player)", "2 player mode (Player vs Player)"};
                    int choice = JOptionPane.showOptionDialog(frame, "Choose a mode:", "New Game",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    
                    if (choice == 0) {
                        new ChessBoardGUI(false, true); 
                    } else if (choice == 1) {
                        try {
                            SwingUtilities.invokeLater(() -> {
                                new ChessBoardGUI(true,false); 
                            });
                            frame.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Hiba történt a játék indítása közben.",
                                    "Hiba", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else if (choice == 2) {
                        try {
                            SwingUtilities.invokeLater(() -> {
                                new ChessBoardGUI(); 
                            });
                            frame.dispose(); 
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Hiba történt a játék indítása közben.",
                                    "Hiba", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                }
            });
            buttonPanel.add(newGameButton);

            JButton loadGameButton = new JButton("Load Game");
            loadGameButton.setFont(new Font("Arial", Font.PLAIN, 16));
            loadGameButton.setPreferredSize(buttonSize);
            loadGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("saved_game.dat"));
                        Board savedBoard = (Board) in.readObject();       
                        String savedPlayer = (String) in.readObject();    
                        boolean savedSinglePlayerMode = in.readBoolean(); 
                        boolean savedZeroPlayerMode = in.readBoolean();   
                        int savedStepCounter = in.readInt();              
                        DefaultListModel<String> savedMoveHistory = (DefaultListModel<String>) in.readObject();
                        in.close();
            
                        ChessBoardGUI loadedGame = new ChessBoardGUI(savedSinglePlayerMode, savedZeroPlayerMode);
                        loadedGame.gameBoard = savedBoard;                
                        loadedGame.currentPlayer = savedPlayer;           
                        loadedGame.stepCounter = savedStepCounter;        
                        loadedGame.moveHistoryModel = savedMoveHistory;            
                        loadedGame.moveHistoryList.setModel(savedMoveHistory);
                        loadedGame.refreshBoard();                        
                        loadedGame.stepCounterLabel.setText("Steps: " + savedStepCounter); 
                        if (savedZeroPlayerMode) {
                            loadedGame.aiPlayerWhite = new AIPlayer("white", loadedGame.gameBoard);
                            loadedGame.aiPlayerBlack = new AIPlayer("black", loadedGame.gameBoard);
                        } else if (savedSinglePlayerMode) {
                            loadedGame.aiPlayerBlack = new AIPlayer("black", loadedGame.gameBoard);
                        }
                    
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to load the game: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            });
            buttonPanel.add(loadGameButton);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0.8; 
            frame.add(buttonPanel, gbc);

            frame.setVisible(true);
        });
    }
}
