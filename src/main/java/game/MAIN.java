package game;

import javax.swing.JFrame;

import chapter.chap0.src.main.GamePanel;
import chapter.chap1.GamePanel_chap1;
import chapter.chap3.GamePanel_chap3;
import chapter.chap3.RacingGame;
import chapter.chap0.src.main.GamePanel;

import javax.swing.*;

public class MAIN {
    public static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("8th Year Student");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 840);
        frame.setLocationRelativeTo(null);

        // Bắt đầu với game đánh quái
        startGameQuai();
        
        frame.setVisible(true);
    }

    // Bắt đầu game đánh quái
    public static void startGameQuai() {
        frame.getContentPane().removeAll();

        GamePanel gamePanel = new GamePanel(); // Game đánh quái
        frame.add(gamePanel);

        // Gắn callback để chuyển sang game mê cung
        gamePanel.setGameCompleteCallback(() -> switchToMazeGame());
        gamePanel.setupGame();
        gamePanel.startgameThread();
        gamePanel.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
    }

    // Chuyển sang game mê cung
    public static void switchToMazeGame() {
        frame.getContentPane().removeAll();

        GamePanel_chap3 mazeGame = new GamePanel_chap3();// Map mê cung
        frame.add(mazeGame);
        // Gắn callback để chuyển sang game đua xe
        mazeGame.setSceneChangeListener(() -> switchToRacingGame());
        mazeGame.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
    }

    // Chuyển sang game đua xe
    public static void switchToRacingGame() {
        frame.getContentPane().removeAll();

        RacingGame racingGame = new RacingGame(frame); // Game đua xe
        frame.add(racingGame);

        // Gắn callback để chuyển sang chap1
        racingGame.setGameOverCallback(() -> switchToChap1());
        racingGame.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
    }

    // Chuyển sang chap1
    public static void switchToChap1() {
        frame.getContentPane().removeAll();

        GamePanel_chap1 chap1 = new GamePanel_chap1(); // Chap1
        frame.add(chap1);

        chap1.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
    }
}
