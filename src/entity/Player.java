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

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void getPlayerImage() {
        up1 = setup("/spirit/up1");
        up2 = setup("/spirit/up2");
        down1 = setup("/spirit/down1");
        down2 = setup("/spirit/down2");
        left1 = setup("/spirit/left1");
        left2 = setup("/spirit/left2");
        right1 = setup("/spirit/right1");
        right2 = setup("/spirit/right2");

    }
    public void getPlayerAttackImage() {
        if(currentWeapon.name == "Air Realm") {
            AU1 = setup("/spirit/AR/AU1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/spirit/AR/AU2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/spirit/AR/AD1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/spirit/AR/AD2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/spirit/AR/AL1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/spirit/AR/AL2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/spirit/AR/AR1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/spirit/AR/AR2", 2 * gp.tileSize, gp.tileSize);
        } else if (currentWeapon.name == "Fire realm") {
            AU1 = setup("/spirit/FR/AU1", gp.tileSize, gp.tileSize * 2);
            AU2 = setup("/spirit/FR/AU2", gp.tileSize, gp.tileSize * 2);
            AD1 = setup("/spirit/FR/AD1", gp.tileSize, gp.tileSize * 2);
            AD2 = setup("/spirit/FR/AD2", gp.tileSize, gp.tileSize * 2);
            AL1 = setup("/spirit/FR/AL1", 2 * gp.tileSize, gp.tileSize);
            AL2 = setup("/spirit/FR/AL2", 2 * gp.tileSize, gp.tileSize);
            AR1 = setup("/spirit/FR/AR1", 2 * gp.tileSize, gp.tileSize);
            AR2 = setup("/spirit/FR/AR2", 2 * gp.tileSize, gp.tileSize);
        }
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
        projectile = new OBJ_fireball(gp);

        attack = getAttack();
        defense = getDefence();

        attackCanceled = false;

        coin = 0;

    }
    public void setDefaultPosition(){
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        direction = "down";
    }
    public void restoreManaAndLife(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }
    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentMagic);
        inventory.add(new OBJ_fireRealm(gp));
        inventory.add(new OBJ_flaskR(gp));
    }
    private int getDefence() {
        return defense = dexterity + 1;
    }
    private int getAttack() {
        return attack = strength * currentWeapon.attackValue;
    }
    public void update() {
        if (attacking == true){
            attack();
        } else if(keyH.upPressed == true||keyH.downPressed == true||keyH.leftPressed == true||keyH.rightPressed == true ||
            keyH.fPressed == true) {
            if (keyH.upPressed == true) direction = "up";
            if (keyH.downPressed == true) direction = "down";
            if (keyH.rightPressed == true) direction = "right";
            if (keyH.leftPressed == true) direction = "left";
            //чек коллизии
            collisionOn = false;
            gp.collisionChecker.checkTile(this);


            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.collisionChecker.checkEntity(this, gp.iTile);


            gp.eventHandler.checkEvent();

            if (collisionOn == false && keyH.fPressed == false) {
                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down":  worldY += speed;break;
                    case "left":  worldX -= speed;break;
                    case "right":  worldX += speed;break;
                }
            }
            gp.keyH.fPressed = false;
        }

        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter == 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }


        //магия
        if (gp.keyH.magicKeyPressed == true && projectile.alive == false
                && shotAvailableCounter == 30 && projectile.haveResource(this) == true){

            projectile.set(worldX,worldY,direction,true,this);
            projectile.subtractResource(this);


            for(int i = 0; i < gp.projectile[1].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
            //звук

        }
        if (gp.keyH.magicKeyPressed == false && projectile.alive == true){
            gp.collisionChecker.checkTile(projectile);
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }

        if (life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(1);
        }
    }
    private void attack() {
        //gp.playSE(1);


        spriteCounter++;

        if (spriteCounter <= 10){
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <=30) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up": worldY -= attackArea.height;break;
                case "down": worldY += attackArea.height;break;
                case "left": worldX -= attackArea.width;break;
                case "right": worldX += attackArea.width;break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.collisionChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex,attack,currentWeapon.knockBackPower);

            int iTileIndex = gp.collisionChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.collisionChecker.checkEntity(this,gp.projectile);
            damageProjectile(projectileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeight;
            solidArea.width = solidAreaWidth;
        }
        if (spriteCounter > 30){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void knockBack(Entity entity,int knockBackPower){
        entity.direction = direction;
        entity.speed += knockBackPower ;
        entity.knockBack = true;
    }
    public void damageMonster(int monsterIndex,int attack, int knockBackPower) {
        if (monsterIndex != 999){
            if (gp.monster[gp.currentMap][monsterIndex].invincible == false){

                knockBack(gp.monster[gp.currentMap][monsterIndex],knockBackPower);

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
                if ( damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
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
    public void interactNPC(int npcIndex){
        if(gp.keyH.fPressed == true){
            if (npcIndex != 999) {
                //зациклить
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][npcIndex].speak();
            }
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
                break;
        }

        if ( invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        g2.drawImage(image, tmpScreenX, tmpScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == typeWeapon ){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == typeMagic) {
                currentMagic = selectedItem;
                getPlayerAttackImage();
                //смена магии
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
}
