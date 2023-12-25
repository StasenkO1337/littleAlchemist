package main;

import entity.Entity;
import object.OBJ_Heart;
//import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font fontD;

    BufferedImage heartF,heartH,heartB;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";
    public int commandNum = 0;


    public UI(GamePanel gp) throws IOException, FontFormatException {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x16y32pxGridGazer.ttf");
            fontD = Font.createFont(Font.TRUETYPE_FONT, is);
        }
        catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        Entity heart = new OBJ_Heart(gp);
        heartF = heart.image;
        heartH = heart.image2;
        heartB = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(fontD);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        g2.setColor(Color.cyan);

        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }

        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
        }

        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
            drawPlayerLife();
        }
    }

    private void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while ( i < gp.player.maxLife / 2){
            g2.drawImage(heartB,x,y,null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        while ( i < gp.player.life){
            g2.drawImage(heartH,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heartF,x,y,null);
            }
            x += gp.tileSize;
            i++;
        }
    }

    private void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,90));
        String text = "Little Alchemist";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(new Color(30, 190, 30, 192));
        g2.drawString(text,x + 3,y + 3);

        g2.setColor(new Color(120,210,120));
        g2.drawString(text,x,y);

        g2.drawImage(gp.player.down1,60, 60 ,gp.tileSize ,gp.tileSize ,null);

        //меню
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,56));
        g2.setColor(new Color(250,250,250));

        String newG = "Новая игра";
        x = getXForCenteredText(newG);
        y = gp.tileSize * 6;
        g2.drawString(newG,x,y);
        if (commandNum == 0){
            g2.drawString(">",x - gp.tileSize,y);
        }


        String loadG = "Загрузить";
        x = getXForCenteredText(loadG);
        y += gp.tileSize + 10;
        g2.drawString(loadG,x,y);
        if (commandNum == 1){
            g2.drawString(">",x - gp.tileSize,y);
        }

        String setting = "Настройки";
        x = getXForCenteredText(setting);
        y += gp.tileSize + 10;
        g2.drawString(setting,x,y);
        if (commandNum == 2){
            g2.drawString(">",x - gp.tileSize,y);
        }


        String exit = "Выйти из игры";
        x = getXForCenteredText(exit);
        y += gp.tileSize + 10;
        g2.drawString(exit,x,y);
        if (commandNum == 3){
            g2.drawString(">",x - gp.tileSize,y);
        }


    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        int x = gp.tileSize * 1;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x+=gp.tileSize;
        y+= gp.tileSize;
        g2.setFont(fontD);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28));

        for (String line:currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y += 30;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        //Color c =Color.MAGENTA;
        g2.setColor(new Color(0,0,0,180));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.white);
        g2.drawRoundRect(x + 5,y + 5,width - 10,height - 10,25,25);
    }

    public int getXForCenteredText(String str) {
        int length = (int) g2.getFontMetrics().getStringBounds(str, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
