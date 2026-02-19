import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame
{
    private TicTacToeTile[][] board = new TicTacToeTile[3][3];
    private String currentPlayer = "X";
    JPanel titlePanel, gamePanel, quitPanel;

    public TicTacToeFrame()
    {
        setTitle("Tic Tac Toe");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        createGamePanel();
        add(gamePanel, BorderLayout.CENTER);

        createQuitPanel();
        add(quitPanel, BorderLayout.SOUTH);

    }

        private void createTitlePanel()
        {
            titlePanel = new JPanel();
            JLabel titleLabel = new JLabel("Tic Tac Toe");
                titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
            titlePanel.add(titleLabel);
        }

        private void  createGamePanel()
        {
            gamePanel = new JPanel();
            gamePanel.setLayout(new java.awt.GridLayout(3,3));


            for( int row = 0; row < 3; row++)
                for(int col= 0; col < 3; col++)
                {
                    int r = row;
                    int c = col;

                    board[r][c] = new TicTacToeTile(r, c);
                    board[r][c].setText(" ");
                    board[r][c].addActionListener(e -> resolveMove(r, c));
                    board[r][c].setFont(new Font("Times New Romans", Font.BOLD, 60));
                    gamePanel.add(board[r][c]);

                }
        }

        private void createQuitPanel()
        {
            quitPanel = new JPanel();
            JButton quitButton = new JButton("Quit");
            quitButton.addActionListener((ActionEvent ae) -> {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            });
            quitPanel.add(quitButton);
        }


    private void resolveMove(int row, int col)
    {
        // handle a move for the current player at the specified row and col
        TicTacToeTile tile = board[row][col];
        if(!tile.getText().equals(" "))
        {
            JOptionPane.showMessageDialog(this, "Invalid move! Please select an empty tile.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tile.setText(currentPlayer);

        if(isWin(currentPlayer))
        {
            JOptionPane.showMessageDialog(this, currentPlayer + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            playAgain();
            return;
        } else if (isTie())
        {
            JOptionPane.showMessageDialog(this, "It's a tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            playAgain();
            return;
        }
        // switch player after checking win and tie
        if(currentPlayer.equals("X"))
        {
            currentPlayer = "O";
        }
        else
        {
            currentPlayer = "X";
        }

    }

    public void playAgain()
    {
        int response = JOptionPane.showConfirmDialog(this, "Play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            clearBoard();
        } else {
            JOptionPane.showMessageDialog(this, "Thanks for playing!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public boolean isColWin(String currentPlayer)
    {
        for(int col=0; col < 3; col++)
        {
            if(
            board[0][col].getText().equals(currentPlayer) &&
            board[1][col].getText().equals(currentPlayer) &&
            board[2][col].getText().equals(currentPlayer))
            {
                return true;
            }
        }

        // check for a column win
        return false;
    }

    public boolean isRowWin(String currentPlayer)
    {
        for(int row=0; row < 3; row++)
        {
            if(
             board[row][0].getText().equals(currentPlayer) &&
             board[row][1].getText().equals(currentPlayer) &&
             board[row][2].getText().equals(currentPlayer))
            {
                return true;
            }
        }

        // check for a row win
        return false;
    }

    public boolean isDiaWin(String currentPlayer)
    {
        if(
        board[0][0].getText().equals(currentPlayer) &&
        board[1][1].getText().equals(currentPlayer) &&
        board[2][2].getText().equals(currentPlayer))
        {
            return true;
        }
        if(
        board[0][2].getText().equals(currentPlayer) &&
        board[1][1].getText().equals(currentPlayer) &&
        board[2][0].getText().equals(currentPlayer))
        {
            return true;
        }

        // check for a diagonal win
        return false;
    }

    public boolean isWin(String currentPlayer)
    {
        if(isColWin(currentPlayer) || isRowWin(currentPlayer) || isDiaWin(currentPlayer))
        {
            return true;
        }

        return false;
    }

    public boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so no win is possible
        // Checking the rows
        for(int row=0; row < 3; row++)
        {
            if(
            board[row][0].getText().equals("X") ||
            board[row][1].getText().equals("X") ||
            board[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(
            board[row][0].getText().equals("O") ||
            board[row][1].getText().equals("O") ||
            board[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) ) //if both are true no win is possible in this row
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < 3; col++)
        {
            if(
            board[0][col].getText().equals("X") ||
            board[1][col].getText().equals("X") ||
            board[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(
            board[0][col].getText().equals("O") ||
            board[1][col].getText().equals("O") ||
            board[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) ) //if both are true no win is possible in this col
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(
        board[0][0].getText().equals("X") ||
        board[1][1].getText().equals("X") ||
        board[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(
        board[0][0].getText().equals("O") ||
        board[1][1].getText().equals("O") ||
        board[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(
        board[0][2].getText().equals("X") ||
        board[1][1].getText().equals("X") ||
        board[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(
        board[0][2].getText().equals("O") ||
        board[1][1].getText().equals("O") ||
        board[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

    public void  clearBoard()
    {
          for(int row=0; row < 3; row++)
          {
                for(int col=0; col < 3; col++)
                {
                 board[row][col].setText(" ");
                }
          }
          currentPlayer = "X";
    }

     public static void main(String[] args)
     {
         TicTacToeFrame frame = new TicTacToeFrame();
         frame.setVisible(true);
     }

}
