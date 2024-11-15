
package banje.ban;

import javax.swing.JFrame;

public class MAIN implements Runnable {
    public static boolean kiemTra = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game với Background");
        GamePanel_chap1 chapter1 = new GamePanel_chap1();
        GamePanel_chap2 chapter2 = new GamePanel_chap2();

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
    private GamePanel_chap2 chapter2;

    public MAIN(JFrame frame, GamePanel_chap1 chapter1, GamePanel_chap2 chapter2) {
        this.frame = frame;
        this.chapter1 = chapter1;
        this.chapter2 = chapter2;
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
