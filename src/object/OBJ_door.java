package object;

import main.GamePanel;

public class OBJ_door extends entity.Entity {

    public OBJ_door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/objects/door");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}