package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
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
        AU1 = setup("/spirit/AU1",gp.tileSize,gp.tileSize*2);
        AU2 = setup("/spirit/AU2",gp.tileSize,gp.tileSize*2);
        AD1 = setup("/spirit/AD1",gp.tileSize,gp.tileSize*2);
        AD2 = setup("/spirit/AD2",gp.tileSize,gp.tileSize*2);
        AL1 = setup("/spirit/AL1",2*gp.tileSize,gp.tileSize);
        AL2 = setup("/spirit/AL2",2*gp.tileSize,gp.tileSize);
        AR1 = setup("/spirit/AR1",2*gp.tileSize,gp.tileSize);
        AR2 = setup("/spirit/AR2",2*gp.tileSize,gp.tileSize);

    }

    public void setDefaultValues() {
        direction = "down";
        worldX = gp.tileSize * 50;
        worldY = gp.tileSize * 55;
        speed = 3;

        maxLife = 6;
        life = maxLife;
    }

    public void update() {
        if (attacking == true){
            attack();
        }
        if(keyH.upPressed == true||keyH.downPressed == true||keyH.leftPressed == true||keyH.rightPressed == true ||
            keyH.ePressed == true) {
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

            gp.eventHandler.checkEvent();

            gp.keyH.ePressed = false;

            if (collisionOn == false && keyH.ePressed == false) {
                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down":  worldY += speed;break;
                    case "left":  worldX -= speed;break;
                    case "right":  worldX += speed;break;
                }
            }
            gp.keyH.ePressed = false;
        }

        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter == 60){
                invincible = false;
                invincibleCounter = 0;
            }
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
    }

    private void attack() {
        spriteCounter++;
        if (spriteCounter <= 5){
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <=30) {
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

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeight;
            solidArea.width = solidAreaWidth;
            damageMonster(monsterIndex);
        }
        if (spriteCounter > 30){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageMonster(int monsterIndex) {
        if (monsterIndex != 999){
            if (gp.monster[monsterIndex].invincible == false){
                gp.monster[monsterIndex].life -= 1;
                gp.monster[monsterIndex].invincible = true;

                if(gp.monster[monsterIndex].life <= 0){
                    gp.monster[monsterIndex].dying = true;
                }

            }
        }

    }

    private void contactMonster(int i) {
        if (i != 999){
            if (invincible == false)
            life --;
            invincible = true;
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = gp.obj[index].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("Ты заполучил ключ");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("Дверь открыта");
                    } else {
                        gp.ui.showMessage("Нужен ключ");
                    }
                    break;
                case "Fan":
                    gp.playSE(2);
                    speed += 1;
                    gp.obj[index] = null;
                    gp.ui.showMessage("С вентилятором быстрее!");
                    break;
                case "Chest":
                    //gp.GameFinished = true;
                    gp.playSE(4);
                    break;
            }

        }
    }

    public void interactNPC(int npcIndex){
        if (npcIndex != 999) {
            if(gp.keyH.ePressed == true){
            gp.gameState = gp.dialogueState;
            gp.npc[npcIndex].speak();
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

}
