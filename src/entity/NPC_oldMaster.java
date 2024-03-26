package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;


public class NPC_oldMaster extends Entity {
    public NPC_oldMaster(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        type = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getNPCImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0] = "Здаров педик, че как сам";
        dialogues[1] = "Вижу дела твои не очень :)";
        dialogues[2] = "Ну ничего страшного, щас соберешь приколов всяких \nновое тело себе сбацаешь";
        dialogues[3] = "Мне можешь продать лишнее, или купить нужное";
        dialogues[4] = "Зайди еще к пожилому дрочиле, купи заклинаний, \nтебе пригодятся";
        dialogues[5] = "Бывай";
    }

    public void getNPCImage() {
        up1 = setup("/npc/oldMaster/up1");
        up2 = setup("/npc/oldMaster/up2");
        down1 = setup("/npc/oldMaster/down1");
        down2 = setup("/npc/oldMaster/down2");
        left1 = setup("/npc/oldMaster/left1");
        left2 = setup("/npc/oldMaster/left2");
        right1 = setup("/npc/oldMaster/right1");
        right2 = setup("/npc/oldMaster/right2");
    }

    public void setAction() {
        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);
        }

        else {
            actionLockCounter++;
            if (actionLockCounter == 60) {
                getRandomDirection(30);
            }
        }
    }

    @Override
    public void speak() {
        super.speak();
        //onPath = true;
    }
}
