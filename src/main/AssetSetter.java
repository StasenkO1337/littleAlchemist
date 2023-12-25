package main;

import entity.NPC_oldMaster;
import entity.NPC_preacher;
import monster.MON_slimeG;
import object.OBJ_chest;
import object.OBJ_door;
import object.OBJ_fan;
import object.OBJ_key;


public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        gp.obj[0] = new OBJ_door(gp);
        gp.obj[0].worldX = gp.tileSize * 50;
        gp.obj[0].worldY = gp.tileSize * 48;

        gp.obj[1] = new OBJ_key(gp);
        gp.obj[1].worldX = gp.tileSize * 49;
        gp.obj[1].worldY = gp.tileSize * 49;

        gp.obj[2] = new OBJ_fan(gp);
        gp.obj[2].worldX = gp.tileSize * 49;
        gp.obj[2].worldY = gp.tileSize * 50;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_oldMaster(gp);
        gp.npc[0].worldX = gp.tileSize * 50;
        gp.npc[0].worldY = gp.tileSize * 45;

        gp.npc[1] = new NPC_preacher(gp);
        gp.npc[1].worldX = gp.tileSize * 41;
        gp.npc[1].worldY = gp.tileSize * 52;
    }

    public void setMonster(){
        gp.monster[0] = new MON_slimeG(gp);
        gp.monster[0].worldX = gp.tileSize * 62;
        gp.monster[0].worldY = gp.tileSize * 53;

        gp.monster[1] = new MON_slimeG(gp);
        gp.monster[1].worldX = gp.tileSize * 62;
        gp.monster[1].worldY = gp.tileSize * 54;
    }
}
