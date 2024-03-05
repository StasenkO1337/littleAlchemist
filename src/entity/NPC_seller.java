package entity;
import main.GamePanel;
import object.OBJ_fan;
import object.OBJ_flaskR;
import object.OBJ_key;
import object.OBJ_wisdomHat;

import java.awt.*;
public class NPC_seller extends Entity{
    public NPC_seller(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getNPCImage();
        setDialogue();
        setItems();
    }

    public void setDialogue(){
        dialogues[0] = "у меня есть немного стафа\nхочешь купить?";
    }

    public void getNPCImage() {
        up1 = setup("/npc/seller/down1");
        up2 = setup("/npc/seller/down2");
        down1 = setup("/npc/seller/down1");
        down2 = setup("/npc/seller/down2");
        left1 = setup("/npc/seller/down1");
        left2 = setup("/npc/seller/down2");
        right1 = setup("/npc/seller/down1");
        right2 = setup("/npc/seller/down2");
    }

    public void setAction() {

    }

    @Override
    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }

    public void setItems(){
        inventory.add(new OBJ_flaskR(gp));
        inventory.add(new OBJ_fan(gp));
        inventory.add(new OBJ_key(gp));
        inventory.add(new OBJ_wisdomHat(gp));
        inventory.add(new OBJ_flaskR(gp));

    }
}
