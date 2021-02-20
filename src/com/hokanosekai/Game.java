package com.hokanosekai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int TILES_SIZE = 10;
    int appleX,appleY;
    int x[];
    int y[];
    int bodyParts;
    int appleEatens;
    char direction;
    boolean running = false;
    Timer timer;
    Random random;

    Game(){
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setVisible(true);
        this.addKeyListener(new KeyDemo());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        appleEatens = 0;
        direction = 'R';
        bodyParts = 4;
        x = new int[(SCREEN_HEIGHT*SCREEN_WIDTH/TILES_SIZE)];
        y = new int[(SCREEN_HEIGHT*SCREEN_WIDTH/TILES_SIZE)];
        timer = new Timer(75,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / TILES_SIZE; i++) {
                g.drawLine(i * TILES_SIZE, 0, i * TILES_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * TILES_SIZE, SCREEN_WIDTH, i * TILES_SIZE);
            }

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, TILES_SIZE, TILES_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], TILES_SIZE, TILES_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], TILES_SIZE, TILES_SIZE);
                }
            }

            g.setColor(Color.RED);
            g.setFont(new Font(Font.SANS_SERIF,Font.CENTER_BASELINE,30));
            FontMetrics score = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEatens, (SCREEN_WIDTH - score.stringWidth("Score: "+appleEatens))/2,40);
        }
        else{
            gameOver(g);
        }
    }
    public void move(){
        for (int i = bodyParts;i > 0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - TILES_SIZE;
                break;
            case 'D':
                y[0] = y[0] + TILES_SIZE;
                break;
            case 'L':
                x[0] = x[0] - TILES_SIZE;
                break;
            case 'R':
                x[0] = x[0] + TILES_SIZE;
                break;
        }
    }
    public void newApple(){
        appleX = random.nextInt((int) (SCREEN_WIDTH/TILES_SIZE))*TILES_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT/TILES_SIZE))*TILES_SIZE;
    }
    public void checkApple(){
        if ((appleX == x[0]) && (appleY == y[0])){
            bodyParts++;
            appleEatens++;
            newApple();
        }
    }
    public void checkCollision(){
        for (int i=bodyParts;i>0;i--){
            if (x[0] == x[i] && y[0] == y[i]){
                running = false;
            }
        }

        if ((x[0] > SCREEN_WIDTH || x[0] < 0) || (y[0] > SCREEN_HEIGHT || y[0] < 0)){
            running = false;
        }

        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,75));
        FontMetrics over = getFontMetrics(g.getFont());
        g.drawString("GameOver", (SCREEN_WIDTH - over.stringWidth("GameOver"))/2,SCREEN_HEIGHT/2);


        g.setFont(new Font(Font.SANS_SERIF,Font.CENTER_BASELINE,30));
        FontMetrics score = getFontMetrics(g.getFont());
        g.drawString("Score: "+appleEatens, (SCREEN_WIDTH - score.stringWidth("Score: "+appleEatens))/2,SCREEN_HEIGHT/2+40);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (running){
            move();
            checkCollision();
            checkApple();
        }
        repaint();
    }

    public class KeyDemo extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (!running){
                        startGame();
                    }
                    break;
            }
        }
    }
}
