package entity;

import main.GamePanel;


public class NPC_preacher extends Entity {
    public NPC_preacher(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;
        type = typeNPC;

        getNPCImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0] = "ежжи еси на небеси";
        dialogues[1] = "салам";
    }

    public void getNPCImage() {

        down1 = setup("/npc/preacher/preacher");
        down2 = setup("/npc/preacher/preacher");

    }

    public void setAction() {

    }

    @Override
    public void speak() {
        super.speak();
    }
}
