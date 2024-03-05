package object;

import main.GamePanel;

public class OBJ_mana extends entity.Entity {

    public OBJ_mana(GamePanel gp) {
        super(gp);
        name = "Mana";
        image = setup("/objects/mana/manaF");
        image2 = setup("/objects/mana/manaB");
    }
}