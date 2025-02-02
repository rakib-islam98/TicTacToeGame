package tictactoegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author iraki
 */
class SinglePlayerTicTacToe extends TicTacToe{
    
    private Difficulty difficulty;
    private final Random rand;
    
    public SinglePlayerTicTacToe(ActionListener backButtonAction)
    {
        super(backButtonAction);
        rand=new Random();
        turnLabel.setText("Single Player Mode");
        turnLabel.setForeground(new Color(153,0,0));
        isPlayerXTurn=false;
        scoreO.setText("    Player: " + winO+"   ");
        scoreX.setText(" Computer: " + winX+" ");
    }
    
    void setDifficulty(Difficulty difficulty)
    {
        this.difficulty=difficulty;
    }
    
    @Override
    protected void initializeButtons()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                buttons[i][j]=new JButton("");
                final int row=i,col=j;
                buttons[i][j].addActionListener(e ->
                        {
                                handlePlayerMove(row,col);
                        });
                buttons[i][j].setBackground(Color.black);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.cyan, 1));
                buttons[i][j].setFont(new Font("Comic Sans MS",Font.BOLD,120));
                buttons[i][j].setFocusPainted(false);
                grid.add(buttons[i][j]);
            }
        }
    }
    
    @Override
    protected void handlePlayerMove(int row,int col)
    {
        if (board[row][col] == '_') 
        {
            buttons[row][col].setForeground(Color.blue);
            board[row][col] = 'O';
            buttons[row][col].setText("O");
            if (isGameOver()) {
                    return;
            }
            isPlayerXTurn = !isPlayerXTurn;
            computerMove();
        }
    }
    
    private void computerMove()
    {
        int row,col;
        int move[];
        switch(difficulty)
        {
            case EASY:
                move=getRandomMove();
                break;
            case MEDIUM:
                if(rand.nextDouble()<0.7)
                    move=getRandomMove();
                else
                    move=findBestMove();
                break;
            case HARD:
            default:
                move=findBestMove();
                break;
        } 
        row = move[0];
        col = move[1];

        // Make the computer's move
        buttons[row][col].setForeground(Color.red);
        board[row][col] = 'X';
        buttons[row][col].setText("X");

        if (isGameOver()) {
            return;
        }
        isPlayerXTurn = !isPlayerXTurn;
    }
    
    private int[] getRandomMove()
    {
        int row,col;
        do
        {
            row=rand.nextInt(3);
            col=rand.nextInt(3);
        }while(board[row][col]!='_');
        return new int[]{row,col};
    }
    
    @Override
    protected boolean isGameOver()
    {
        if (checkWinner('X')) 
        {
            winX++;scoreX.setText(" Computer: "+winX+" ");
            int option=showConfirmationDialog("Computer Wins!","Game Over",new String[]{"Play Again", "Reset Score"},"Play Again");
            if(option==JOptionPane.YES_OPTION || option==-1) { resetGame();}
            else if(option==JOptionPane.NO_OPTION) { newGame();}
            return true;
        } 
        else if (checkWinner('O')) 
        {
            winO++;scoreO.setText("    Player: "+winO+"   ");
            int option=showConfirmationDialog("Player Wins!","Game Over",new String[]{"Play Again", "Reset Score"},"Play Again");
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
    
    private int[] findBestMove()
    {
        int[] bestMove = new int[2];
        int bestScore=Integer.MIN_VALUE;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') 
                {
                        board[i][j]='X';
                        int moveScore=minimax(board,0,false);
                        board[i][j]='_';
                        if(moveScore>bestScore)
                        {
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestScore=moveScore;
                        }
                }
            }
        }
        return bestMove;
    }
    
    private int minimax(char board[][],int depth,boolean isMaximizing)
    {
        //Check the winner X / O / Draw if null means board is not full and still no winner
        String result=getWinner();
        //Base condition of recursion
        if(result!=null)
        {
            //if max wins return 10-depth(max depth=9) "- depth" to choose best move in less path. less depth=higher positive value
            if(result.equals("X")) return 10-depth;
            //in case of min less depth=higher negative value
            if(result.equals("O")) return depth-10;
            //getWinner() returns X/O/Draw/null
            return 0;
        }
        if(isMaximizing)
        {
            int bestScore=Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) 
            {
                for (int j = 0; j < 3; j++) 
                {
                    if (board[i][j] == '_') 
                    {
                            board[i][j]='X';
                            bestScore=Math.max(bestScore,minimax(board,depth+1,false));
                            board[i][j]='_';
                    }
                }   
            }
            return bestScore;
        }
        else
        {
            int bestScore=Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) 
            {
                for (int j = 0; j < 3; j++) 
                {
                    if (board[i][j] == '_') 
                    {
                            board[i][j]='O';
                            bestScore=Math.min(bestScore,minimax(board,depth+1,true));
                            board[i][j]='_';
                    }
                }   
            }
            return bestScore;
        }   
    }
    
    private String getWinner() 
    {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '_')
                return board[i][0] == 'X' ? "X" : "O";
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '_')
                return board[0][i] == 'X' ? "X" : "O";
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '_')
            return board[0][0] == 'X' ? "X" : "O";
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '_')
            return board[0][2] == 'X' ? "X" : "O";
        return isBoardFull() ? "Draw" : null;
    }
    
    @Override
    void newGame()
    {
        resetGame();
        winX=0;winO=0;
        isPlayerXTurn=false;
        scoreX.setText(" Computer: "+winX+" ");
        scoreO.setText("    Player: "+winO+"   ");
    }
}
