package object;

import main.GamePanel;

public class OBJ_flaskR extends entity.Entity {

    public OBJ_flaskR(GamePanel gp) {
        super(gp);
        name = "FlaskR";
        down1 = setup("/objects/flaskR");
    }
}