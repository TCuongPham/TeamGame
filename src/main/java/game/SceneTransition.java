package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SceneTransition extends JPanel {
    private float alpha = 0.0f;
    private boolean isFadingIn = true;
    private boolean isShowingDialog = false;
    private String[] dialogLines = {
        "Chương 1 hoàn thành...",
        "Hành trình vẫn còn tiếp diễn...",
        "Chương 2 bắt đầu!"
    };
    private int currentDialog = 0;
    private Timer fadeTimer;
    private boolean isTransitionComplete = false;
    
    // Thêm biến để theo dõi việc đã chuyển map chưa
    private boolean hasMapChanged = false;
    // Thêm interface để callback khi cần chuyển map
    private MapChangeListener mapChangeListener;
    
    // Interface để thông báo thời điểm chuyển map
    public interface MapChangeListener {
        void onMapChange();
    }
    
    // Setter cho listener
    public void setMapChangeListener(MapChangeListener listener) {
        this.mapChangeListener = listener;
    }
    
    public SceneTransition() {
        setBackground(Color.BLACK);
        setOpaque(false);
        setupTimer();
    }
    
    private void setupTimer() {
        fadeTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFadingIn) {
                    
                    alpha += 0.33f;
                    if (alpha >= 1.0f) {
                        alpha = 1.0f;
                        isFadingIn = false;
                        isShowingDialog = true;
                        
                        // Thông báo chuyển map khi màn hình đã tối hoàn toàn
                        if (!hasMapChanged && mapChangeListener != null) {
                            mapChangeListener.onMapChange();
                            hasMapChanged = true;
                        }
                    }
                } else if (isShowingDialog) {
                    if (currentDialog < dialogLines.length - 1) {
                        currentDialog++;
                    } else {
                        isShowingDialog = false;
                    }
                } else {
                    alpha -= 0.33f;
                    if (alpha <= 0.0f) {
                        alpha = 0.0f;
                        fadeTimer.stop();
                        isTransitionComplete = true;
                    }
                }
                repaint();
            }
        });
    }
    
    public void startTransition() {
        alpha = 0.0f;
        isFadingIn = true;
        isShowingDialog = false;
        currentDialog = 0;
        isTransitionComplete = false;
        hasMapChanged = false;  // Reset trạng thái chuyển map
        fadeTimer.start();
    }
    
    public boolean isTransitionComplete() {
        return isTransitionComplete;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        if (isShowingDialog && alpha >= 1.0f) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            
            FontMetrics fm = g2d.getFontMetrics();
            String text = dialogLines[currentDialog];
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            
            g2d.drawString(text,
                         (getWidth() - textWidth) / 2,
                         (getHeight() - textHeight) / 2 + fm.getAscent());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        g2d.dispose();
    }
}