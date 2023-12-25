package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRectangle[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRectangle = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRectangle[col][row] = new EventRect();
            eventRectangle[col][row].x = 23;
            eventRectangle[col][row].y = 23;
            eventRectangle[col][row].width = 2;
            eventRectangle[col][row].height = 2;
            eventRectangle[col][row].eventRectDefaultX = eventRectangle[col][row].x;
            eventRectangle[col][row].eventRectDefaultY = eventRectangle[col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }


    }

    public void checkEvent() {
        //проверка на то чтобы не застрять в евенте
        int distanceX = Math.abs(gp.player.worldX - previousEventX);
        int distanceY = Math.abs(gp.player.worldX - previousEventY);
        int distance = Math.max(distanceX, distanceY);
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }

        if (canTouchEvent == true){
            if (hit(49, 58, "right") == true) {
                gp.player.direction = "left";
                damagePit(49,43,gp.dialogueState);
            }
        }

        if (hit(41, 52  , "any") == true) {
            healingPool(gp.dialogueState);
        }

        if (hit(51, 49, "any") == true) {
            teleport(gp.dialogueState, 45, 55);
        }
        if (hit(45, 55, "any") == true) {
            teleport(gp.dialogueState, 51, 49);
        }
    }

    public void damagePit(int col,int row,int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "попався в ловушку";
        gp.player.life -= 1;
        //eventRectangle[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRectangle[col][row].x = col * gp.tileSize + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * gp.tileSize + eventRectangle[col][row].y;

        if (gp.player.solidArea.intersects(eventRectangle[col][row]) && eventRectangle[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) ||
                    reqDirection.contentEquals("any") ) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRectangle[col][row].x = eventRectangle[col][row].eventRectDefaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].eventRectDefaultY;

        return hit;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.ePressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "вам дали святой водички и отпустили\nгрехи";
            gp.player.life = gp.player.maxLife;
        }
        gp.keyH.ePressed = false;
    }

    public void teleport(int gameState, int x, int y) {
        if (gp.keyH.ePressed == true) {
            gp.ui.currentDialogue = "тп на базу";
            gp.gameState = gameState;
            gp.player.worldX = x * gp.tileSize;
            gp.player.worldY = y * gp.tileSize;
        }
        gp.keyH.ePressed = false;
    }
}
