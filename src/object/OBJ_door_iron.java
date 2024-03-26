package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_door_iron extends Entity {

    public static String objName = "Iron Door";

    public OBJ_door_iron(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Iron Door";
        down1 = setup("/objects/Idoor");
        left1 = setup("/objects/Idoor1");
        right1 =setup("/objects/Idoor2");
        up1 = setup("/objects/Idoor3");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        type = typeObstacle;
    }

}
