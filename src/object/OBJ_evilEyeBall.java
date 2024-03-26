package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;
public class OBJ_evilEyeBall extends Projectile {

    GamePanel gp;

    public OBJ_evilEyeBall(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "evilEyeBall";
        speed = 4;
        attack = 2;
        maxLife = 80;
        life = maxLife;
        useCost = 0;
        alive = false;
        getImage();

    }

    public void getImage(){
        down1 = setup("/monster/evilEye/evilEyeBall");
        down2 = setup("/monster/evilEye/evilEyeBall");
        down3 = setup("/monster/evilEye/evilEyeBall");
        up1 = setup("/monster/evilEye/evilEyeBall");
        up2 = setup("/monster/evilEye/evilEyeBall");
        up3 = setup("/monster/evilEye/evilEyeBall");
        left1 = setup("/monster/evilEye/evilEyeBall");
        left2 = setup("/monster/evilEye/evilEyeBall");
        left3 = setup("/monster/evilEye/evilEyeBall");
        right1 = setup("/monster/evilEye/evilEyeBall");
        right2 = setup("/monster/evilEye/evilEyeBall");
        right3 = setup("/monster/evilEye/evilEyeBall");
    }

    public Color getParticleColor(){
        Color color = new Color(190, 64, 86);
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
