package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_fan extends entity.Entity {

    public OBJ_fan(GamePanel gp) {
        super(gp);
        value = 1;
        name = "Fan";
        down1 = setup("/objects/fan");
        type = typePickUpOnly;
    }

    public void use(Entity entity){
        gp.ui.addMessage("ускорился");
        gp.player.speed += value;
    }
}