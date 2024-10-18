package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
            JFrame frame = new JFrame();
            frame.setTitle("Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            App app = new App();
            frame.add(app);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
            app.startGameThread();
        }

    }
