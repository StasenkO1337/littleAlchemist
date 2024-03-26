package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public void save() throws Exception {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));

            dataStorage ds = new dataStorage();

            ds.lvl = gp.player.lvl;
            ds.maxLife = gp.player.maxLife;
            ds.maxMana= gp.player.maxMana;
            ds.life = gp.player.life;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLvlExp;
            ds.coin = gp.player.coin;

            for(int i = 0;i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                //ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }

            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentMagicSlot = gp.player.getCurrentMagicSlot();



            oos.writeObject(ds);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
            dataStorage ds = (dataStorage) ois.readObject();
            gp.player.lvl=ds.lvl ;
            gp.player.maxLife =ds.maxLife ;
            gp.player.maxMana = ds.maxMana;
            gp.player.life = ds.life ;
            gp.player.mana=ds.mana ;
            gp.player.strength=ds.strength ;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLvlExp = ds.nextLevelExp;
            gp.player.coin=ds.coin;

            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++){
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
                //gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }

            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentMagic = gp.player.inventory.get(ds.currentMagicSlot);
            gp.player.getAttack();
            gp.player.getAttackImage();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Entity getObject(String itemName){
        Entity obj = null;
        switch (itemName){
            case "Air Realm": obj = new OBJ_airRealm(gp);break;
            case "Fireball scroll" : obj = new OBJ_fireballScroll(gp);break;
            case "Fire realm" : obj = new OBJ_fireRealm(gp);break;
            case  "Heal flask": obj = new OBJ_flaskHeal(gp);break;
            case  "Icicle scroll": obj = new OBJ_icicle(gp);break;
            case  "Key": obj = new OBJ_key(gp);break;
            case  "Lantern": obj = new OBJ_lantern(gp);break;
            case  "Sword": obj = new OBJ_sword(gp);break;
            case  "Pickaxe": obj = new OBJ_pick(gp);break;
        }
        return  obj;
    }
}
