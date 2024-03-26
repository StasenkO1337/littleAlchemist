package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_bed extends Entity {
    public OBJ_bed(GamePanel gp) {
        super(gp);
        name = "Bed";
        down1 = setup("/objects/bed");
        direction = "down";
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
        gp.gameState = gp.bedState;
    }
}