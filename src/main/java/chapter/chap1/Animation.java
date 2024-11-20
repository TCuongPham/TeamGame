package chapter.chap1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Animation {
    BufferedImage [] listImages = new BufferedImage[4];
    public String nameOfList = "character_move_down";
    public int tileSize = 25;
    public Animation() {};
    int index = 0;
    double one_loop_time = 1.0 / (30.0 / 4);
    double pre_loop_time = -1.0;
    double start_time;
    public String state = "run";
    boolean Switch = true;
    public void getImage() {
        for (int i = 1; i <= 4; i++) {
            try {
                listImages[i-1] = ImageIO.read(new File("pic/" + nameOfList + " ("+ i +")" + ".png"));
            } catch (IOException e) {
                System.out.println("Can't find image");
                e.printStackTrace();
            }
        }
    }
    private void update() {

        if((System.currentTimeMillis() - pre_loop_time) / 1000 > one_loop_time)  {
            pre_loop_time = System.currentTimeMillis();
            if(index == listImages.length - 1)
                index = 0;
            else
                index += 1;

        }
    }

    public void operation(Graphics graphics, int x, int y) {
        
        if(Switch){
            state = "run";
            start_time = System.currentTimeMillis();
            Switch = false;

        }
        if(pre_loop_time == -1.0) {
            pre_loop_time = System.currentTimeMillis();
        }
        update();
        graphics.drawImage(listImages[index], x, y, tileSize, tileSize, null);
    }
}
