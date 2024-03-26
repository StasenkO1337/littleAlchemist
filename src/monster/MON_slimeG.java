package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_coin;
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
        knockBackPower = 1;

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

    public void setAction() {
        if (onPath == true) {
            //проверка на "отставния"
            checkStopChasingOrNot(gp.player,8);
            //выбор направения следования
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            //выстрелы если нужно
        } else {
            //проверка на агр
            checkStartChasingOrNot(gp.player,3);
            //рандом движения
            getRandomDirection(60);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }


    public void checkDrop(){
        int i = new Random().nextInt( 100)+1;
        dropItem(new OBJ_coin(gp));
        if ( i > 50){
            dropItem(new OBJ_icicle(gp));
        }
    }
}
