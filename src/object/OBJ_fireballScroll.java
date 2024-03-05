package object;

import main.GamePanel;

public class OBJ_fireballScroll extends entity.Entity {

    public OBJ_fireballScroll(GamePanel gp) {
        super(gp);
        name = "Fireball scroll";
        down1 = setup("/objects/fireballScroll");
        description = name + "\nFire!";
        type = typeMagic;
    }
}