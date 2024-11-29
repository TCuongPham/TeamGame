package chapter.chap0.src.object;

import chapter.chap0.src.entity.Entity;
import chapter.chap0.src.main.GamePanel;
import org.ietf.jgss.GSSManager;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    int value = 10;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "potion red";
        type = type_consumable;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "Nếu bạn uống " + name + " thì mạng\n sống của bạn sẽ được\n công thêm " + value;
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Mạng sống của bạn đã được phục hồi";
        entity.life += value;
        if(entity.life > entity.maxLife) {
            entity.life = entity.maxLife;
        }
        gp.playSE(2);
    }

}
