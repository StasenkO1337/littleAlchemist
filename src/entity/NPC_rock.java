package entity;

import main.GamePanel;
import object.OBJ_door_iron;
import tile_interactive.IT_plate;
import tile_interactive.interactiveTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NPC_rock extends Entity{

    public final static String npcName = "Rock";

    public int count = 0;

    public NPC_rock(GamePanel gp) {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;
        type = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getNPCImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0] = "Я Саня, камень";
    }

    public void getNPCImage() {
        up1 = setup("/objects/tileInteractive/rock");
        up2 = setup("/objects/tileInteractive/rock");
        down1 = setup("/objects/tileInteractive/rock");
        down2 = setup("/objects/tileInteractive/rock");
        left1 = setup("/objects/tileInteractive/rock");
        left2 = setup("/objects/tileInteractive/rock");
        right1 = setup("/objects/tileInteractive/rock");
        right2 = setup("/objects/tileInteractive/rock");
    }

    public void setAction() {

    }

    @Override
    public void move(String d){
        this.direction = d;

        checkCollision();

        if (collisionOn == false){
            switch (direction){
                case "up":worldY -= speed;break;
                case "down":worldY += speed;break;
                case "left":worldX -= speed;break;
                case "right":worldX += speed;break;
            }
        }

        detectPlate();
    }

    public void update(){}

    public void detectPlate() {
        ArrayList<interactiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        for (int i = 0; i < gp.iTile[1].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name != null &&
                    gp.iTile[gp.currentMap][i].name.equals(IT_plate.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }

        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] != null &&
                    gp.npc[gp.currentMap][i].name.equals(NPC_rock.npcName)) {
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        for (int i = 0;i<plateList.size();i++){
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance,yDistance);

            if (distance < 24){
                if (linkEntity == null) {
                    linkEntity = plateList.get(i);
                    gp.playSE(2);
                }
            } else {
                if (linkEntity == plateList.get(i)) {
                    linkEntity = null;
                }
            }
        }

        for (int i = 0; i < rockList.size();i++){
            if (rockList.get(i).linkEntity != null){
                count++;
            }
        }

        if (count == rockList.size()){
            for (int i = 0; i < gp.obj[1].length;i++){
                if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_door_iron.objName)){
                    gp.obj[gp.currentMap][i] = null;
                    gp.playSE(2);
                }
            }
        }
        count = 0;
    }

    @Override
    public void speak() {
        super.speak();
    }
}
