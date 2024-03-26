package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_coin;

import java.util.Random;

public class MON_bat extends Entity {
    public MON_bat(GamePanel gp) {
        super(gp);
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 6;
        life = maxLife;
        type = typeMonster;
        attack = 2;
        defense = 0;
        exp = 30;
        knockBackPower = 1;

        solidArea.x = 3;
        solidArea.y = 16;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage(){
        up1 = setup("/monster/bat/batD1");
        up2 = setup("/monster/bat/batD2");
        down1 = setup("/monster/bat/batD1");
        down2 = setup("/monster/bat/batD2");
        left1 = setup("/monster/bat/batD1");
        left2 = setup("/monster/bat/batD2");
        right1 = setup("/monster/bat/batD1");
        right2 = setup("/monster/bat/batD2");
    }

    public void setAction() {
        if (onPath == true) {
//            //проверка на "отставния"
//            checkStopChasingOrNot(gp.player,8);
//            //выбор направения следования
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
//            //выстрелы если нужно
        } else {
            //проверка на агр
            //checkStartChasingOrNot(gp.player,3);
            //рандом движения
            getRandomDirection(30);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
        //direction = gp.player.direction;
    }


    public void checkDrop(){
        int i = new Random().nextInt( 100)+1;
        dropItem(new OBJ_coin(gp));
        if ( i > 50){
            dropItem(new OBJ_coin(gp));
        }
    }
}

