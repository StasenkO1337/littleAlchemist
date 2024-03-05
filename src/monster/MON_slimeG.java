package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_coin;
import object.OBJ_fan;
import object.OBJ_icicle;

import java.util.Random;

public class MON_slimeG extends Entity {

    public MON_slimeG(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 15;
        life = maxLife;
        type = typeMonster;
        attack = 3;
        defense = 0;
        exp = 3;

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
        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalrow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol,goalrow);
        } else {
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

    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);

        int tileDistance = (xDistance + yDistance)/ gp.tileSize;
        if(onPath == false && tileDistance < 3){
            onPath = true;
        }
        if(onPath == true && tileDistance > 8){
            onPath = false;
        }
    }

    public void checkDrop(){
        int i = new Random().nextInt( 100)+1;
        dropItem(new OBJ_coin(gp));
        if ( i > 50){
            dropItem(new OBJ_icicle(gp));
        }
    }
}
