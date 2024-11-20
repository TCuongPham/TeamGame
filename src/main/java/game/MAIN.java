package game;

import chapter.chap1.GamePanel_chap1;
import chapter.chap3.GamePanel_chap3;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Dimension;

public class MAIN implements Runnable {
    public static boolean kiemTra = false;
    private boolean isTransitioning = false;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game với Background");
        
        GamePanel_chap1 chapter1 = new GamePanel_chap1();
        GamePanel_chap3 chapter3 = new GamePanel_chap3();
        SceneTransition transition = new SceneTransition();
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(960, 840));
        
        chapter3.setBounds(0, 0, 960, 840);
        transition.setBounds(0, 0, 960, 840);
        
        layeredPane.add(chapter3, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(transition, JLayeredPane.MODAL_LAYER);
        
        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        
        MAIN mainRunnable = new MAIN(frame, chapter1, chapter3, transition, layeredPane);
        // Đăng ký listener cho transition
        transition.setMapChangeListener(new SceneTransition.MapChangeListener() {
            @Override
            public void onMapChange() {
                // Xóa chapter3 và thêm chapter1 vào layeredPane
                layeredPane.remove(chapter3);
                chapter1.setBounds(0, 0, 960, 840);
                chapter1.setVisible(true);
                layeredPane.add(chapter1, JLayeredPane.DEFAULT_LAYER);
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
        
        Thread thread = new Thread(mainRunnable);
        thread.start();
    }
    
    private JFrame frame;
    private GamePanel_chap1 chapter1;
    private GamePanel_chap3 chapter3;
    private SceneTransition transition;
    private JLayeredPane layeredPane;
    
    public MAIN(JFrame frame, GamePanel_chap1 chapter1, GamePanel_chap3 chapter3, 
                SceneTransition transition, JLayeredPane layeredPane) {
        this.frame = frame;
        this.chapter1 = chapter1;
        this.chapter3 = chapter3;
        this.transition = transition;
        this.layeredPane = layeredPane;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (kiemTra && !isTransitioning) {
                isTransitioning = true;
                transition.startTransition();
            }
            
            if (isTransitioning && transition.isTransitionComplete()) {
                // Không cần xóa và thêm lại components ở đây nữa
                // vì đã được xử lý trong listener
                frame.revalidate();
                frame.repaint();
                chapter1.requestFocusInWindow();
                //break;
            }
        }
    }
}

// public class MAIN {
//     public static boolean kiemTra;
//     public static String nameOfList = "character_move_down";
//     public static void main(String[] args) {
//         // JFrame frame = new JFrame("Game với Background");
//         // Animation a = new Animation();
//         // GamePanel_chap1 chap1 = new GamePanel_chap1();
//         // frame.add(chap1);
//         // frame.setVisible(true);
//         // frame.setResizable(false);
//         // frame.pack();
//         // frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//         // frame.setLocationRelativeTo(null);
//         for (int i = 1; i <=4 ; i++) {
//             System.out.println("pic/" + nameOfList + " ("+ i +")" + ".png");
//         }
//     }
// }
