package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage AD1, AD2, AU1, AU2, AL1, AL2, AR1, AR2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];

    //состояния
    public int worldX, worldY;
    public int speed;
    public String direction = "down";
    int dialogueIndex = 0;
    public boolean invincible = false;
    public boolean collisionOn = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;

    //счетчики
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;

    public int type;
    public int spriteNum = 1;
    public int maxLife;
    public int life;
    public String name;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

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

    public void update() {
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkPlayer(this);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (gp.player.invincible == false) {
                gp.player.life--;
                gp.player.invincible = true;
            }
        }

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
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        BufferedImage image = null;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    break;
                case "down":
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    break;
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    break;
            }
            if (invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }

            if (dying == true) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }

    private void dyingAnimation(Graphics2D g2) {

            dyingCounter++;
            if (dyingCounter > 0 && dyingCounter <=20)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            if (dyingCounter > 20 && dyingCounter <=  40)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            if (dyingCounter >40 && dyingCounter <= 60)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            if (dyingCounter > 60 && dyingCounter <=  80)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            if (dyingCounter > 80 && dyingCounter <=  100)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            if (dyingCounter > 100 && dyingCounter <=  120)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            if (dyingCounter > 120) {
                dying = false;
                alive = false;
            }
        dyingCounter++;
    }
}
