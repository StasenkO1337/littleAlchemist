package data;

import java.io.Serializable;
import java.util.ArrayList;

public class dataStorage implements Serializable {
    //игрок
    int lvl;
    int maxLife ;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    //инвентарь
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();

    int currentWeaponSlot;
    int currentMagicSlot;

    String mapObjectNames[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    boolean mapObjectOpened[][];

}
