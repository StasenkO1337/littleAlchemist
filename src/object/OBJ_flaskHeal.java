package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_flaskHeal extends entity.Entity {


    public OBJ_flaskHeal(GamePanel gp) {
        super(gp);
        name = "Heal flask";
        down1 = setup("/objects/potion/potionHeal");
        description = name + "\nheal 3 point";
        type = typeConsumable;
        value = 3;
        cost = 2;
        stackable = true;
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Heal";
        entity.life += value;

        if (gp.player.life > gp.player.maxLife){
            gp.player.life =gp.player.maxLife;
        }
        //мб звук
    }
}
