package chapter.chap1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.Area;

public class GamePanel_chap1 extends JPanel implements Runnable, KeyListener{
    private BufferedImage backgroundImage;
    public final int ScreenHeight =840;
    public final int ScreenWidth = 960;
    private final int tileSize = 25;
    private int cameraX = 1400; // Tọa độ X của camera
    private int cameraY = 900; // Tọa độ Y của camera
    private int preCamX = cameraX;
    private int preCamY = cameraY;
    private int BGWidth = 3302;
    private int BGHeight = 2347;
    private int DefaultX = ScreenHeight / 2 - tileSize / 2;
    private int DefaultY = ScreenWidth / 2 - tileSize / 2;
    Rectangle hcnTest;
    Area polyArea;
    Area rectArea;
    Rock_chap1 r1 = new Rock_chap1(DefaultX, DefaultY);
    Animation animation;
    Thread thread;
    public GamePanel_chap1(){
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        try {
            // Đọc ảnh nền từ tệp
            backgroundImage = ImageIO.read(new File("pic/BackGround.png")); // Đổi đường dẫn nếu cần
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setFocusable(true);
        this.addKeyListener(this);
        thread = new Thread(this);
        thread.start();
        animation = new Animation();
        animation.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Cắt và vẽ một phần của ảnh nền
            g.drawImage(
                backgroundImage,
                0, 0, ScreenWidth , ScreenHeight,  // Vị trí và kích thước hiển thị trên panel
                cameraX, cameraY, cameraX + ScreenWidth, cameraY + ScreenHeight,  // Phần ảnh cần cắt
                this
            );
        }
        r1.draw(g);
        animation.operation(g, r1.x, r1.y);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(33); // Tạo độ mượt cho game (khoảng 30 FPS)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (cameraX > tileSize && r1.x == DefaultX) {
                    cameraX -= tileSize;  // Di chuyển sang trái
                }
                else {
                    r1.diChuyen(key);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (cameraX < backgroundImage.getWidth() - ScreenWidth - tileSize && r1.x == DefaultX) {
                    cameraX += tileSize;  // Di chuyển sang phải
                }
                else {
                    r1.diChuyen(key);
                }
                break;
            case KeyEvent.VK_UP:
                if (cameraY > tileSize && r1.y == DefaultY) {
                    cameraY -= tileSize;  // Di chuyển lên trên
                }
                else {
                    r1.diChuyen(key);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (cameraY < backgroundImage.getHeight() - ScreenHeight - tileSize && r1.y == DefaultY) {
                    cameraY += tileSize;  // Di chuyển xuống dưới
                }
                else {
                    r1.diChuyen(key);
                    preCamX = cameraX;
                    preCamY = cameraY;
                }
                break;
        }
        hcnTest = new Rectangle(2040 - cameraX ,766 - cameraY, 1012, 1392);
        Polygon poly = new Polygon(
            new int[] {3302 - cameraX, 1958 - cameraX, 1958 - cameraX, 1750 - cameraX, 1750 - cameraX, 1535 - cameraX, 860 - cameraX, 
            1535 - cameraX, 1535 - cameraX, 432 - cameraX, 432 - cameraX, 0 - cameraX, 0 - cameraX, 3302 - cameraX}, // Tọa độ x của các điểm
            new int[] {471 - cameraY, 471 - cameraY, 561 - cameraY, 561 - cameraY, 620 - cameraY, 620 - cameraY, 1295 - cameraY, 
            1295 - cameraY, 2181 - cameraY, 2181 - cameraY, 2220 - cameraY, 2220 - cameraY, 0 - cameraY, 0 - cameraY}, // Tọa độ y của các điểm
            14 // Số lượng đỉnh
        );
        polyArea = new Area(poly);
        rectArea = new Area(r1.rect);
        polyArea.intersect(rectArea);
        if (r1.vaCham(hcnTest) || !polyArea.isEmpty() || r1.x > 908 || r1.y > 793 || r1.x < 33 ) {
            r1.luiLai();
            cameraX = preCamX;
            cameraY = preCamY;
        }
        else {
            preCamX = cameraX;
            preCamY = cameraY;
            r1.preX = r1.x;
            r1.preY = r1.y;
        }
        repaint(); // Vẽ lại sau khi di chuyển camera
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
