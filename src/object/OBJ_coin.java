package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_coin extends Entity {
    GamePanel gp;

    public OBJ_coin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = typePickUpOnly;
        down1 = setup("/objects/coin");
        value = 1;
    }

    public void use(Entity entity){
        //zvyk
        gp.ui.addMessage("Coin "+value);
        gp.player.coin += value;
    }
}
