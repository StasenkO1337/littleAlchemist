package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_coin;
import object.OBJ_icicle;

import java.util.Random;

public class MON_orc extends Entity {
    public MON_orc(GamePanel gp) {
        super(gp);
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 60;
        life = maxLife;
        type = typeMonster;
        attack = 8;
        defense = 3;
        exp = 50;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1Duration = 40;
        motion2Duration = 85;
        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("/monster/orc/ou1");
        up2 = setup("/monster/orc/ou2");
        down1 = setup("/monster/orc/od1");
        down2 = setup("/monster/orc/od2");
        left1 = setup("/monster/orc/ol1");
        left2 = setup("/monster/orc/ol2");
        right1 = setup("/monster/orc/or1");
        right2 = setup("/monster/orc/or2");
    }
    public void getAttackImage(){
        AU1 = setup("/monster/orc/oau1", gp.tileSize, gp.tileSize * 2);
        AU2 = setup("/monster/orc/oau2", gp.tileSize, gp.tileSize * 2);
        AD1 = setup("/monster/orc/oad1", gp.tileSize, gp.tileSize * 2);
        AD2 = setup("/monster/orc/oad2", gp.tileSize, gp.tileSize * 2);
        AL1 = setup("/monster/orc/oal1", 2 * gp.tileSize, gp.tileSize);
        AL2 = setup("/monster/orc/oal2", 2 * gp.tileSize, gp.tileSize);
        AR1 = setup("/monster/orc/oar1", 2 * gp.tileSize, gp.tileSize);
        AR2 = setup("/monster/orc/oar2", 2 * gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        if (onPath == true) {
            //проверка на "отставния"
            checkStopChasingOrNot(gp.player, 8);
            //выбор направения следования
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            //проверка на агр
            checkStartChasingOrNot(gp.player, 3);
            //рандом движения
            getRandomDirection(60);
        }
        if (attacking == false){
            checkAttackOrNot(30,gp.tileSize*4,gp.tileSize);
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
        onPath = true;
    }
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;
        dropItem(new OBJ_coin(gp));
        if (i > 50) {
            dropItem(new OBJ_icicle(gp));
        }
    }
}
