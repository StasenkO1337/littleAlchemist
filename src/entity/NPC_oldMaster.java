package entity;

import main.GamePanel;

import java.util.Random;


public class NPC_oldMaster extends Entity {
    public NPC_oldMaster(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        type = 0;

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

        actionLockCounter++;

        if (actionLockCounter == 60) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void speak() {
        super.speak();
    }
}
