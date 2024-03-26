package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_pick extends Entity {
    public OBJ_pick(GamePanel gp) {
        super(gp);
        name = "Pickaxe";
        down1 = setup("/objects/pickaxe");
        description = name + "\nДауби дауби";
        attackValue = 1;
        knockBackPower = 4;
        type = typePick;

        attackArea.width = 30;
        attackArea.height = 30;
        motion1Duration = 10;
        motion2Duration = 20;
    }
}
