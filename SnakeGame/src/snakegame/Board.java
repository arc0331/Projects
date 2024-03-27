package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;

    private final int ALL_DOTS=900;
    private final int DOT_SIZE=10;
    private final int RANDOM_POSITION=29;

    private int apple_x;
    private int apple_y;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection=false;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private boolean rightDirection=true;

    private boolean ingame = true;
    private int dots;
    private Timer timer;
    Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
        //when the game opens it runs but to actually play you have to click the game window
        //set focusable by default makes the window active

        loadimages();
        initGame();
    }
    public void loadimages(){
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple=i1.getImage();
        ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head=i3.getImage();
    }
    public void initGame()
    {
        dots=3;
        for(int i=0; i<dots; i++)
        {
            y[i]=50;
            x[i]=50-i*DOT_SIZE;
        }
        locateApple();

        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple(){
        int r = (int)(Math.random()*RANDOM_POSITION);
        apple_x = r * DOT_SIZE;
        r = (int)(Math.random()*RANDOM_POSITION);
        apple_y = r * DOT_SIZE;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(ingame)
        {
            g.drawImage(apple, apple_x, apple_y, this);
            for(int i =0; i<dots; i++)
            {
                if(i==0)
                {
                    g.drawImage(head,x[i],y[i],this);
                }
                else {
                    g.drawImage(dot,x[i],y[i],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }

    }

    public void gameOver(Graphics g)
    {
        String msg= "Game Over";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg,(300-metrices.stringWidth(msg))/2, 300/2);
    }
    public void move()
    {
        //moves then dots by +1 position(relative to head)
        for (int i = dots; i>0; i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        if(leftDirection)
        {
            x[0]=x[0]-DOT_SIZE;
        }
        if(rightDirection)
        {
            x[0]=x[0]+DOT_SIZE;
        }
        if(upDirection)
        {
            y[0]=y[0]-DOT_SIZE;
        }
        if(downDirection)
        {
            y[0]=y[0]+DOT_SIZE;
        }
    }

    public void checkApple()
    {
        //snake head == apple location => collision else no collision
        if((x[0] == apple_x) && (y[0] == apple_y))
        {
            dots++;//snake length increases by 1
            locateApple();//after eating apple new location for apple is generated
        }
    }

    public void checkCollision()
    {
        for(int i = dots; i>0; i--)
        {
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i]))
            {
                ingame=false;
            }

        }
        if(y[0]>=300)
        {
            ingame=false;
        }
        if(x[0]>=300)
        {
            ingame=false;
        }
        if(y[0]<0)
        {
            ingame=false;
        }
        if(x[0]<0)
        {
            ingame=false;
        }
        if(!ingame)
        {
            timer.stop();
        }
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ingame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public class TAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

            if(key== KeyEvent.VK_LEFT && (!rightDirection))
            {
                leftDirection=true;
                //rightDirection=false; to move left (as right is by default) it will first go down then take left
                upDirection=false;
                downDirection=false;
            }
            if(key== KeyEvent.VK_RIGHT && (!leftDirection))
            {
                //leftDirection=false;
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key== KeyEvent.VK_UP && (!downDirection))
            {
                leftDirection=false;
                rightDirection=false;
                upDirection=true;
             //   downDirection=false;
            }
            if(key== KeyEvent.VK_DOWN && (!upDirection))
            {
                leftDirection=false;
                rightDirection=false;
                //upDirection=false;
                downDirection=true;
            }
        }
    }
}
