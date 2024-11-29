package chapter.chap0.src.main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canToachEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow && map < gp.maxMap) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

            if(row == gp.maxWorldRow) {
                row = 0;
                map++;
            }

        }

    }

    public void checkEvent() {
        //Check if the player character is more than 1 title away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canToachEvent = true;
        }

        if(canToachEvent == true) {
            if(hit(0,24,15,"right") == true) { damagePit(gp.dialogueState); }
            else if(hit(0,23,19,"any") == true) { damagePit(gp.dialogueState); }
            //if(hit(24,15,"right") == true) { teleport(gp.dialogueState); }
            else if(hit(0,23,7 ,"up") == true) { healingPool(gp.dialogueState); }
            else if(hit(0,10,7,"any") == true) {teleport(1,23,21);}
            else if(hit(1,12,13,"any") == true) {teleport(0,23,21);}
        }

    }

    public boolean hit(int map,int col, int row, String reqDirection) {

        boolean hit = false;

        //eventRect[col][row] = new EventRect();

        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if(gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }


        return hit;

    }

//    public void teleport(int gameState) {
//        gp.gameState = gameState;
//        gp.ui.currentDialogue = "Teleport!";
//        gp.player.worldX = gp.tileSize*37;
//        gp.player.worldY = gp.tileSize*10;
////        gp.gameState = gameState;
//    }

    public void damagePit(int gameState) {

        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
//        eventRect[col][row].eventDone = true;
        canToachEvent = false;

    }

    public void healingPool(int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            gp.ui.currentDialogue = "You drink the water.\nYour life has been recorved!";
            gp.player.life = gp.player.maxLife;
            gp.aSetter.setMonster();
        }
        gp.keyH.enterPressed = false;
    }

    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize*col;
        gp.player.worldY = gp.tileSize*row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canToachEvent = false;
        gp.playSE(11);
    }

}
