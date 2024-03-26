package object;
import entity.Entity;
import main.GamePanel;

public class OBJ_axe extends Entity {
    public OBJ_axe(GamePanel gp) {
        super(gp);
        name = "Axe";
        down1 = setup("/objects/axe");
        description = name + "\nРуби нахуй";
        attackValue = 3;
        knockBackPower = 3;
        type = typeAxe;

        attackArea.width = 30;
        attackArea.height = 30;
        motion1Duration = 20;
        motion2Duration = 40;
    }
}
