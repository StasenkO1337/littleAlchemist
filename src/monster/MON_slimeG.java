package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_slimeG extends Entity {

    public MON_slimeG(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 2;
        life = maxLife;
        type = 2;

        solidArea.x = 2;
        solidArea.y = 16;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage(){
        up1 = setup("/monster/slimeG/slimeG1");
        up2 = setup("/monster/slimeG/slimeG2");
        down1 = setup("/monster/slimeG/slimeG1");
        down2 = setup("/monster/slimeG/slimeG2");
        left1 = setup("/monster/slimeG/slimeG1");
        left2 = setup("/monster/slimeG/slimeG2");
        right1 = setup("/monster/slimeG/slimeG1");
        right2 = setup("/monster/slimeG/slimeG2");
    }

    public void setAction(){
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
}
