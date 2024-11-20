package chapter.chap3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel_chap3 extends JPanel implements KeyListener {
    // Screen setting
    private BufferedImage backgroundImage, bikeImage, character, character_1;
    private Image bikImage_scaled, backgroundImage_scaled, character_scaled, character_scaled_right;
    private Image character_1_scaled, preImage;
    public int check = 0;
    final int TypeSize = 12;
    final int scale = 10;
    final int tileSize = TypeSize * scale;
    final int maxScreenCol = 8;
    final int maxScreenRow = 7;
    final int ScreenWidth = tileSize * maxScreenCol;
    final int ScreenHeight = tileSize * maxScreenRow;
    private int x = 7 * tileSize;  // Vị trí ban đầu của nhân vật (trục X)
    private int y = 0 * tileSize;  // Vị trí ban đầu của nhân vật (trục Y)
    private int preX = x;
    private int preY = y;
    public int key = -1;
    boolean winCheck = false;
    Rectangle rect1 = new Rectangle(0 * tileSize, 0 * tileSize, 6 * tileSize, tileSize);
    Rectangle rect2 = new Rectangle(0 * tileSize, 1 * tileSize, tileSize, 6 * tileSize);
    Rectangle rect3 = new Rectangle(1 * tileSize, 1 * tileSize, 1 * tileSize, 2 * tileSize);
    Rectangle rect4 = new Rectangle(1 * tileSize, 6 * tileSize, 7*  tileSize, tileSize);
    Rectangle rect5 = new Rectangle(3 * tileSize, 3 * tileSize, 5 * tileSize, tileSize);
    Rectangle rect6 = new Rectangle(7 * tileSize, 2 * tileSize, 1 * tileSize, 3 * tileSize);
    
    Rectangle rectA = new Rectangle(x, y, tileSize, tileSize);
    Rock_chap3[] r = {
    new Rock_chap3(4, 1),
    new Rock_chap3(5, 2),
    new Rock_chap3(2, 4),
    new Rock_chap3(2, 5),
    new Rock_chap3(5, 4),
    new Rock_chap3(4, 5)
};
    public GamePanel_chap3() {
        try {
            // Đọc ảnh nền từ tệp
            backgroundImage = ImageIO.read(new File("pic/background_3.png")); 
            bikeImage = ImageIO.read(new File ("pic/xe_may.png"));
            character = ImageIO.read(new File("pic/character.png")); 
            character_1 = ImageIO.read(new File("pic/character_1.png")); 
            character_1_scaled = character_1.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            character_scaled = character.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            character_scaled_right = character.getScaledInstance((-1)* tileSize, tileSize, Image.SCALE_SMOOTH);
            bikImage_scaled = bikeImage.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            backgroundImage_scaled = backgroundImage.getScaledInstance(ScreenWidth, ScreenHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.requestFocus();
        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        };            
        Timer timer = new Timer(33, ac);  
        timer.start();
    }
    public boolean chamTuong(Rectangle rectA) {
        if (rect1.intersects(rectA) || rect2.intersects(rectA) || rect3.intersects(rectA) || 
                rect4.intersects(rectA) || rect5.intersects(rectA) || rect6.intersects(rectA)) {
            return true;
            
        }
        else return false;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage_scaled, 0, 0, null);
        //g.setColor(Color.red);
        drawCharacter(g, key);
        //g.drawImage(character_scaled, x, y, null); // Vẽ nhân vật tại (x, y)
    // Vẽ tường 
        // g.setColor(Color.blue);
        // g.fillRect(0 * tileSize, 0 * tileSize, 6 * tileSize, tileSize);
        // g.fillRect(0 * tileSize, 1 * tileSize, tileSize, 6 * tileSize);
        // g.fillRect(1 * tileSize, 1 * tileSize, 1 * tileSize, 2 * tileSize);
        // g.fillRect(1 * tileSize, 6 * tileSize, 7*  tileSize, tileSize);
        // g.fillRect(3 * tileSize, 3 * tileSize, 5 * tileSize, tileSize);
        // g.fillRect(7 * tileSize, 2 * tileSize, 1 * tileSize, 3 * tileSize);
        for (int i = 0; i < 6; i++) r[i].draw(g, bikImage_scaled);
    }
    public void drawCharacter(Graphics g, int key) {
        if (key == -1) {
            g.drawImage(character_scaled, x, y, null);
            preImage = character_scaled;
        }
        else if (key == KeyEvent.VK_LEFT) {
            g.drawImage(character_scaled, x, y, null);
            preImage = character_scaled;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            g.drawImage(character_1_scaled, x, y, null);
            preImage = character_1_scaled;
        }
        else {
            g.drawImage(preImage, x, y, null);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        
        preX = x;
        preY = y;  // Lưu tọa độ cũ

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (x > 0) {
                    x -= tileSize ;  // Di chuyển sang trái
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (x < ScreenWidth - tileSize) {
                    x += tileSize ;  // Di chuyển sang phải
                }
                break;
            case KeyEvent.VK_UP:
                if (y > 0) {
                    y -= tileSize ;  // Di chuyển lên trên
                }
                break;
            case KeyEvent.VK_DOWN:
                if (y < ScreenHeight - tileSize) {
                    y += tileSize ;  // Di chuyển xuống dưới
                }
                break;
        }

        // Cập nhật tọa độ của rect2 với vị trí mới
        rectA.setLocation(x, y);
        // Kiểm tra va chạm sau khi di chuyển
        if (chamTuong(rectA)) {
            // Nếu va chạm, quay lại vị trí cũ
            x = preX;
            y = preY;
            rectA.setLocation(x, y);  // Cập nhật lại vị trí HCN của nhân vật chính
            
        } 
        for (int i = 0; i < 6; i++) {
            if (r[i].vaCham(rectA)) {
                x = preX;
                y = preY;
                rectA.setLocation(x, y);
                r[i].diChuyen(key);
                if (chamTuong(r[i].rect)) {
                    r[i].luiLai();
                }
                for (int j = 0; j < 6 ; j++) {
                    if (j == i) continue;
                    else {
                        if (r[i].vaCham(r[j].rect)) {
                            r[i].luiLai();
                            break;
                        }
                    }
                }
            }
        }
        if (x == 7 * tileSize && y == 5 * tileSize) {
            System.out.print("YOU WIN\n");
            game.MAIN.kiemTra = true;
            check = 1;
        }
        // repaint(); // Vẽ lại sau khi thay đổi vị trí
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Không cần xử lý
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Không cần xử lý
    }
}
