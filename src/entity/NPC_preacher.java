package entity;

import main.GamePanel;


public class NPC_preacher extends Entity {
    public NPC_preacher(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;
        type = 0;

        getNPCImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0] = "ежжи еси на небеси";
    }

    public void getNPCImage() {
        up1 = setup("/npc/preacher/preacher");
        up2 = setup("/npc/preacher/preacher");
        down1 = setup("/npc/preacher/preacher");
        down2 = setup("/npc/preacher/preacher");
        left1 = setup("/npc/preacher/preacher");
        left2 = setup("/npc/preacher/preacher");
        right1 = setup("/npc/preacher/preacher");
        right2 = setup("/npc/preacher/preacher");
    }

    public void setAction() {

    }

    @Override
    public void speak() {
        super.speak();
    }
}
