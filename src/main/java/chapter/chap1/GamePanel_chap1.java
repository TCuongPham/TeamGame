package chapter.chap1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.Timer;

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

    //suy nghĩ
    private String suynghi = " ";
    private boolean showSuynghi = false;
    private  javax.swing.Timer suynghiTimer;
    private int buocsuynghi = 0;
    private String[] cacsuynghi = {
        "Lúc nào cx là những giấc mơ quái dị đó",
        "Ngủ trong lớp mà cũng gặp, mệt mỏi quá",
        "May mà thầy Hóa dễ nên không bị mắng",
        "Điểm danh xong rồi, lên thư viện ngủ tiếp vậy",

    };

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
        Suynghitrongem();
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

    private void Suynghitrongem() {
        suynghiTimer = new Timer (2000, e -> {
            if (buocsuynghi < cacsuynghi.length){
                suynghi = cacsuynghi[buocsuynghi];
                showSuynghi = true;
                repaint();
                buocsuynghi++;

            }else{
                showSuynghi = false;
                suynghiTimer.stop();

            }
        });
        suynghiTimer.setInitialDelay(0);
        suynghiTimer.start();
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
        if(showSuynghi){
            g.setColor(new Color(0,0,0,150));
            g.fillRoundRect(r1.x - 40, r1.y - 40 , 300, 40, 10, 10);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN,14));
            g.drawString(suynghi,r1.x - 30, r1.y - 20);
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
            "linh_start",
            "Đạt ơi! Là cậu phải không? Trông phế thế này, tớ không nhận ra luôn đấy!",
            List.of(
                new Response("Ơ, cậu là ai?", "linh_reveal_identity"),
                new Response("Linh! Lâu rồi không gặp!", "linh_casual_response"),
                new Response("Phế chỗ nào? Cậu nhìn lại mình đi!", "linh_tease_response")
            )
        ));

        // Linh tiết lộ danh tính
        dialogues.add(new Dialogue(
            "linh_reveal_identity",
            "Tớ là Linh đây, bạn cũ của cậu. Quên tớ rồi à?",
            List.of(
                new Response("À, chào Linh! Lâu rồi không gặp.", "linh_continue_casual"),
                new Response("Haha, quên sao được. Dạo này cậu sao rồi?", "linh_ask_about_life")
            )
        ));

        // Đạt trêu Linh về vẻ ngoài
        dialogues.add(new Dialogue(
            "linh_tease_response",
            "Xấu? Xấu nhưng đầy cá tính! Khác với cậu, trông như cây tre trăm đốt bị vứt ra đồng!",
            List.of(
                new Response("Thôi, không đùa nữa. Dạo này cậu thế nào?", "linh_ask_about_life")
            )
        ));

        // Linh tiếp tục câu chuyện
        dialogues.add(new Dialogue(
            "linh_casual_response",
            "Tớ biết thế nào cậu cũng đỗ vào Bách Khoa mà, nên tớ cũng đăng ký vào đây. Cậu thấy sao, được học cùng tớ thích không?",
            List.of(
                new Response("Tuyệt vời! Lại được làm bạn đồng hành như xưa.", "linh_continue_casual"),
                new Response("Sợ cậu đuổi kịp tớ sao? Haha!", "linh_competitive_joke")
            )
        ));

        // Linh phản ứng khi Đạt đùa cạnh tranh
        dialogues.add(new Dialogue(
            "linh_competitive_joke",
            "Đuổi kịp? Tớ chỉ cần đi bộ cũng vượt cậu đấy! Thế dạo này cậu thế nào?",
            List.of(
                new Response("Không tốt lắm, tớ thường gặp ác mộng.", "linh_talk_about_nightmares"),
                new Response("Cũng ổn, nhưng mẹ cậu thế nào rồi?", "linh_ask_about_mother")
            )
        ));

        // Tiếp tục hội thoại thân thiện
        dialogues.add(new Dialogue(
            "linh_continue_casual",
            "Nhưng trông cậu không vui lắm nhỉ? Có chuyện gì sao?",
            List.of(
                new Response("Không có gì đâu, tớ chỉ khó ngủ thôi.", "linh_talk_about_nightmares"),
                new Response("Thực ra... tớ muốn kể với cậu một chuyện.", "linh_direct_confession")
            )
        ));

        // Hội thoại về ác mộng
        dialogues.add(new Dialogue(
            "linh_talk_about_nightmares",
            "Ác mộng á? Mơ thấy gì? Quái vật? Hay là tạch OOP?",
            List.of(
                new Response("Mơ thấy bị quái vật truy đuổi.", "linh_suggest_psychologist"),
                new Response("Mơ thấy bị tai nạn xe máy.", "linh_ask_about_mother")
            )
        ));

        // Linh gợi ý gặp bác sĩ tâm lý
        dialogues.add(new Dialogue(
            "linh_suggest_psychologist",
            "Cậu nên đi gặp bác sĩ tâm lý đấy! Đừng để quái vật đuổi trong giấc mơ rồi chuyển sang đời thật!",
            List.of(
                new Response("Haha, cảm ơn. Nhưng mẹ cậu thế nào rồi?", "linh_ask_about_mother"),
                new Response("Thực ra... tớ muốn nói một điều quan trọng.", "linh_direct_confession")
            )
        ));

        // Linh phản ứng về mẹ
        dialogues.add(new Dialogue(
            "linh_ask_about_mother",
            "À, mẹ tớ ổn rồi. Chỉ bị gãy chân, giờ khỏi hẳn. Nhưng hồi đó tớ giận kẻ gây tai nạn lắm.",
            List.of(
                new Response("Cậu có còn giận người đó không?", "linh_response_about_accident"),
                new Response("Thực ra, tớ muốn thú nhận một chuyện.", "linh_direct_confession")
            )
        ));

        // Linh phản ứng về tai nạn
        dialogues.add(new Dialogue(
            "linh_response_about_accident",
            "Ban đầu thì có, nhưng giờ thì không. Nếu không vì tai nạn đó, mẹ tớ đã không bỏ chuyến bay gặp khủng bố hôm ấy rồi...",
            List.of(
                new Response("Thực ra, tớ là người gây ra tai nạn đó.", "linh_direct_confession"),
                new Response("Tớ hiểu. Cảm ơn cậu đã kể.", "linh_talk_about_feelings")
            )
        ));

        // Đạt tỏ tình
        dialogues.add(new Dialogue(
            "linh_talk_about_feelings",
            "Thực ra, Linh... ngoài chuyện đó, tớ còn muốn nói một điều. Tớ đã thích cậu từ lâu rồi.",
            List.of(
                new Response("Linh phản ứng thế nào?", "linh_response_to_love")
            )
        ));

        // Linh đồng ý
        dialogues.add(new Dialogue(
            "linh_response_to_love",
            "Tớ cũng thích cậu lâu rồi, Đạt. Nhưng giờ cậu cần ổn định tâm lý trước đã, sau đó chúng ta sẽ nói chuyện nhiều hơn, được chứ?",
            List.of(
                new Response("Tớ đồng ý. Cảm ơn cậu, Linh.", "end_game")
            )
        ));

        // Kết thúc game
        dialogues.add(new Dialogue(
            "end_game",
            "Đạt trở về quê, nhận lỗi với gia đình Linh, tỏ tình thành công, và bắt đầu cuộc sống mới đầy hy vọng. Cuối cùng, cậu đã vượt qua ám ảnh quá khứ và tìm thấy tình yêu.",
            List.of()
        ));
        return dialogues;
    }
}