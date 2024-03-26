package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled;
    public boolean lightUpdated = false;

    public boolean haveKey1 = false;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 52;
        attackArea.height = 52;
        guarding = false;

        setDefaultValues();
        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
    }
    public void getImage() {
        up1 = setup("/player/Walking sprites/up1");
        up2 = setup("/player/Walking sprites/up2");
        down1 = setup("/player/Walking sprites/down1");
        down2 = setup("/player/Walking sprites/down2");
        left1 = setup("/player/Walking sprites/left1");
        left2 = setup("/player/Walking sprites/left2");
        right1 = setup("/player/Walking sprites/right1");
        right2 = setup("/player/Walking sprites/right2");

    }
    public void getAttackImage() {
        if(currentWeapon.name == "Air Realm") {
            AU1 = setup("/player/Attacking sprites/AR/AU1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/player/Attacking sprites/AR/AU2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/player/Attacking sprites/AR/AD1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/player/Attacking sprites/AR/AD2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/player/Attacking sprites/AR/AL1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/player/Attacking sprites/AR/AL2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/player/Attacking sprites/AR/AR1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/player/Attacking sprites/AR/AR2", 2 * gp.tileSize, gp.tileSize);
        } else if (currentWeapon.name == "Fire realm") {
            AU1 = setup("/player/Attacking sprites/FR/AU1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/player/Attacking sprites/FR/AU2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/player/Attacking sprites/FR/ad1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/player/Attacking sprites/FR/AD2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/player/Attacking sprites/FR/AL1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/player/Attacking sprites/FR/AL2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/player/Attacking sprites/FR/AR1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/player/Attacking sprites/FR/AR2", 2 * gp.tileSize, gp.tileSize);
        } else if (currentWeapon.name == "Axe") {
            AU1 = setup("/player/Attacking sprites/Axe/au1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/player/Attacking sprites/Axe/au2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/player/Attacking sprites/Axe/ad1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/player/Attacking sprites/Axe/ad2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/player/Attacking sprites/Axe/al1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/player/Attacking sprites/Axe/al2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/player/Attacking sprites/Axe/ar1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/player/Attacking sprites/Axe/ar2", 2 * gp.tileSize, gp.tileSize);
        }
        else if (currentWeapon.name == "Sword") {
            AU1 = setup("/player/Attacking sprites/Sword/su1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/player/Attacking sprites/Sword/su2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/player/Attacking sprites/Sword/sd1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/player/Attacking sprites/Sword/sd2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/player/Attacking sprites/Sword/sl1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/player/Attacking sprites/Sword/sl2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/player/Attacking sprites/Sword/sr1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/player/Attacking sprites/Sword/sr2", 2 * gp.tileSize, gp.tileSize);
        }
        else if (currentWeapon.name == "Pickaxe") {
            AU1 = setup("/player/Attacking sprites/Pick/pu1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/player/Attacking sprites/Pick/pu2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/player/Attacking sprites/Pick/pd1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/player/Attacking sprites/Pick/pd2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/player/Attacking sprites/Pick/pl1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/player/Attacking sprites/Pick/pl2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/player/Attacking sprites/Pick/pr1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/player/Attacking sprites/Pick/pr2", 2 * gp.tileSize, gp.tileSize);
        }
    }
    public void getGuardImage(){
        gu = setup("/player/Guarding sprites/gu");
        gd = setup("/player/Guarding sprites/gd");
        gl = setup("/player/Guarding sprites/gl");
        gr = setup("/player/Guarding sprites/gr");

    }
    public void setDefaultValues() {
        direction = "down";
        worldX = gp.tileSize * 20;
        worldY = gp.tileSize * 20;

        inventory = new ArrayList<>();
        maxInventorySize = 20;
        lvl  = 1;
        exp = 0;
        nextLvlExp = 10;

        maxMana = 6;
        mana = maxMana;
        maxLife = 6;
        defaultSpeed = 5;
        speed = defaultSpeed;
        strength = 1;
        dexterity = 0;
        life = maxLife;

        currentWeapon = new OBJ_airRealm(gp);
        currentMagic = new OBJ_fireballScroll(gp);
        currentLantern = null;
        projectile = new OBJ_fireball(gp);

        attack = getAttack();
        defense = getDefence();

        attackCanceled = false;

        coin = 0;

    }
    public void setDefaultPosition(){
        worldX = gp.tileSize * 20;
        worldY = gp.tileSize * 20;
        direction = "down";
        gp.currentMap = 0;
    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = 5;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }
    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentMagic);
        inventory.add(new OBJ_fireRealm(gp));
        inventory.add(new OBJ_pick(gp));
        inventory.add(new OBJ_sword(gp));
        inventory.add(new OBJ_axe(gp));
        inventory.add(new OBJ_lantern(gp));

    }
    private int getDefence() {
        return defense = dexterity + 1;
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1Duration = currentWeapon.motion1Duration;
        motion2Duration = currentWeapon.motion2Duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public void update() {
        if (knockBack == true) {
            collisionOn = false;
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);
            if (collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (collisionOn == false) {
                switch (knockBackDirection) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else if (attacking == true && keyH.qPressed == false) {
            guarding = false;
            attack();
        } else if (keyH.qPressed == true && attacking == false) {
            attacking = false;
            guarding = true;
            guardCounter++;
        }
        else if (keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true || keyH.fPressed == true) {
            if (keyH.upPressed == true) direction = "up";
            if (keyH.downPressed == true) direction = "down";
            if (keyH.rightPressed == true) direction = "right";
            if (keyH.leftPressed == true) direction = "left";
            //чек коллизии
            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.cChecker.checkEntity(this, gp.iTile);
            gp.eHandler.checkEvent();

            if (collisionOn == false && keyH.fPressed == false) {
                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
            gp.keyH.fPressed = false;
            if (guarding == false) {
                spriteCounter++;
                if (spriteCounter > 15) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
            guarding = false;
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter == 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        guardCounter = 0;
        //магия
        if (gp.keyH.magicKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            //звук
        }
        if (gp.keyH.magicKeyPressed == false && projectile.alive == true) {
            gp.cChecker.checkTile(projectile);
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(1);
        }

    }
    public void damageMonster(int monsterIndex,Entity attacker,int attack, int knockBackPower) {
        if (monsterIndex != 999){
            if (gp.monster[gp.currentMap][monsterIndex].invincible == false){

                setKnockBack(gp.monster[gp.currentMap][monsterIndex],attacker,knockBackPower);

                if (gp.monster[gp.currentMap][monsterIndex].offBalance == true){
                    attack *= 3;
                }

                int damage = attack - gp.monster[gp.currentMap][monsterIndex].defense;
                if ( damage < 0){
                    damage = 0;
                }

                gp.monster[gp.currentMap][monsterIndex].life -= attack;
                gp.monster[gp.currentMap][monsterIndex].invincible = true;
                gp.monster[gp.currentMap][monsterIndex].damageReaction();

                if(gp.monster[gp.currentMap][monsterIndex].life <= 0){
                    gp.monster[gp.currentMap][monsterIndex].dying = true;
                    exp += gp.monster[gp.currentMap][monsterIndex].exp;
                    checkLvlUp();
                }

            }
        }

    }
    public void damageInteractiveTile(int iTileIndex){
        if (    iTileIndex != 999 && gp.iTile[gp.currentMap][iTileIndex].destructible == true &&
                gp.iTile[gp.currentMap][iTileIndex].isCorrectItem(this) == true &&
               gp.iTile[gp.currentMap][iTileIndex].invincible == false ){

            //gp.iTile[i].playSE();
            gp.iTile[gp.currentMap][iTileIndex].life--;
            gp.iTile[gp.currentMap][iTileIndex].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][iTileIndex],gp.iTile[gp.currentMap][iTileIndex]);

            if(gp.iTile[gp.currentMap][iTileIndex].life == 0) {
                gp.iTile[gp.currentMap][iTileIndex] = gp.iTile[gp.currentMap][iTileIndex].getDestroyedForm();
            }
        }
    }
    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }
    private void checkLvlUp() {
        if(exp >= nextLvlExp){
            lvl++;
            nextLvlExp = nextLvlExp + 15;

            /// что то прикольное нужно сделать
            strength++;
            dexterity++;
            maxLife +=2;
            maxMana +=1;
            life = maxLife;
            mana = maxMana;

            attack = getAttack();
            defense = getDefence();
            //звуковой эвент
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Поднялся подкачался епты";
        }
    }
    private void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if ( damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }
    public void pickUpObject(int index) {
        if (index != 999) {
            //тлько поднятие
            if (gp.obj[gp.currentMap][index].type == typePickUpOnly){
                gp.obj[gp.currentMap][index].use(this);
                gp.obj[gp.currentMap][index] = null;
            }
            //инвентарь
            else if (gp.obj[gp.currentMap][index].type == typeObstacle){
                if (keyH.fPressed == true){
                    gp.obj[gp.currentMap][index].interact();
                }
            }
            else {
                String text;
                if (canObtainItem(gp.obj[gp.currentMap][index]) == true) {
                    //звук поднятия
                    text = "Pick up " + gp.obj[gp.currentMap][index].name;
                } else {
                    text = "Inventory full";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][index] = null;
            }
        }
    }
    public void interactNPC(int npcIndex) {
        if (npcIndex != 999) {
            if (gp.keyH.fPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][npcIndex].speak();

            }
            gp.npc[gp.currentMap][npcIndex].move(direction);
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int tmpScreenX = screenX;
        int tmpScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false){
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                if (attacking == true){
                    tmpScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) image = AU1;
                    if (spriteNum == 2) image = AU2;
                }
                if ( guarding == true){
                    image = gu;
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                if (attacking == true){
                    if (spriteNum == 1) image = AD1;
                    if (spriteNum == 2) image = AD2;
                }
                if ( guarding == true){
                    image = gd;
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
                if (attacking == true){
                    if (spriteNum == 1) image = AR1;
                    if (spriteNum == 2) image = AR2;
                }
                if (guarding){
                    image = gr;
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                if (attacking == true){
                    tmpScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) image = AL1;
                    if (spriteNum == 2) image = AL2;
                }
                if ( guarding == true){
                    image = gl;
                }
                break;
        }

        if ( transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        g2.drawImage(image, tmpScreenX, tmpScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == typeWeapon || selectedItem.type == typeAxe|| selectedItem.type == typePick){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }


            if (selectedItem.type == typeMagic) {
                currentMagic = selectedItem;
                getAttackImage();
                //смена магии
            }
            if (selectedItem.type == typeLight) {
                if (currentLantern == selectedItem){
                    currentLantern = null;
                } else {
                    currentLantern = selectedItem;
                }
                lightUpdated = true;
            }
            if (selectedItem.type == typeConsumable){

                if (selectedItem.amount > 1) {
                    selectedItem.use(this);
                    selectedItem.amount--;
                }
                else {
                    inventory.remove(itemIndex);
                }
            }

        }
    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        if(item.stackable == true){
            int index = searchItemInInventory(item.name);
            if (index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {
                if (inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else {
            if (inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentMagicSlot(){
        int currentMagicSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i) == currentMagic){
                currentMagicSlot = i;
            }
        }
        return currentMagicSlot;
    }
}
