package object;

import main.GamePanel;

public class OBJ_door extends entity.Entity {
    GamePanel gp;

    public OBJ_door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Door";
        down1 = setup("/objects/door");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        type = typeObstacle;
    }

    public void interact(){
        gp.gameState = gp.doorState;
        gp.ui.currentDialogue = "нужен ключик чтобы отпереть";
    }
}