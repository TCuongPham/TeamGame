package chapter.chap0.src.object;

import chapter.chap0.src.entity.Entity;
import chapter.chap0.src.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {



    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = "boots";

        type = type_shield;

        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
        collision = true;
    }

}
