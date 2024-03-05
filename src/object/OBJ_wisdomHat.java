package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_wisdomHat extends Entity {

    public OBJ_wisdomHat(GamePanel gp) {
        super(gp);
        name = "Wisdom Hat";
        down1 = setup("/objects/wisdomHat",gp.tileSize,gp.tileSize);
        //defenseValue = 1;
    }
}
