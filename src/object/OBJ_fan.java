package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_fan extends entity.Entity {

    public OBJ_fan(GamePanel gp) {
        super(gp);
        name = "Fan";
        down1 = setup("/objects/fan");
    }
}