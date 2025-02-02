package tictactoegame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


/**
 * @author iraki
 */
public class TicTacToeMain extends JFrame{
    
    private final CardLayout cl;
    private final JPanel mainPanel;
    private final TicTacToe gameBoard;
    private final SinglePlayerTicTacToe singleGameBoard;
    
    public TicTacToeMain()
    {
        super("Tic Tac Toe");
        
        cl=new CardLayout();
        //Layout of mainPanel is set to card layout
        //Differnt game screen such as Main menu,Level Selection and Game board added to mainPanel
        mainPanel=new JPanel(cl);
        
        MainMenu mainMenu=new MainMenu(e -> showSinglePlayerMode(), e -> showMultiPlayerMode());
        LevelSelection levelMenu=new LevelSelection(e -> setEasyButton(),e -> setMediumButton(),
                                                    e -> setHardButton(),e -> showMainMenu());
        gameBoard=new TicTacToe(e -> multiPlayerBackButton());
        singleGameBoard=new SinglePlayerTicTacToe(e -> singlePlayerBackButton());
        
        mainPanel.add("mainMenu",mainMenu);
        mainPanel.add("gameBoard",gameBoard);
        mainPanel.add("singleGameBoard",singleGameBoard);
        mainPanel.add("levelMenu",levelMenu);
        
        add(mainPanel);
        super.setIconImage(new ImageIcon(getClass().getResource("/images/IconImage.png")).getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
    }
    
    private void setEasyButton()
    {
        singleGameBoard.setDifficulty(Difficulty.EASY);
        cl.show(mainPanel,"singleGameBoard");
    }
    
    private void setMediumButton()
    {
        singleGameBoard.setDifficulty(Difficulty.MEDIUM);
        cl.show(mainPanel,"singleGameBoard");
    }
    
    private void setHardButton()
    {
        singleGameBoard.setDifficulty(Difficulty.HARD);
        cl.show(mainPanel,"singleGameBoard");
    }
    
    private void showMainMenu()
    {
        cl.show(mainPanel,"mainMenu");
    }
    
    private void showSinglePlayerMode()
    {
        cl.show(mainPanel, "levelMenu");
    }
    
    private void showMultiPlayerMode()
    {
        cl.show(mainPanel, "gameBoard");
    }
    
    private void multiPlayerBackButton()
    {
        designJOptionPane();
        int option=JOptionPane.showOptionDialog(this, "Exit to Main Menu","Confirm Exit",
                JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,new String[]{"Yes","No"},"No");
        if(option==JOptionPane.YES_OPTION)
        {
            gameBoard.newGame();
            cl.show(mainPanel, "mainMenu");
        }
    }
    
    private void singlePlayerBackButton()
    {
        designJOptionPane();
        int option=JOptionPane.showOptionDialog(this, "Exit to Main Menu","Confirm Exit",
                JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,new String[]{"Yes","No"},"No");
        if(option==JOptionPane.YES_OPTION)
        {
            singleGameBoard.newGame();
            cl.show(mainPanel, "mainMenu");
        }
    }
    
    public static void main(String[] args) {
       TicTacToeMain newGame=new TicTacToeMain();
       newGame.setSize(600, 600);
       newGame.setResizable(false);
       newGame.setLocation(100, 100);
       newGame.setVisible(true);
       newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void designJOptionPane()
    {
        UIManager.put("OptionPane.messageFont", new Font("Comic Sans MS", Font.BOLD, 14));
        UIManager.put("OptionPane.messageForeground", Color.red);
        UIManager.put("OptionPane.buttonFont", new Font("Comic Sans MS", Font.BOLD, 12));
        UIManager.put("OptionPane.background",Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Button.background", Color.CYAN);
    }
}
