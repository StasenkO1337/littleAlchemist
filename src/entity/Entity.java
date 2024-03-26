package entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    public GamePanel gp;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage AD1, AD2, AU1, AU2, AL1, AL2, AR1, AR2;
    public BufferedImage gu,gd,gl,gr;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[30];
    public Entity attacker;

    //состояния
    public int worldX, worldY;
    public String direction = "down";
    public String knockBackDirection;
    public int dialogueIndex = 0;
    public boolean invincible = false;
    public boolean collisionOn = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean stackable = false;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public boolean opened = false;
    public boolean inRage = false;

    //счетчики
    public int shotAvailableCounter = 0;
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int guardCounter = 0;


    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;
    int offBalanceCounter = 0;

    public int spriteNum = 1;

    //атрибуты
    public String name;
    public int speed;
    public int defaultSpeed;
    public int lvl;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLvlExp;
    public int coin;
    public int maxMana;
    public int mana;
    public int maxLife;
    public int life;
    public int ammo;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int cost = 1;
    public int knockBackPower = 0;
    public int maxInventorySize = 20;
    public int amount = 1;
    public int lightRadius;
    public int motion1Duration;
    public int motion2Duration;
    public boolean boss;

    public Projectile projectile;

    public Entity linkEntity;

    public Entity currentWeapon;
    public Entity currentMagic;
    public Entity currentLantern;
    public ArrayList<Entity> inventory = new ArrayList<>();
    //типы
    public int type;
    public final int typePlayer = 0;
    public final int typeNPC = 1;
    public final int typeMonster = 2;
    public final int typeWeapon = 3;
    public final int typeAxe = 31;
    public final int typePick = 32;
    public final int typeMagic = 4;
    public final int typeConsumable = 5;
    public final int typePickUpOnly = 6;
    public final int typeObstacle = 7;
    public final int typeLight = 8;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void setAction() {
    }
    public void checkDrop() {
    }
    public void dropItem(Entity dropItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = dropItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    public void attack() {
        spriteCounter++;
        if (spriteCounter <= motion1Duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1Duration && spriteCounter <= motion2Duration) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            if (type == typeMonster) {
                if (gp.cChecker.checkPlayer(this) == true) {
                    damagePlayer(attack);
                }
            } else {
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);
                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.height = solidAreaHeight;
            solidArea.width = solidAreaWidth;
        }
        if (spriteCounter > motion2Duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void damageReaction() {
    }
    public void move(String d){

    }

    public String getOppositeDirection(String direction){
        String oppositeDirection = "";
        switch (direction){
            case "up": oppositeDirection = "down";break;
            case "down": oppositeDirection = "up";break;
            case "left": oppositeDirection = "right";break;
            case "right": oppositeDirection = "left";break;
        }
        return  oppositeDirection;
    }
    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            int damage = attack - gp.player.defense;
            //чек на направление щита
            String canGuardDirection = getOppositeDirection(direction);

            if (gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)){
                //парирование
                if (gp.player.guardCounter < 30){
                    damage = 0;
                    //звук
                    setKnockBack(this, gp.player, 2);
                    offBalance = true;
                    spriteCounter -= 120;
                } else {
                    //звук
                    damage /= 3;
                }

            } else {
                //звук
                if (damage < 1) {
                    damage = 1;
                }
            }
            if (damage != 0){
                gp.player.transparent = true;
                setKnockBack(gp.player,this, this.knockBackPower);
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        } else {
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
            switch (gp.player.direction) {
                case "up":
                    direction = "down";
                    break;
                case "down":
                    direction = "up";
                    break;
                case "left":
                    direction = "right";
                    break;
                case "right":
                    direction = "left";
                    break;
            }
        }
    }
    public void interact() {
    }
    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (this.type == typeMonster && contactPlayer == true) {
            damagePlayer(attack);
        } else if (this.type == typeNPC && contactPlayer == true) {
            collisionOn = true;
        }
    }
    public void update() {
        if (knockBack == true) {
            checkCollision();
            if (collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (collisionOn == false) {
                switch (knockBackDirection) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else if (attacking == true) {
            attack();
        } else {
            setAction();
            checkCollision();
            if (collisionOn == false) {
                switch (direction) {
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
            spriteCounter++;
            if (spriteCounter > 24) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if (offBalance == true){
            offBalanceCounter ++;
            if (offBalanceCounter > 120){
                offBalance = false;
                offBalanceCounter = 0;
            }
        }

    }
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public boolean inCamera(){
        boolean inCamera = false;
        if (    worldX + gp.tileSize *5 > gp.player.worldX - gp.player.screenX &&
                worldY + gp.tileSize*5 > gp.player.worldY - gp.player.screenY &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            inCamera  = true;
        }
        return inCamera;
    }
    public int getScreenX(){
        return worldX - gp.player.worldX + gp.player.screenX;
    }
    public int getScreenY(){
        return worldY - gp.player.worldY + gp.player.screenY;
    }
    public void draw(Graphics2D g2) {

        int tmpScreenX = getScreenX();
        int tmpScreenY =  getScreenY();
        BufferedImage image = null;
        if ( inCamera() == true) {
            switch (direction) {
                case "up":
                    if (attacking == false) {
                        if (spriteNum == 1) image = up1;
                        if (spriteNum == 2) image = up2;
                        if (spriteNum == 3) image = up3;
                    }
                    if (attacking == true) {
                        tmpScreenY =  getScreenY() - up1.getHeight();
                        if (spriteNum == 1) image = AU1;
                        if (spriteNum == 2) image = AU2;
                    }
                    break;
                case "down":
                    if (attacking == false) {
                        if (spriteNum == 1) image = down1;
                        if (spriteNum == 2) image = down2;
                        if (spriteNum == 3) image = down3;
                    }
                    if (attacking == true) {
                        if (spriteNum == 1) image = AD1;
                        if (spriteNum == 2) image = AD2;
                    }
                    break;
                case "right":
                    if (attacking == false) {
                        if (spriteNum == 1) image = right1;
                        if (spriteNum == 2) image = right2;
                        if (spriteNum == 3) image = right3;
                    }
                    if (attacking == true) {
                        if (spriteNum == 1) image = AR1;
                        if (spriteNum == 2) image = AR2;
                    }
                    break;
                case "left":
                    if (attacking == false) {
                        if (spriteNum == 1) image = left1;
                        if (spriteNum == 2) image = left2;
                        if (spriteNum == 3) image = left3;
                    }
                    if (attacking == true) {
                        tmpScreenX =  getScreenX() - left1.getWidth();
                        if (spriteNum == 1) image = AL1;
                        if (spriteNum == 2) image = AL2;
                    }
                    break;
            }

        }


        if (invincible == true) {
            hpBarOn = true;
            hpBarCounter = 0;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        if (dying == true) {
            dyingAnimation(g2);
        }
        g2.drawImage(image, tmpScreenX, tmpScreenY,  null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        if (dyingCounter > 0 && dyingCounter <= 20)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 20 && dyingCounter <= 40)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 40 && dyingCounter <= 60)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 60 && dyingCounter <= 80)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 80 && dyingCounter <= 100)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 100 && dyingCounter <= 120)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 120) {
            alive = false;
        }
        dyingCounter++;
    }
    public void use(Entity entity) {
    }
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if (gp.pFinder.search() == true) {
            //получили следующий х и у
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            //текущие х и у
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }
        }
    }
    public void moveTowardPlayer(int interval){
        actionLockCounter++;

        if (actionLockCounter >= interval){
            if (getXDistance(gp.player) > getYDistance(gp.player)){
                if (gp.player.getCenterX() < getCenterX()){
                    direction = "left";
                } else {
                    direction = "right";
                }
            }
            else if (getXDistance(gp.player) < getYDistance(gp.player)){
                if (gp.player.getCenterY() < getCenterY()){
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }
    public int getCenterX(){
        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }
    public int getCenterY(){
        int centerY = worldY + up1.getHeight()/2;
        return centerY;
    }
    public int getXDistance(Entity target){
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }
    public int getYDistance(Entity target){
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }
    public int getTileDistance(Entity target){
        int tileDistance = (getXDistance(target) + getYDistance(target))/ gp.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x)/gp.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y)/gp.tileSize;
        return goalRow;
    }
    public void checkStopChasingOrNot(Entity target,int distance){
        if (getTileDistance(target) > distance){
            onPath = false;
        }
    }
    public void checkStartChasingOrNot(Entity target,int distance){
        if (getTileDistance(target) < distance){
            onPath = true;
        }
    }
    public void checkShootOrNot(int rate, int shotInterval){

        int i = new Random().nextInt(rate);

        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval){
            projectile.set(worldX,worldY,direction,true,this);
            for (int ii = 0; ii < gp.projectile[1].length ; ii ++){
                if (gp.projectile[gp.currentMap][ii] == null){
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void getRandomDirection(int interval) {
        actionLockCounter++;
        if (actionLockCounter > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {direction = "up";}
            if (i > 25 && i <= 50) {direction = "down";}
            if (i > 50 && i <= 75) {direction = "left";}
            if (i > 75 && i <= 100) {direction = "right";}
            actionLockCounter = 0;
        }
    }
    public void setKnockBack(Entity target,Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower ;
        target.knockBack = true;
    }
    public void checkAttackOrNot(int rate,int straight,int horizontal){
        boolean targetInRange = false;
        int xDistance = getXDistance(gp.player);
        int yDistance = getYDistance(gp.player);

        switch (direction) {
            case "up":
                if (gp.player.getCenterY() < getCenterY() && yDistance < straight && xDistance < horizontal) {
                    targetInRange = true;
                }
                break;
            case "down":
                if (gp.player.getCenterY() > getCenterY() && yDistance < straight && xDistance < horizontal) {
                    targetInRange = true;
                }
                break;
            case "left":
                if (gp.player.getCenterX() < getCenterX() && xDistance < straight && yDistance < horizontal) {
                    targetInRange = true;
                }
                break;
            case "right":
                if (gp.player.getCenterX() > getCenterX() && xDistance < straight && yDistance < horizontal) {
                    targetInRange = true;
                }
                break;
        }
        if (targetInRange == true){
            int i = new Random().nextInt(rate);
            if (i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

}