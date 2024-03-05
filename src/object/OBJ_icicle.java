package object;

import main.GamePanel;

public class OBJ_icicle extends entity.Entity {

    public OBJ_icicle(GamePanel gp) {
        super(gp);
        name = "Icicle scroll";
        down1 = setup("/objects/icicle");
        description = name + "\nFreeze !";
        type = typeMagic;
    }
}
