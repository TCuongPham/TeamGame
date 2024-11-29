package chapter.chap1;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel_chap1 extends JPanel implements Runnable, KeyListener {
    // Screen and Rendering Properties
    public final int ScreenHeight = 840;
    public final int ScreenWidth = 960;
    private final int tileSize = 25;
    private final int speed = 20;

    // Camera and Background Properties
    private int cameraX = 20;
    private int cameraY = 1500;
    private int preCamX = cameraX;
    private int preCamY = cameraY;
    private int BGWidth = 3302;
    private int BGHeight = 2347;

    // Character Positioning
    private int DefaultX = ScreenHeight / 2 - tileSize / 2;
    private int DefaultY = ScreenWidth / 2 - tileSize / 2;

    // Game State Variables
    private int key = -1;
    private char stand = 'f';
    private boolean isDialogueActive = false;

    // Game Objects
    private BufferedImage backgroundImage,girl_Image;
    Rectangle hcnTest, Girl_Rec;
    Area polyArea;
    Area rectArea;
    Rock_chap1 r1;
    
    // Animations
    Animation UP, DOWN, LEFT, RIGHT, STAND_FRONT, STAND_BACK, STAND_LEFT, STAND_RIGHT;

    // Dialogue System
    private DialogueManager dialogueManager;

    // Game Thread
    Thread thread;

    public GamePanel_chap1() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        
        // Initialize Background
        try {
            backgroundImage = ImageIO.read(new File("pic/BackGround.png"));
            girl_Image = ImageIO.read(new File("pic/GIRl_CHAR_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize Character
        r1 = new Rock_chap1(68, 708);

        // Initialize Dialogue System
        List<Dialogue> dialogues = loadDialogues();
        dialogueManager = new DialogueManager(dialogues);

        // Setup Panel
        this.setFocusable(true);
        this.addKeyListener(this);

        // Initialize Game Thread
        thread = new Thread(this);
        thread.start();

        // Initialize Animations
        initializeAnimations();
    }

    private void initializeAnimations() {
        UP = new Animation("character_move_up", 4);
        DOWN = new Animation("character_move_down", 4);
        LEFT = new Animation("character_move_left", 4);
        RIGHT = new Animation("character_move_right", 4);
        STAND_FRONT = new Animation("character_stand_front", 3);
        STAND_BACK = new Animation("character_stand_back", 3);
        STAND_LEFT = new Animation("character_stand_left", 3);
        STAND_RIGHT = new Animation("character_stand_right", 3);

        // Preload animation images
        UP.getImage(); DOWN.getImage(); LEFT.getImage(); RIGHT.getImage();
        STAND_RIGHT.getImage(); STAND_BACK.getImage(); STAND_LEFT.getImage(); STAND_FRONT.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println(r1.x + " " + r1.y + " " + " " + cameraX + " " + cameraY);
        // Draw Background
        if (backgroundImage != null) {
            g.drawImage(
                backgroundImage,
                0, 0, ScreenWidth, ScreenHeight,
                cameraX, cameraY, cameraX + ScreenWidth, cameraY + ScreenHeight,
                this
            );
        }

        g.drawImage(girl_Image, 2400 - cameraX ,2088 - cameraY, 120, 90, null);
        Girl_Rec = new Rectangle(2340 - cameraX ,2118 - cameraY, 200, 100);

        drawCharacterAnimation(g);

        // Draw Dialogue if Active
        if (isDialogueActive) {
            dialogueManager.draw(g, ScreenWidth, ScreenHeight);
        }
    }

    private void drawCharacterAnimation(Graphics g) {
        if (key == KeyEvent.VK_DOWN) DOWN.operation(g, r1.x, r1.y);
        else if (key == KeyEvent.VK_UP) UP.operation(g, r1.x, r1.y);
        else if (key == KeyEvent.VK_LEFT) LEFT.operation(g, r1.x, r1.y);
        else if (key == KeyEvent.VK_RIGHT) RIGHT.operation(g, r1.x, r1.y);
        else {
            if (stand == 'f') STAND_FRONT.operation(g, r1.x, r1.y);
            else if (stand == 'b') STAND_BACK.operation(g, r1.x, r1.y);
            else if (stand == 'l') STAND_LEFT.operation(g, r1.x, r1.y);
            else if (stand == 'r') STAND_RIGHT.operation(g, r1.x, r1.y);
        }
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(33); // Approximately 30 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If dialogue is active, handle dialogue interactions
        if (isDialogueActive) {
            dialogueManager.update(e);
            if (!dialogueManager.isActive()) {
                isDialogueActive = false;
            }
            repaint();
            return;
        }

        // Handle key presses for movement
        key = e.getKeyCode();
        handleMovement(key);

        // Collision detection
        handleCollisions();

        repaint();
    }

    private void handleMovement(int key) {
        switch (key) {
            case KeyEvent.VK_LEFT:
                if (cameraX > tileSize && r1.x == DefaultX) {
                    cameraX -= speed;
                    stand = 'l';
                } else {
                    r1.diChuyen(key);
                    stand = 'l';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (cameraX < backgroundImage.getWidth() - ScreenWidth - tileSize && r1.x == DefaultX) {
                    cameraX += speed;
                    stand = 'r';
                } else {
                    r1.diChuyen(key);
                    stand = 'r';
                }
                break;
            case KeyEvent.VK_UP:
                if (cameraY > tileSize && r1.y == DefaultY) {
                    cameraY -= speed;
                    stand = 'b';
                } else {
                    r1.diChuyen(key);
                    stand = 'b';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (cameraY < backgroundImage.getHeight() - ScreenHeight - tileSize && r1.y == DefaultY) {
                    cameraY += speed;
                    stand = 'f';
                } else {
                    r1.diChuyen(key);
                    stand = 'f';
                }
                break;
        }
    }
    // xử lý va chạm 
    private void handleCollisions() {
        // Library collision area
        hcnTest = new Rectangle(2040 - cameraX, 766 - cameraY, 1012, 1392);
        
        // Complex polygon area
        Polygon poly = new Polygon(
            new int[] {3302 - cameraX, 1958 - cameraX, 1958 - cameraX, 1750 - cameraX, 1750 - cameraX, 1535 - cameraX, 860 - cameraX, 
            1535 - cameraX, 1535 - cameraX, 432 - cameraX, 432 - cameraX, 0 - cameraX, 0 - cameraX, 3302 - cameraX},
            new int[] {471 - cameraY, 471 - cameraY, 561 - cameraY, 561 - cameraY, 620 - cameraY, 620 - cameraY, 1295 - cameraY, 
            1295 - cameraY, 2181 - cameraY, 2181 - cameraY, 2220 - cameraY, 2220 - cameraY, 0 - cameraY, 0 - cameraY},
            14
        );
        
        polyArea = new Area(poly);
        rectArea = new Area(r1.rect);
        polyArea.intersect(rectArea);

        // Collision checks
        if (r1.vaCham(hcnTest) || !polyArea.isEmpty() || r1.x > 908 || r1.y > 793 || r1.x < 33) {
            r1.luiLai();
            cameraX = preCamX;
            cameraY = preCamY;

        } else {
            preCamX = cameraX;
            preCamY = cameraY;
            r1.preX = r1.x;
            r1.preY = r1.y;
        }

        // Girl interaction
        if (r1.vaCham(Girl_Rec)) {
            System.out.println("Girl");
            // Trigger dialogue on collision
            isDialogueActive = true;
            dialogueManager.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        key = -1;
    }
   

    // Dialogue initialization method
    private List<Dialogue> loadDialogues() {
        List<Dialogue> dialogues = new ArrayList<>();
        dialogues.add(new Dialogue(
            "girl_start",
            "Chào bạn! Bạn có vẻ như đang tìm kiếm điều gì đó?",
            List.of(
                new Response("con chó", "girl_info_search"),
                new Response("con mèo", "girl_help"),
                new Response("thằng Trương Viết Bạn", "girl_passing")
            )
        ));
        dialogues.add(new Dialogue(
            "girl_info_search",
        "Tôi có thể giúp bạn tìm thông tin. Bạn quan tâm đến lĩnh vực nào?",
            List.of(
                new Response("thằng Cường", "girl_library_info"),
            new Response("Triều Cường", "girl_study_info"),
            new Response("Bạn chó", "girl_start")
            )
        ));
        dialogues.add(new Dialogue(
            "girl_library_info",
        "Thư viện Tạ Quang Bửu là một trong những thư viện lớn nhất Đông Nam Á, với hơn 1 triệu đầu sách.",
            List.of(
                new Response("fwefne", "girl_enter_library"),
            new Response("ểwr", "girl_info_search")
            )
        ));
        dialogues.add(new Dialogue(
            "girl_enter_library",
        "Rất tiếc, thư viện hiện đang đóng cửa. Bạn có thể quay lại vào giờ mở cửa.",
            List.of(
                new Response("Hiểu rồi", "girl_start")
            )
        ));
        dialogues.add(new Dialogue(
            "girl_help",
        "Tôi có thể giúp gì cho bạn? Bạn cần hỗ trợ về điều gì?",
            List.of(
                new Response("Hướng dẫn đến thư viện", "girl_library_direction"),
            new Response("Quay lại", "girl_start")
            )
        ));
        dialogues.add(new Dialogue(
            "girl_library_direction",
            "Thư viện nằm ở trung tâm khuôn viên, bạn chỉ cần đi thẳng về hướng bắc.",
            List.of(
                new Response("Hướng dẫn đến thư viện", "girl_library_direction"),
            new Response("Quay lại", "girl_start")
            )
        ));
        
        return dialogues;
    }
}