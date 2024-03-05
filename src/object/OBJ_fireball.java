package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;
public class OBJ_fireball extends Projectile {

    GamePanel gp;

    public OBJ_fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 6;
        attack = 5;
        knockBackPower = 2;
        maxLife = 80;
        life = maxLife;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        down1 = setup("/objects/fireball/fireballDown1");
        down2 = setup("/objects/fireball/fireballDown2");
        down3 = setup("/objects/fireball/fireballDown3");
        up1 = setup("/objects/fireball/fireballUp1");
        up2 = setup("/objects/fireball/fireballUp2");
        up3 = setup("/objects/fireball/fireballUp3");
        left1 = setup("/objects/fireball/fireballLeft1");
        left2 = setup("/objects/fireball/fireballLeft2");
        left3 = setup("/objects/fireball/fireballLeft3");
        right1 = setup("/objects/fireball/fireballRight1");
        right2 = setup("/objects/fireball/fireballRight2");
        right3 = setup("/objects/fireball/fireballRight3");
    }

    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if (user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }

    public Color getParticleColor(){
        Color color = new Color(255, 143, 18);
        return color;
    }

    public int getParticleSize(){
        int size = 5;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 2;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 15;
        return maxLife;
    }
}
