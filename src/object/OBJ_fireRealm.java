package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_fireRealm extends Entity {
    public OBJ_fireRealm(GamePanel gp) {
        super(gp);
        name = "Fire realm";
        down1 = setup("/objects/fireRealm",gp.tileSize,gp.tileSize);
        attackValue = 5;
        type = typeWeapon;
        knockBackPower = 3;
        description = name + "\nallows you to let\nin slimes";
        attackArea.width = 52;
        attackArea.height = 52;
        motion1Duration = 10;
        motion2Duration = 30;
    }
}
