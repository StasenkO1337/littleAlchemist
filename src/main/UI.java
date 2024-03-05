package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_coin;
import object.OBJ_mana;
//import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font fontD;

    BufferedImage heartF, heartH, heartB , manaF,manaB,coin;

    public boolean messageOn = false;
    public String currentDialogue = "";
    public int commandNum = 0;


    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    int subState = 0;
    int counter= 0;

    public Entity npc;

    public UI(GamePanel gp){
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x16y32pxGridGazer.ttf");
            fontD = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Entity heart = new OBJ_Heart(gp);
        Entity mana = new OBJ_mana(gp);
        Entity Coin = new OBJ_coin(gp);

        heartF = heart.image;
        heartH = heart.image2;
        heartB = heart.image3;
        manaF = mana.image;
        manaB = mana.image2;
        coin = Coin.down1;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(fontD);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        g2.setColor(Color.cyan);

        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }

        if (gp.gameState == gp.titleState) {
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

        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player,true);
            drawPlayerLife();
        }

        if (gp.gameState == gp.optionsState){
            drawOptionScreen();
        }

        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }

        if (gp.gameState == gp.transitionState){
            drawTransition();
        }

        if (gp.gameState == gp.tradeState){
            drawTradeScreen();
        }

        if (gp.gameState == gp.doorState){
            doorScreen();
        }
    }

    public void drawTradeScreen() {
        switch (subState){
            case 0:trade_select();break;
            case 1:trade_buy();break;
            case 2:trade_sell();break;
        }
        gp.keyH.fPressed = false;
    }

    public void doorScreen(){
        int x = gp.tileSize * 8 + 24;
        int y = gp.tileSize * 2;
        int width = gp.tileSize * 3;
        int height = gp.tileSize * 3 + 24;

        drawSubWindow(x,y,width,height);
        String text;

        g2.setColor(Color.white);

        x+=gp.tileSize - 14;
        y+=gp.tileSize;
        g2.drawString("Open",x,y);
        if (commandNum == 0){
            g2.drawString(">",x - 20,y);
            if (gp.keyH.fPressed == true){
                if (gp.player.haveKey1 == false) {
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Нужен ключ";
                } else if (gp.player.haveKey1 == true) {
                    gp.obj[1][0] = null;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "дверь открылась!";
                }
            }
        }
        y+=gp.tileSize + 10;
        g2.drawString("Leave",x,y);
        if (commandNum == 1){
            g2.drawString(">",x - 20,y);
            if (gp.keyH.fPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "вернусь позже";
            }
        }
        gp.keyH.fPressed = false;
    }

    public void trade_select(){
        int x = gp.tileSize * 8 + 24;
        int y = gp.tileSize * 2;
        int width = gp.tileSize * 3;
        int height = gp.tileSize * 4 + 24;

        drawSubWindow(x,y,width,height);
        String text;

        currentDialogue = "Чего хотел?";
        drawDialogueScreen();

        g2.setColor(Color.white);

        x+=gp.tileSize - 14;
        y+=gp.tileSize;
        g2.drawString("Buy",x,y);
        if (commandNum == 0){
            g2.drawString(">",x - 20,y);
            if (gp.keyH.fPressed == true){
                subState = 1;
            }
        }
        y+=gp.tileSize + 10;
        g2.drawString("Sell",x,y);
        if (commandNum == 1){
            g2.drawString(">",x - 20,y);
            if (gp.keyH.fPressed == true){
                subState = 2;
            }
        }
        y+=gp.tileSize + 10;
        g2.drawString("Leave",x,y);
        if (commandNum == 2){
            g2.drawString(">",x - 20,y);
            if (gp.keyH.fPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Бывай";
            }
        }
        gp.keyH.fPressed = false;
    }

    public void trade_sell() {
        drawInventory(gp.player, true);

        int x = gp.tileSize * 2;
        int y = gp.tileSize * 7;
        int width = gp.tileSize * 6 + 20;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("Your money: " + gp.player.coin, x + 54, y + 54);

        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);

        if (itemIndex < gp.player.inventory.size()) {
            int price = gp.player.inventory.get(itemIndex).cost;
            y = gp.tileSize * 9 + 10;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x, y + 18, 64, 64, null);
            g2.drawString("Cost: " + price, x + 54, y + 54);
            if (gp.keyH.fPressed == true) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentMagic) {
                    //zvyk
                } else {
                    gp.player.coin += price;
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    }else {
                        gp.player.inventory.remove(itemIndex);
                    }
                }
            }
        }

        x = gp.tileSize * 16;
        y = gp.tileSize * 7;
        drawSubWindow(x, y, gp.tileSize * 3, gp.tileSize * 2);
        g2.drawString("ESC - back", x+24, y+36);
    }

    public void trade_buy() {
        drawInventory(gp.player, false);
        drawInventory(npc, true);

        int x = gp.tileSize * 2;
        int y = gp.tileSize * 7;
        int width = gp.tileSize * 6 + 20;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("Your money: " + gp.player.coin, x+54, y+54);

        int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
        if (itemIndex < npc.inventory.size()){
            int price = npc.inventory.get(itemIndex).cost;

            y = gp.tileSize * 9 + 10;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin,x ,y + 18,64,64,null);
            g2.drawString("Cost: " + price , x+54, y+54);

            if (gp.keyH.fPressed == true){
                if (npc.inventory.get(itemIndex).cost > gp.player.coin){
                    //звук типа нет
                }

                else {
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
                        gp.player.coin -= npc.inventory.get(itemIndex).cost;
                    } else {
                        //звук типа нет
                    }
                }
            }
        }


        x = gp.tileSize * 16;
        y = gp.tileSize * 7;
        drawSubWindow(x, y, gp.tileSize * 3, gp.tileSize * 2);
        g2.drawString("ESC - back", x+24, y+36);
    }


    private void drawTransition() {
        counter++;
        g2.setColor(new Color(0,0,0, counter * 5));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        if (counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eventHandler.tmpMap;
            gp.player.worldX = gp.tileSize * gp.eventHandler.tmpX;
            gp.player.worldY = gp.tileSize * gp.eventHandler.tmpY;
        }
    }

    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0, 200));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        String text;
        int x,y;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110));

        text = "Потрачено";
        g2.setColor(Color.white);
        x = getXForCenteredText(text);
        y = gp.tileSize * 3;
        g2.drawString(text,x,y);

        g2.setColor(Color.red);
        g2.drawString(text,x-4,y-4);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));

        text = "Retry";
        x = getXForCenteredText(text);
        y += gp.tileSize * 5;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">",x-40,y);
        }

        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.tileSize + 20;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">",x-40,y);
        }
    }

    private void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch (subState){
            case 0: options_top(frameX,frameY); break;
            case 1: fullScreenNotification(frameX,frameY);break;
            case 2: options_control(frameX,frameY);break;
            case 3: options_endGameConformation(frameX,frameY);break;
        }
        gp.keyH.enterPressed = false;
    }

    public void options_control(int frameX,int frameY){
        int textX;
        int textY;

        String text = "Control";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move",textX,textY); textY+=gp.tileSize;
        g2.drawString("Attack",textX,textY); textY+=gp.tileSize;
        g2.drawString("Magic",textX,textY); textY+=gp.tileSize;
        g2.drawString("Use",textX,textY); textY+=gp.tileSize;
        g2.drawString("Inventory",textX,textY); textY+=gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD",textX,textY); textY+=gp.tileSize;
        g2.drawString("SPACE",textX,textY); textY+=gp.tileSize;
        g2.drawString("E",textX,textY); textY+=gp.tileSize;
        g2.drawString("F",textX,textY); textY+=gp.tileSize;
        g2.drawString("P",textX,textY); textY+=gp.tileSize;

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("back",textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX - 20,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }
    }

    public void options_endGameConformation(int frameX,int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*2;

        currentDialogue = "Quit the game and\nreturn to the title\nscreen?";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY +=40;
        }

        //yes\no

        String text = "Yes";
        textX = getXForCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text,textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX - 25,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
        text = "No";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text,textX,textY);
        if(commandNum == 1){
            g2.drawString(">",textX - 25,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 1;
            }
        }
    }

    public void options_top(int frameX,int frameY){
        int textX;
        int textY;

        //title
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        //полный экран
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full screen", textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX - 20,textY);
            if(gp.keyH.enterPressed == true){
                if (gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                    gp.setFullScreen();
                } else if (gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                    gp.setFullScreen();
                }
                subState = 1;
            }
        }

        //музыка
        textY += gp.tileSize ;
        g2.drawString("Music", textX,textY);
        if (commandNum == 1){
            g2.drawString(">",textX - 20,textY);
        }

        //эфекты
        textY += gp.tileSize ;
        g2.drawString("SE", textX,textY);
        if (commandNum == 2){
            g2.drawString(">",textX - 20,textY);
        }

        //управление
        textY += gp.tileSize ;
        g2.drawString("Control", textX,textY);
        if (commandNum == 3){
            g2.drawString(">",textX - 20,textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }

        //выход
        textY += gp.tileSize ;
        g2.drawString("Exit", textX,textY);
        if (commandNum == 4){
            g2.drawString(">",textX - 20,textY);
            if (gp.keyH.enterPressed == true){
                subState = 3;
                commandNum = 0;
            }
        }

        textY += gp.tileSize * 2;
        g2.drawString("Resume", textX,textY);
        if (commandNum == 5){
            g2.drawString(">",textX - 20,textY);
            if (gp.keyH.enterPressed == true){
                subState = gp.playState;
                commandNum = 0;
            }
        }

        //////////////////////////////////////

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2 + 24;
        g2.drawRect(textX,textY,24,24);
        if (gp.fullScreenOn == true){
            g2.fillRect(textX,textY,24,24);
        }

        textY += gp.tileSize;
        g2.drawRect(textX - 24,textY,120,24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX - 24,textY,volumeWidth,24);

        textY += gp.tileSize;
        g2.drawRect(textX - 24,textY,120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX - 24,textY,volumeWidth,24);

        gp.config.saveConfig();
    }

    private void fullScreenNotification (int frameX,int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "перезайди в игру";

        g2.drawString(currentDialogue,textX,textY);

        textY =frameY + gp.tileSize * 8;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX - 20,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }
    }

    private void drawInventory(Entity entity,boolean cursor) {
        int x = 0;
        int y = 0;
        int width = gp.tileSize * 6 + 10;
        int height = gp.tileSize * 5 + 10;;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            if (gp.gameState == gp.characterState) {
                x = gp.tileSize * 8;
            } else {
                x = gp.tileSize * 9;
            }
            y = gp.tileSize * 1 + 20;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }   else  {
            x = gp.tileSize * 2;
            y = gp.tileSize * 1 + 20;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        drawSubWindow(x, y, width, height );

        //slot
        final int slotXstart = x + 20;
        final int slotYstart = y + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 6;

        //предметы
        for (int i = 0; i < entity.inventory.size(); i++) {

                if( entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentMagic) {
                g2.setColor(new Color(218, 182, 30));
                g2.fillRoundRect( slotX, slotY, gp.tileSize,gp.tileSize,10,10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX = 0;
                int amountY = 0;

                String s = "" + entity.inventory.get(i).amount;
                amountX = slotX + 40;
                amountY = slotY + 44;

                g2.setColor(new Color(89, 87, 87));
                g2.drawString(s,amountX,amountY);
                g2.setColor(new Color(255, 255, 255));
                g2.drawString(s,amountX - 3,amountY - 3);
            }
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //cursor
        if (cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWight = gp.tileSize;
            int cursorHeight = gp.tileSize;
            g2.setColor(Color.cyan);
            g2.drawRoundRect(cursorX, cursorY, cursorWight, cursorHeight, 10, 10);
            //описание предмета
            int dx = 0;
            if (gp.gameState == gp.characterState) {
                dx = gp.tileSize * 8;
            } else {
                dx = gp.tileSize * 9;
            }
            int dy = gp.tileSize * 7;
            int textX = dx + 40;
            int textY = dy + 40;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16));
            int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dx, dy, width - 10, height - 50);
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol,int slotRow){
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }

    private void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 5;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 40;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    private void drawCharacterScreen() {
        int x = gp.tileSize * 1;
        int y = gp.tileSize * 1 + 20;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 10;
        drawSubWindow(x, y, width, height);

        x += 20;
        y += 20;
        g2.setFont(fontD);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));

        y += 20;
        g2.drawString("Level", x, y);
        y += 35;
        g2.drawString("Strength", x, y);
        y += 35;
        g2.drawString("Dexterity", x, y);
        y += 35;
        g2.drawString("attack", x, y);
        y += 35;
        g2.drawString("defense", x, y);
        y += 35;
        g2.drawString("exp", x, y);
        y += 35;
        g2.drawString("nextLvlExp", x, y);
        y += 35;
        g2.drawString("coin", x, y);
        y += 70;
        g2.drawString("Weapon", x, y);
        y += 35;
        g2.drawString("Magic", x, y);


        int tailX = (x + width);
        y = gp.tileSize * 1 + 60;
        String value;

        value = String.valueOf(gp.player.lvl);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.strength);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.dexterity);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.attack);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.defense);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.exp);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.nextLvlExp);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        value = String.valueOf(gp.player.coin);
        x = getXForAlignToRight(value, tailX);
        g2.drawString(value, x, y);
        y += 35;
        x -= 20;
        g2.drawImage(gp.player.currentWeapon.down1, x, y, null);
        y += 50;
        g2.drawImage(gp.player.currentMagic.down1, x, y, null);
    }

    private void drawPlayerLife() {
        int x = gp.tileSize / 2 - 10;
        int y = gp.tileSize / 2 - 20;
        int i = 0;

        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heartB, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2 - 10;
        y = gp.tileSize / 2 - 20;
        i = 0;
        while (i < gp.player.life) {
            g2.drawImage(heartH, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heartF, x, y, null);
            }
            x += gp.tileSize;
            i++;
        }

        //mana
        x = gp.tileSize / 2 - 24;
        y = gp.tileSize / 2 + 5;
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(manaB, x , y, null);
            if (i < gp.player.mana) {
                g2.drawImage(manaF, x , y, null);
            }
            x += gp.tileSize / 2;
            i++;
        }
    }

    private void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90));
        String text = "Little Alchemist";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0,0,5000,5000);

        g2.setColor(new Color(30, 190, 30, 192));
        g2.drawString(text, x + 3, y + 3);

        g2.setColor(new Color(120, 210, 120));
        g2.drawString(text, x, y);

        g2.drawImage(gp.player.down1, 60, 60, gp.tileSize, gp.tileSize, null);

        //меню
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 56));
        g2.setColor(new Color(250, 250, 250));

        String newG = "New game";
        x = getXForCenteredText(newG);
        y = gp.tileSize * 6;
        g2.drawString(newG, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }


        String loadG = "Load game";
        x = getXForCenteredText(loadG);
        y += gp.tileSize + 10;
        g2.drawString(loadG, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        String setting = "Options";
        x = getXForCenteredText(setting);
        y += gp.tileSize + 10;
        g2.drawString(setting, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }


        String exit = "Exit";
        x = getXForCenteredText(exit);
        y += gp.tileSize + 10;
        g2.drawString(exit, x, y);
        if (commandNum == 3) {
            g2.drawString(">", x - gp.tileSize, y);
        }


    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
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

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(fontD);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 30;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        //Color c =Color.MAGENTA;
        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.white);
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXForCenteredText(String str) {
        int length = (int) g2.getFontMetrics().getStringBounds(str, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXForAlignToRight(String str, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(str, g2).getWidth();
        int x = tailX - length - 50;
        return x;
    }
}
