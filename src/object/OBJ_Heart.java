package object;

import main.GamePanel;


public class OBJ_Heart extends entity.Entity{

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("/objects/heart/heartF");
        image2 = setup("/objects/heart/heartH");
        image3 = setup("/objects/heart/heartB");
    }
}
