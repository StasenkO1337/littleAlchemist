package main;

import entity.NPC_oldMaster;
import entity.NPC_preacher;
import entity.NPC_rock;
import entity.NPC_seller;
import monster.*;
import object.*;
import tile_interactive.IT_plate;
import tile_interactive.IT_tree;
import tile_interactive.IT_wall;

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

        gp.obj[3][i] = new OBJ_door_iron(gp);
        gp.obj[3][i].worldX = gp.tileSize * 14;
        gp.obj[3][i].worldY = gp.tileSize * 23;
        i++;

        gp.obj[0][i] = new OBJ_bed(gp);
        gp.obj[0][i].worldX = gp.tileSize * 22;
        gp.obj[0][i].worldY = gp.tileSize * 31;
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

        gp.obj[0][i] = new OBJ_lantern(gp);
        gp.obj[0][i].worldX = gp.tileSize * 22;
        gp.obj[0][i].worldY = gp.tileSize * 36;
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

        gp.npc[3][i] = new NPC_rock(gp);
        gp.npc[3][i].worldX = gp.tileSize * 32;
        gp.npc[3][i].worldY = gp.tileSize * 29;
        i++;

        gp.npc[3][i] = new NPC_rock(gp);
        gp.npc[3][i].worldX = gp.tileSize * 31;
        gp.npc[3][i].worldY = gp.tileSize * 40;
        i++;
        gp.npc[3][i] = new NPC_rock(gp);
        gp.npc[3][i].worldX = gp.tileSize * 11;
        gp.npc[3][i].worldY = gp.tileSize * 26;
        i++;

    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;

        gp.monster[2][i] = new MON_evilEye(gp);
        gp.monster[2][i].worldX = gp.tileSize * 22;
        gp.monster[2][i].worldY = gp.tileSize * 20;
        i++;
        gp.monster[2][i] = new MON_evilEye(gp);
        gp.monster[2][i].worldX = gp.tileSize * 22;
        gp.monster[2][i].worldY = gp.tileSize * 23;
        i++;
        gp.monster[1][i] = new MON_evilEye(gp);
        gp.monster[1][i].worldX = gp.tileSize * 22;
        gp.monster[1][i].worldY = gp.tileSize * 24;
        i++;
        gp.monster[1][i] = new MON_orc(gp);
        gp.monster[1][i].worldX = gp.tileSize * 22;
        gp.monster[1][i].worldY = gp.tileSize * 25;
        i++;

        gp.monster[3][i] = new MON_bat(gp);
        gp.monster[3][i].worldX = gp.tileSize * 32;
        gp.monster[3][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[3][i] = new MON_bat(gp);
        gp.monster[3][i].worldX = gp.tileSize * 14;
        gp.monster[3][i].worldY = gp.tileSize * 26;
        i++;

        gp.monster[4][i] = new MON_skeletron(gp);
        gp.monster[4][i].worldX = gp.tileSize * 25;
        gp.monster[4][i].worldY = gp.tileSize * 25;
        i++;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;

        gp.iTile[mapNum][i] = new IT_tree(gp,23,36);i++;

        mapNum = 3;

        gp.iTile[mapNum][i] = new IT_wall(gp,8,26);i++;
        gp.iTile[mapNum][i] = new IT_wall(gp,20,26);i++;
        gp.iTile[mapNum][i] = new IT_wall(gp,20,27);i++;
        gp.iTile[mapNum][i] = new IT_wall(gp,21,26);i++;
        gp.iTile[mapNum][i] = new IT_wall(gp,19,27);i++;

        gp.iTile[mapNum][i] = new IT_plate(gp,5,26);i++;
        gp.iTile[mapNum][i] = new IT_plate(gp,32,27);i++;
        gp.iTile[mapNum][i] = new IT_plate(gp,25,41);i++;
    }
}
