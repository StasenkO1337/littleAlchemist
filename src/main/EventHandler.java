package main;
import entity.Entity;
public class EventHandler {
    GamePanel gp;
    EventRect eventRectangle[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tmpMap,tmpX,tmpY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRectangle = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRectangle[map][col][row] = new EventRect();
            eventRectangle[map][col][row].x = 8;
            eventRectangle[map][col][row].y = 8;
            eventRectangle[map][col][row].width = 32;
            eventRectangle[map][col][row].height = 32;
            eventRectangle[map][col][row].eventRectDefaultX = eventRectangle[map][col][row].x;
            eventRectangle[map][col][row].eventRectDefaultY = eventRectangle[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow - 1){
                    row = 0;
                    map++;
                }
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
            if(hit(1,48,24,"any") == true){teleport(0,2,25, gp.outside);}
            else if(hit(1,48,25,"any") == true){teleport(0,2,25, gp.outside);}
            else if(hit(1,48,26,"any") == true){teleport(0,2,26, gp.outside);}
            else if(hit(1,48,27,"any") == true){teleport(0,2,26, gp.outside);}

            else if(hit(0,1,25,"any") == true){teleport(1,46,25, gp.outside);}
            else if(hit(0,1,26,"any") == true){teleport(1,46,26, gp.outside);}
            else if(hit(0,11,19,"up") == true){speak(gp.npc[0][1]);}
            else if(hit(0,25,1,"any") == true){teleport(2,25,47, gp.outside);}
            else if(hit(0,26,1,"any") == true){teleport(2,25,47, gp.outside);}

            else if(hit(2,24,48,"any") == true){teleport(0,25,2, gp.outside);}
            else if(hit(2,25,48,"any") == true){teleport(0,25,2, gp.outside);}

            else if(hit(2,48,27,"any") == true){teleport(3,2,37, gp.dungeon);}
            else if(hit(2,48,28,"any") == true){teleport(3,2,37, gp.dungeon);}

            else if(hit(3,1,37,"any") == true){teleport(2,47,27, gp.outside);}
            else if(hit(3,14,17,"any") == true){teleport(4,25,47, gp.dungeon);}

            else if(hit(4,25,48,"any") == true){teleport(3,15,17, gp.dungeon);}
        }

    }

    public void speak(Entity entity){
        if (gp.keyH.fPressed == true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "попався в ловушку";
        gp.player.life -= 1;
        //eventRectangle[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public boolean hit(int map,int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRectangle[map][col][row].x = col * gp.tileSize + eventRectangle[map][col][row].x;
            eventRectangle[map][col][row].y = row * gp.tileSize + eventRectangle[map][col][row].y;
            if (gp.player.solidArea.intersects(eventRectangle[map][col][row]) && eventRectangle[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRectangle[map][col][row].x = eventRectangle[map][col][row].eventRectDefaultX;
            eventRectangle[map][col][row].y = eventRectangle[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.fPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "вам дали святой водички и отпустили\nгрехи";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
        gp.keyH.fPressed = false;
    }

    public void teleport(int mapNum, int x, int y, int area) {
        gp.gameState = gp.transitionState;
        gp.currentArea = area;
        tmpMap = mapNum;
        tmpX = x;
        tmpY = y;

        canTouchEvent = false;

        gp.playSE(2);
    }
}
