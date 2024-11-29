package chapter.chap0.src.object;

import chapter.chap0.src.entity.Entity;
import chapter.chap0.src.main.GamePanel;

public class OBJ_Axe extends Entity {

    GamePanel gp;

    public OBJ_Axe(GamePanel gp) {
        super(gp);
        name = "axe";
        type = type_axe;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        collision = true;
        description = "[ " + name + " ]" + " An new sword";
    }

}
