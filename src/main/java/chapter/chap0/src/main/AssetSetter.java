package chapter.chap0.src.main;

import chapter.chap0.src.entity.NPC_Oldman;
import chapter.chap0.src.monster.MON_GreenSlime;
import chapter.chap0.src.object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        int mapNum = 0;
        int i = 0;

//        gp.obj[mapNum][i] = new OBJ_Key(gp);
//        gp.obj[mapNum][i].worldX = gp.tileSize*23;
//        gp.obj[mapNum][i].worldY = gp.tileSize*23;
//        i++;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*33;
        i++;

//        gp.obj[mapNum][i] = new OBJ_Key(gp);
//        gp.obj[mapNum][i].worldX = gp.tileSize*21;
//        gp.obj[mapNum][i].worldY = gp.tileSize*23;
//        i++;

        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*11;
        gp.obj[mapNum][i].worldY = gp.tileSize*32;
        i++;

        gp.obj[mapNum][i] = new OBJ_Shiled_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*10;
        gp.obj[mapNum][i].worldY = gp.tileSize*33;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*35;
        gp.obj[mapNum][i].worldY = gp.tileSize*35;
        ++i;

        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*10;
        gp.obj[mapNum][i].worldY = gp.tileSize*7;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*10;
        gp.obj[mapNum][i].worldY = gp.tileSize*11;


    }

    public void setNPC() {

        int mapNum = 0;

//        gp.npc[mapNum][0] = new NPC_Oldman(gp);
//        gp.npc[mapNum][0].worldX = gp.tileSize * 21;
//        gp.npc[mapNum][0].worldY = gp.tileSize * 21;

//        gp.npc[mapNum][1] = new NPC_Oldman(gp);
//        gp.npc[mapNum][1].worldX = gp.tileSize * 11;
//        gp.npc[mapNum][1].worldY = gp.tileSize * 21;
//
//        gp.npc[mapNum][2] = new NPC_Oldman(gp);
//        gp.npc[mapNum][2].worldX = gp.tileSize * 31;
//        gp.npc[mapNum][2].worldY = gp.tileSize * 21;
//
//        gp.npc[mapNum][3] = new NPC_Oldman(gp);
//        gp.npc[mapNum][3].worldX = gp.tileSize * 21;
//        gp.npc[mapNum][3].worldY = gp.tileSize * 11;
//
//        gp.npc[mapNum][4] = new NPC_Oldman(gp);
//        gp.npc[mapNum][4].worldX = gp.tileSize * 21;
//        gp.npc[mapNum][4].worldY = gp.tileSize * 31;

    }


    public void setMonster() {

        int mapNum = 0;
        int i=0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*36;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*37;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*38;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*33;
        gp.monster[mapNum][i].worldY = gp.tileSize*38;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*36;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*12;
        gp.monster[mapNum][i].worldY = gp.tileSize*32;


    }


}
