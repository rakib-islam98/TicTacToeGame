package tictactoegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;



/**
 *
 * @author iraki
 */
public class TicTacToe extends JPanel {
    
    final char board[][];
    final JButton buttons[][];
    boolean isPlayerXTurn;
    final JPanel top,grid;
    final JLabel turnLabel,scoreX,scoreO;
    private final JButton backButton;
    private final Image gameBackground;
    int winX,winO;
    
    public TicTacToe(ActionListener backButtonAction)
    {
        board=new char[3][3];
        buttons=new JButton[3][3];
        isPlayerXTurn=true;
        winX=0;winO=0;
               
        turnLabel=new JLabel("Turn: Player X",SwingConstants.CENTER);
        grid=new JPanel(new GridLayout(3,3));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        
        Image img=new ImageIcon(getClass().getResource("/images/BackButtonIcon.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        backButton=new JButton(new ImageIcon(img));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        
        scoreX=new JLabel(" Player X: "+winX+" ");
        scoreO=new JLabel(" Player O: "+winO+" ");
        gameBackground=new ImageIcon(getClass().getResource("/images/GameBackground.jpg")).getImage();
        
        backButton.addActionListener(backButtonAction);
        
        JPanel backPanel=new JPanel(new BorderLayout());
        backPanel.add(backButton,BorderLayout.EAST);
        top=new JPanel(new BorderLayout());
        top.add(backPanel,BorderLayout.NORTH);
        top.add(turnLabel,BorderLayout.CENTER);
        
        JPanel scorePanel=new JPanel(new BorderLayout());
        scorePanel.add(scoreX,BorderLayout.EAST);
        scorePanel.add(scoreO,BorderLayout.WEST);
        
        JPanel bottom=new JPanel(new BorderLayout());
        bottom.add(grid,BorderLayout.CENTER);
        bottom.add(scorePanel,BorderLayout.SOUTH);
        
        turnLabel.setFont(new Font("Curlz MT",Font.BOLD,30));
        turnLabel.setForeground((isPlayerXTurn)?Color.red:Color.blue);
        
        scoreX.setFont(new Font("Curlz MT",Font.BOLD,25));
        scoreX.setForeground(Color.red);
        scoreX.setBackground(Color.orange);
        scoreX.setOpaque(true);
        
        scoreO.setFont(new Font("Curlz MT",Font.BOLD,25));
        scoreO.setForeground(Color.blue);
        scoreO.setBackground(Color.orange);
        scoreO.setOpaque(true);

        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 50, 40));
        
        backPanel.setOpaque(false);
        scorePanel.setOpaque(false);
        grid.setOpaque(false);
        top.setOpaque(false);
        bottom.setOpaque(false);
        
        setLayout(new BorderLayout());
        add(top,BorderLayout.NORTH);
        add(bottom,BorderLayout.CENTER);
        
        initializeBoard();
        initializeButtons();
    }
    
    private void initializeBoard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                board[i][j]='_';
            }
        }
    }
    
    void initializeButtons()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                buttons[i][j]=new JButton("");
                final int row=i,col=j;
                buttons[i][j].addActionListener(e -> handlePlayerMove(row,col));
                buttons[i][j].setBackground(Color.black);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.cyan, 1));
                buttons[i][j].setFont(new Font("Comic Sans MS",Font.BOLD,120));
                buttons[i][j].setFocusPainted(false);
                grid.add(buttons[i][j]);
            }
        }
    }
    
    void handlePlayerMove(int row,int col)
    {
        if (board[row][col] == '_') 
        {
            if(isPlayerXTurn)
                buttons[row][col].setForeground(Color.red);
            else
                buttons[row][col].setForeground(Color.blue);
            
            board[row][col] = isPlayerXTurn ? 'X' : 'O';
            buttons[row][col].setText(isPlayerXTurn ? "X" : "O");
            if (isGameOver()) {
                return;
            }
            
            isPlayerXTurn = !isPlayerXTurn;
            if(isPlayerXTurn){ turnLabel.setText("Turn: Player X"); turnLabel.setForeground(Color.red);}
            else
            {    turnLabel.setText("Turn: Player O"); turnLabel.setForeground(Color.blue);}
        }
    }
    
    boolean isGameOver()
    {
        if (checkWinner('X')) 
        {
            winX++;scoreX.setText(" Player X: "+winX+" ");
            int option=showConfirmationDialog("Player X Wins!","Game Over",new String[]{"Play Again", "Reset Score"},"Play Again");
            if(option==JOptionPane.YES_OPTION || option==-1) { resetGame();}
            else if(option==JOptionPane.NO_OPTION) { newGame();}
            return true;
        } 
        else if (checkWinner('O')) 
        {
            winO++;scoreO.setText(" Player O: "+winO+" ");
            int option=showConfirmationDialog("Player O Wins!","Game Over",new String[]{"Play Again", "Reset Score"},"Play Again");
            if(option==JOptionPane.YES_OPTION || option==-1) { resetGame();}
            else if(option==JOptionPane.NO_OPTION) { newGame();}
            return true;
        } 
        else if (isBoardFull()) 
        {
            int option=showConfirmationDialog("It's a draw!","Game Over",new String[]{"Play Again", "Reset Score"},"Play Again");
            if(option==JOptionPane.YES_OPTION || option==-1) { resetGame();}
            else if(option==JOptionPane.NO_OPTION) { newGame();}
            return true;
        }
        return false;
    }
    
    int showConfirmationDialog(String message, String title, String[] options, String defaultOption) {
        designJOptionPane();
        return JOptionPane.showOptionDialog(
                this,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                defaultOption
        );
    }
    
    boolean checkWinner(char symbol)
    {
        // Check rows, columns, and diagonals
        Color lightRed = new Color(255, 102, 102);
        Color lightBlue = new Color(102, 102, 255);
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol)
            {
                buttons[i][0].setBackground(symbol=='X'?lightRed:lightBlue);
                buttons[i][1].setBackground(symbol=='X'?lightRed:lightBlue);
                buttons[i][2].setBackground(symbol=='X'?lightRed:lightBlue);
                return true;
            }
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)
            {
                buttons[0][i].setBackground(symbol=='X'?lightRed:lightBlue);
                buttons[1][i].setBackground(symbol=='X'?lightRed:lightBlue);
                buttons[2][i].setBackground(symbol=='X'?lightRed:lightBlue);
                return true;
            }
        }
        //Diagonal
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
        {
            buttons[0][0].setBackground(symbol=='X'?lightRed:lightBlue);
            buttons[1][1].setBackground(symbol=='X'?lightRed:lightBlue);
            buttons[2][2].setBackground(symbol=='X'?lightRed:lightBlue);
            return true;
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)
        {
            buttons[0][2].setBackground(symbol=='X'?lightRed:lightBlue);
            buttons[1][1].setBackground(symbol=='X'?lightRed:lightBlue);
            buttons[2][0].setBackground(symbol=='X'?lightRed:lightBlue);
            return true;
        }
        return false;
    }
    
    boolean isBoardFull() 
    {
        for (char[] row : board)
            for (char cell : row)
                if (cell == '_') 
                    return false;
        return true;
    }
    
    void resetGame() 
    {
        initializeBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.black);
            }
        }
    }
    void newGame()
    {
        resetGame();
        winX=0;winO=0;
        isPlayerXTurn=true;
        turnLabel.setText("Turn: Player X");
        turnLabel.setForeground((isPlayerXTurn)?Color.red:Color.blue);
        scoreX.setText(" Player X: "+winX+" ");
        scoreO.setText(" Player O: "+winO+" ");
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(gameBackground!=null)
        {
            Graphics2D g2d = (Graphics2D) g;

            // Enable high-quality image rendering
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(gameBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
    void designJOptionPane()
    {
        UIManager.put("OptionPane.messageFont", new Font("Comic Sans MS", Font.BOLD, 14));
        UIManager.put("OptionPane.messageForeground", Color.red);
        UIManager.put("OptionPane.buttonFont", new Font("Comic Sans MS", Font.BOLD, 12));
        UIManager.put("OptionPane.background",Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Button.background", Color.CYAN);
    }
}
