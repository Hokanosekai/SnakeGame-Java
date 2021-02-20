package com.hokanosekai;

import com.hokanosekai.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Frame extends JFrame {

    Game game = new Game();

    Frame(){
        this.add(game);
        this.setTitle("Snake Game");
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
