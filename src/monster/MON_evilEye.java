package monster;

import entity.Entity;
import entity.Projectile;
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
        knockBackPower = 2;

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

    public void setAction(){
        if (onPath == true) {
            //проверка на "отставния"
            checkStopChasingOrNot(gp.player,15);
            //выбор направения следования
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            //выстрелы если нужно
            checkShootOrNot(100,30);
        } else {
            //проверка на агр
            checkStartChasingOrNot(gp.player,8);
            //рандом движения
            getRandomDirection(30);
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