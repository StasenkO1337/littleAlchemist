package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_lantern extends Entity {
    public OBJ_lantern(GamePanel gp) {
        super(gp);
        type = typeLight;
        name = "Lantern";
        down1 = setup("/objects/lantern");
        description =name +  "\nНемного увеличивает радиус\nобзора ночью";
        cost = 100;
        lightRadius = 350;
    }
}
