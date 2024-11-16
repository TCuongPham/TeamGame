package game;

import javax.swing.JFrame;
import chapter.chap1.*;
import chapter.chap3.*;

public class MAIN implements Runnable {
    public static boolean kiemTra = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game với Background");
        GamePanel_chap1 chapter1 = new GamePanel_chap1();
        GamePanel_chap3 chapter2 = new GamePanel_chap3();

        frame.add(chapter2);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        MAIN mainRunnable = new MAIN(frame, chapter1, chapter2);
        Thread thread = new Thread(mainRunnable);
        thread.start();  // Chạy luồng kiểm tra và chuyển cảnh
    }

    private JFrame frame;
    private GamePanel_chap1 chapter1;
    private GamePanel_chap3 chapter3;

    public MAIN(JFrame frame, GamePanel_chap1 chapter1, GamePanel_chap3 chapter3) {
        this.frame = frame;
        this.chapter1 = chapter1;
        this.chapter3 = chapter3;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(33); // Tạo độ mượt cho game (khoảng 30 FPS)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (kiemTra) {
                frame.getContentPane().removeAll();
                frame.add(chapter1);
                frame.pack();
                frame.revalidate();
                frame.repaint();
                chapter1.requestFocusInWindow();  // Đảm bảo chapter1 nhận sự kiện bàn phím
                break;
            }
        }
    }

}
