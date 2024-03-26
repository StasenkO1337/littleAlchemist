package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_airRealm extends Entity {
    public OBJ_airRealm(GamePanel gp) {
        super(gp);
        name = "Air Realm";
        down1 = setup("/objects/airRealm",gp.tileSize,gp.tileSize);
        attackValue = 2;
        knockBackPower = 1;
        description = name + "\nallows you to let\nin slimes";
        type = typeWeapon;
        attackArea.width = 52;
        attackArea.height = 52;
        motion1Duration = 15;
        motion2Duration = 35;
    }
}
