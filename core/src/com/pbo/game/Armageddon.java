package com.pbo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Armageddon extends Game {
    public ArrayList<String> scores;
    public Player currentPlayer;
    public Batch batch;
    public BitmapFont bitmapFont;

    public int widthScreen = 1000;
    public int heightScreen = 800;

    @Override
    public void create() {
        currentPlayer = new Player();
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(3, 3);
        this.setScreen(new MainMenuScreen(this));

    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        bitmapFont.dispose();
    }

    public void saveScore(ArrayList<String> highScores, String name, int score) {
        if (!highScores.isEmpty()) {
            //update top 5 score jika memenuhi
            for (int i = 0; i < highScores.size(); i++) {
                int compare = Integer.parseInt(highScores.get(i).substring(highScores.get(i).indexOf('-') + 1));
                if (score > compare || (score == compare && i != 4)) {
                    highScores.add(i, String.format("%s-%d", name, score));
                    if (highScores.size() == 6) highScores.remove(highScores.size() - 1);
                    break;
                }
            }
        } else {
            highScores.add(String.format("%s-%d", name, score));
        }
        rewriteText(highScores, Paths.get("Scores.txt"));
    }

    public void displayScore(BitmapFont bitmapFontIn, ArrayList<String> highScores, int x, int y) {
        String tmp = "TOP 3 SCORES : ";
        int count = 1;
        for (String text : highScores) {
            tmp += "\n" + count + ". " + text;
            count++;
            if (count == 4) {
                break;
            }
        }
        bitmapFontIn.draw(batch, tmp, x, y);
    }

    public String getHighScore(int index) {
        return (index + 1) + ". " + scores.get(index);
    }

    public void readScore(ArrayList<String> highScores, Path path) {
        try {
            highScores.clear();
            //menghapus semua reading sebelumnya
            BufferedReader br = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
            String text = br.readLine();
            while (text != null) {
                highScores.add(text);
                text = br.readLine();
            }
            br.close();
        } catch (IOException ioeError) {
            System.out.println("Error reading file");
        }
    }

    private void cleanFile(Path path) {
        //function buat membersihkan file dulu supaya waktu di rewrite tidak ada bekas teks lama yang mungkin membuat error!
        try {
            File file = new File(path.toString());
            PrintWriter pw = new PrintWriter(file);
            pw.write("");
            pw.close();
        } catch (FileNotFoundException exception) {
            System.out.println("no such file found!");
        }
    }

    void rewriteText(ArrayList<String> scores, Path path) {
        cleanFile(path);
        //membersihkan file dlu sebelum di rewrite!
        try {
            BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardCharsets.ISO_8859_1,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            for (String text : scores) {
                bw.write(text);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ioeError) {
            System.out.println("Error writing file.");
            System.exit(0);
        }
    }
}
