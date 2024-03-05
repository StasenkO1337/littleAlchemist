package main;

import entity.NPC_oldMaster;
import entity.NPC_preacher;
import entity.NPC_seller;
import monster.MON_evilEye;
import monster.MON_slimeG;
import object.*;
import tile_interactive.IT_tree;


public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        int i = 0;
        int mapNum = 1;

        gp.obj[mapNum][i] = new OBJ_door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 31;
        i++;

        gp.obj[1][i] = new OBJ_key(gp);
        gp.obj[1][i].worldX = gp.tileSize * 22;
        gp.obj[1][i].worldY = gp.tileSize * 32;
        i++;

        gp.obj[0][i] = new OBJ_flaskHeal(gp);
        gp.obj[0][i].worldX = gp.tileSize * 22;
        gp.obj[0][i].worldY = gp.tileSize * 32;
        i++;
        gp.obj[0][i] = new OBJ_flaskHeal(gp);
        gp.obj[0][i].worldX = gp.tileSize * 22;
        gp.obj[0][i].worldY = gp.tileSize * 33;
        i++;
        gp.obj[0][i] = new OBJ_flaskR(gp);
        gp.obj[0][i].worldX = gp.tileSize * 22;
        gp.obj[0][i].worldY = gp.tileSize * 34;
        i++;
        gp.obj[0][i] = new OBJ_flaskR(gp);
        gp.obj[0][i].worldX = gp.tileSize * 22;
        gp.obj[0][i].worldY = gp.tileSize * 35;
        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_preacher(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 37;
        gp.npc[mapNum][i].worldY = gp.tileSize * 15;
        i++;

        gp.npc[mapNum][i] = new NPC_seller(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 11;
        gp.npc[mapNum][i].worldY = gp.tileSize * 17;
        i++;

        gp.npc[mapNum][i] = new NPC_oldMaster(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 37;
        gp.npc[mapNum][i].worldY = gp.tileSize * 20;
        i++;
    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;

        gp.monster[1][i] = new MON_evilEye(gp);
        gp.monster[1][i].worldX = gp.tileSize * 22;
        gp.monster[1][i].worldY = gp.tileSize * 20;
        i++;
        gp.monster[1][i] = new MON_evilEye(gp);
        gp.monster[1][i].worldX = gp.tileSize * 22;
        gp.monster[1][i].worldY = gp.tileSize * 23;
        i++;
        gp.monster[1][i] = new MON_evilEye(gp);
        gp.monster[1][i].worldX = gp.tileSize * 22;
        gp.monster[1][i].worldY = gp.tileSize * 24;
        i++;
        gp.monster[1][i] = new MON_slimeG(gp);
        gp.monster[1][i].worldX = gp.tileSize * 22;
        gp.monster[1][i].worldY = gp.tileSize * 25;
        i++;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;

        //gp.iTile[mapNum][i] = new IT_tree(gp,30,35);
        i++;

    }
}
