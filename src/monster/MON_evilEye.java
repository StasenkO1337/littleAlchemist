package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_coin;
import object.OBJ_evilEyeBall;
import object.OBJ_icicle;

import java.util.Random;

public class MON_evilEye extends Entity {

    public MON_evilEye(GamePanel gp) {
        super(gp);
        name = "Evil eye";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 20;
        life = maxLife;
        type = typeMonster;
        attack = 2;
        defense = 1;
        exp = 10;
        projectile = new OBJ_evilEyeBall(gp);

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 24;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage(){
        up1 = setup("/monster/evilEye/evilEye");
        up2 = setup("/monster/evilEye/evilEye");
        down1 = setup("/monster/evilEye/evilEye");
        down2 = setup("/monster/evilEye/evilEye");
        left1 = setup("/monster/evilEye/evilEye");
        left2 = setup("/monster/evilEye/evilEye");
        right1 = setup("/monster/evilEye/evilEye");
        right2 = setup("/monster/evilEye/evilEye");
    }
    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);

        int tileDistance = (xDistance + yDistance)/ gp.tileSize;
        if(onPath == false && tileDistance < 5){
            onPath = true;
        }
        if(onPath == true && tileDistance > 15){
            onPath = false;
        }
    }
    public void setAction(){
        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalrow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol,goalrow);

            int i = new Random().nextInt(100) + 1;
            if (i > 98 && projectile.alive == false && shotAvailableCounter == 30){
                projectile.set(worldX,worldY,direction,true,this);
                for (int ii = 0; ii < gp.projectile[1].length ; ii ++){
                    if (gp.projectile[gp.currentMap][ii] == null){
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }
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
        onPath = true;
    }
    public void checkDrop(){
        int i = new Random().nextInt( 100)+1;
        dropItem(new OBJ_coin(gp));
        if ( i > 50){
            dropItem(new OBJ_icicle(gp));
        }
    }
}