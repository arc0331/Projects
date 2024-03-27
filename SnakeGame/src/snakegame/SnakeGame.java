package snakegame;

import javax.swing.*;

public class SnakeGame extends JFrame {

    SnakeGame()
    {
        super("Snake Game");//super should be first keyword inside constructor otherwise it gives error
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Board());
        pack();

        setLocationRelativeTo(null);
        setResizable(false);

    }
    public static void main(String args[])
    {
        new SnakeGame().setVisible(true);
    }
}
