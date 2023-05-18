package com.pbo.game;

import javax.swing.*;

public class Player {
    private String name;
    private int score = 0;

    public Player() {
        this.name = JOptionPane.showInputDialog("Insert Your Name:");
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
