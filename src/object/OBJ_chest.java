package object;

import main.GamePanel;

public class OBJ_chest extends entity.Entity {

    public OBJ_chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/objects/chest");
        collision = true;
    }
}
