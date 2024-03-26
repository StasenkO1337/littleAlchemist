package object;
import entity.Entity;
import main.GamePanel;
public class OBJ_sword extends Entity {
    public OBJ_sword(GamePanel gp) {
        super(gp);
        name = "Sword";
        down1 = setup("/objects/sword");
        description = name + "\nРежь нахуй";
        attackValue = 5;
        knockBackPower = 2;
        type = typeWeapon;

        attackArea.width = 30;
        attackArea.height = 30;
        motion1Duration = 5;
        motion2Duration = 25;
    }
}
