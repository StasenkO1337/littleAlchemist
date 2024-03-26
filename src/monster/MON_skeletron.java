package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_coin;
import object.OBJ_icicle;

import java.util.Random;

public class MON_skeletron extends Entity {
    public final static String monName = "Skeletron";
    public MON_skeletron(GamePanel gp) {
        super(gp);
        boss = true;
        name = monName;
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 200;
        life = maxLife;
        type = typeMonster;
        attack = 10;
        defense = 2;
        exp = 100;
        knockBackPower = 5;

        int size = gp.tileSize*5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1Duration = 25;
        motion2Duration = 60;
        getImage();
        getAttackImage();
    }

    public void getImage() {
        int i = 5;
        if (inRage == false) {
            up1 = setup("/monster/skeletron/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/skeletron/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/skeletron/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/skeletron/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/skeletron/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/skeletron/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/skeletron/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/skeletron/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
        } else {
            up1 = setup("/monster/skeletron/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/skeletron/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/skeletron/skeletonlord_phase2_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/skeletron/skeletonlord_phase2_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/skeletron/skeletonlord_phase2_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/skeletron/skeletonlord_phase2_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/skeletron/skeletonlord_phase2_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/skeletron/skeletonlord_phase2_right_2", gp.tileSize * i, gp.tileSize * i);
        }
    }
    public void getAttackImage(){
        int i = 5;
        if (inRage == false) {
            AU1 = setup("/monster/skeletron/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * 2 * i);
            AU2 = setup("/monster/skeletron/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * 2 * i);
            AD1 = setup("/monster/skeletron/skeletonlord_attack_down_1", gp.tileSize * i, gp.tileSize * 2 * i);
            AD2 = setup("/monster/skeletron/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * 2 * i);
            AL1 = setup("/monster/skeletron/skeletonlord_attack_left_1", 2 * gp.tileSize * i, gp.tileSize * i);
            AL2 = setup("/monster/skeletron/skeletonlord_attack_left_2", 2 * gp.tileSize * i, gp.tileSize * i);
            AR1 = setup("/monster/skeletron/skeletonlord_attack_right_1", 2 * gp.tileSize * i, gp.tileSize * i);
            AR2 = setup("/monster/skeletron/skeletonlord_attack_right_2", 2 * gp.tileSize * i, gp.tileSize * i);
        } else{
                AU1 = setup("/monster/skeletron/skeletonlord_phase2_attack_up_1", gp.tileSize * i, gp.tileSize * 2 * i);
            AU2 = setup("/monster/skeletron/skeletonlord_phase2_attack_up_2", gp.tileSize * i, gp.tileSize * 2 * i);
            AD1 = setup("/monster/skeletron/skeletonlord_phase2_attack_down_1", gp.tileSize * i, gp.tileSize * 2 * i);
            AD2 = setup("/monster/skeletron/skeletonlord_phase2_attack_down_2", gp.tileSize * i, gp.tileSize * 2 * i);
            AL1 = setup("/monster/skeletron/skeletonlord_phase2_attack_left_1", 2 * gp.tileSize * i, gp.tileSize * i);
            AL2 = setup("/monster/skeletron/skeletonlord_phase2_attack_left_2", 2 * gp.tileSize * i, gp.tileSize * i);
            AR1 = setup("/monster/skeletron/skeletonlord_phase2_attack_right_1", 2 * gp.tileSize * i, gp.tileSize * i);
            AR2 = setup("/monster/skeletron/skeletonlord_phase2_attack_right_2", 2 * gp.tileSize * i, gp.tileSize * i);
        }
    }
    public void setAction() {
        if (inRage == false && life < maxLife/2){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed = 5;
            defense = 4;
        }
        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(60);
        } else {

            //рандом движения
            getRandomDirection(60);
        }
        if (attacking == false){
            checkAttackOrNot(60,gp.tileSize*7,gp.tileSize*5);
        }
    }
    public void update() {
        super.update();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;
        if (onPath == false && tileDistance < 5) {
            onPath = true;
        }
        if (onPath == true && tileDistance > 15) {
            onPath = false;
        }
    }


    public void damageReaction() {
        actionLockCounter = 0;

    }
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;
        dropItem(new OBJ_coin(gp));
        if (i > 50) {
            dropItem(new OBJ_icicle(gp));
        }
    }
}


