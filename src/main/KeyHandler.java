package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, fPressed, magicKeyPressed, enterPressed,ePressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            titleState(keyCode);
        }
        //актив
        else if (gp.gameState == gp.playState) {
            playState(keyCode);
        }
        //пауза
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(keyCode);
        }
        //диалог
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(keyCode);
        }
        //перс статы
        else if (gp.gameState == gp.characterState) {
            characterState(keyCode);
        } else if (gp.gameState == gp.optionsState) {
            optionState(keyCode);
        } else if (gp.gameState == gp.tradeState) {
            tradeState(keyCode);
        } else if (gp.gameState == gp.doorState) {
            doorState(keyCode);
        }
    }
    private void tradeState(int keyCode) {
        if (keyCode == KeyEvent.VK_F) {
            fPressed = true;
        }

        if (gp.ui.subState == 0) {
            if(keyCode == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
                gp.playSE(3);
            }
            if(keyCode == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
                gp.playSE(3);
            }
        }

        if (gp.ui.subState == 1){
            npcInventory(keyCode);
            if (keyCode == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2){
            playerInventory(keyCode);
            if (keyCode == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }
    public void doorState(int keyCode){

        if (keyCode == KeyEvent.VK_F) {
            fPressed = true;
        }
        if (gp.ui.subState == 0) {
            if(keyCode == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
                gp.playSE(3);
            }
            if(keyCode == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
                gp.playSE(3);
            }
        }
    }
    public void titleState(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 3;
            }
        }
        if (keyCode == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 3) {
                gp.ui.commandNum = 0;
            }
        }
        if (keyCode == KeyEvent.VK_E) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                //gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1) {
                //загрузка
            }
            if (gp.ui.commandNum == 2) {
                //настройки
            }
            if (gp.ui.commandNum == 3) {
                System.exit(0);
            }
        }
    }
    public void gameOverState(int keyCode){
        if (keyCode == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(3);
        }
        if (keyCode == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
            gp.playSE(3);
        }

        if(keyCode == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    public void playState(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            gp.player.attacking = true;
        }
        if (keyCode == KeyEvent.VK_F) {
            fPressed = true;
        }
        if (keyCode == KeyEvent.VK_P) {
            gp.gameState = gp.characterState;
        }
        if(keyCode == KeyEvent.VK_E){
            magicKeyPressed = true;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }
    public void pauseState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_F) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int keyCode) {
        if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (keyCode == KeyEvent.VK_F) {
            gp.player.selectItem();
        }
        playerInventory(keyCode);
    }
    public void playerInventory(int keyCode){
        if (keyCode == KeyEvent.VK_W) {
            if(gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
            } else {
                gp.ui.playerSlotRow = 3;
            }
            //zvyk
        }
        if (keyCode == KeyEvent.VK_S) {
            if(gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
            } else {
                gp.ui.playerSlotRow = 0;
            }
        }
        if (keyCode == KeyEvent.VK_A) {
            if(gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
            } else {
                gp.ui.playerSlotCol = 4;
            }
        }
        if (keyCode == KeyEvent.VK_D) {
            if(gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
            } else {
                gp.ui.playerSlotCol = 0;
            }
        }
    }
    public void npcInventory(int keyCode){
        if (keyCode == KeyEvent.VK_W) {
            if(gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
            } else {
                gp.ui.npcSlotRow = 3;
            }
            //zvyk
        }
        if (keyCode == KeyEvent.VK_S) {
            if(gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
            } else {
                gp.ui.npcSlotRow = 0;
            }
        }
        if (keyCode == KeyEvent.VK_A) {
            if(gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
            } else {
                gp.ui.npcSlotCol = 4;
            }
        }
        if (keyCode == KeyEvent.VK_D) {
            if(gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
            } else {
                gp.ui.npcSlotCol = 0;
            }
        }
    }
    private void optionState(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(keyCode == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 5;break;
            case 3: maxCommandNum = 1;break;
        }

        if(keyCode == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(1);
            if (gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(keyCode == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(1);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if(keyCode == KeyEvent.VK_A) {
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(2);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    gp.playSE(2);
                }
            }
        }
        if(keyCode == KeyEvent.VK_D) {
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(2);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                    gp.playSE(2);
                }
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            gp.player.attacking = false;
        }
        if(keyCode == KeyEvent.VK_E){
            magicKeyPressed = false;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
