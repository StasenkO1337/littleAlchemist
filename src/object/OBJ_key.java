package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_key extends entity.Entity {

    public OBJ_key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/objects/key");
    }
}