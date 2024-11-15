package banje.ban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Rock_chap2 {
    final int TypeSize = 12;
    final int scale = 10;
    final int tileSize = TypeSize * scale;
    final int maxScreenCol = 8;
    final int maxScreenRow = 7;
    final int ScreenWidth = tileSize * maxScreenCol;
    final int ScreenHeight = tileSize * maxScreenRow;   
    public int x; 
    public int y;  
    public int preX = x, preY = y;
    Rectangle rect;
    public Rock_chap2(int x, int y) {
        this.x = x * tileSize;
        this.y = y * tileSize;
        this.preX = this.x; // Khởi tạo preX
        this.preY = this.y; // Khởi tạo preY
        rect = new Rectangle(this.x, this.y, tileSize, tileSize); // Khởi tạo Rectangle
    }
    // Phương thức để vẽ nhân vật Rock
    public void draw(Graphics g) {
        g.setColor(Color.white);  // Đặt màu trắng
        g.fillOval(x, y, tileSize, tileSize);  // Vẽ hình tròn tại vị trí (x, y)
    }
    public boolean vaCham(Rectangle rectA) {
        if (rectA.intersects(rect)) return true;
        else return false;
    }
    // Getter và Setter cho vị trí của nhân vật nếu cần di chuyển
    public void diChuyen(int key) {
        preX = x;
        preY = y;

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (x > 0) {
                    x -= tileSize;  // Di chuyển sang trái
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (x < ScreenWidth - tileSize) {
                    x += tileSize;  // Di chuyển sang phải
                }
                break;
            case KeyEvent.VK_UP:
                if (y > 0) {
                    y -= tileSize;  // Di chuyển lên trên
                }
                break;
            case KeyEvent.VK_DOWN:
                if (y < ScreenHeight - tileSize) {
                    y += tileSize;  // Di chuyển xuống dưới
                }
                break;
        }

        // Cập nhật vị trí của hình chữ nhật đại diện cho quân cờ
        rect.setLocation(x, y);
    }

    public void luiLai() {
        x = preX;
        y = preY;
        rect.setLocation(x, y);
    }
    
}
