package chapter.chap0.src.object;

import chapter.chap0.src.entity.Projectile;
import chapter.chap0.src.main.GamePanel;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/Projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Projectile/fireball_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("/Projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Projectile/fireball_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("/Projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Projectile/fireball_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("/Projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Projectile/fireball_right_2", gp.tileSize, gp.tileSize);
    }

}
