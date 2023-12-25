package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed,leftPressed,rightPressed,ePressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gp.gameState == gp.titleState){
            if(keyCode == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if(keyCode == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if(keyCode == KeyEvent.VK_E){
                if (gp.ui.commandNum == 0){
                    gp.gameState =gp.playState;
                    //gp.playMusic(0);
                }
                if (gp.ui.commandNum == 1){
                    //загрузка
                }
                if (gp.ui.commandNum == 2){
                    //настройки
                }
                if (gp.ui.commandNum == 3){
                    System.exit(0);
                }
            }
        }
        //актив
        else if(gp.gameState == gp.playState) {
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
                gp.gameState = gp.pauseState;
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                gp.player.attacking = true;
            }
            if ( keyCode == KeyEvent.VK_E){
                ePressed = true;
            }
        }
        //пауза
        else if (gp.gameState == gp.pauseState){
            if (keyCode == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        //диалог
        else if (gp.gameState == gp.dialogueState) {
            if(keyCode == KeyEvent.VK_SPACE){
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W){
            upPressed = false;
        }
        if(keyCode == KeyEvent.VK_S){
            downPressed = false;
        }
        if(keyCode == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(keyCode == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            gp.player.attacking = false;
        }
    }
}
