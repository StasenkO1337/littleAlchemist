package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_key extends entity.Entity {

    public OBJ_key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/objects/key");
        description = name + "\nOpen all what you see";
        type = typePickUpOnly;
    }

    public void use(Entity entity){
        gp.player.haveKey1 = true;
    }
}
