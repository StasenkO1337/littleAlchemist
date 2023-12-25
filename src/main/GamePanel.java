package main;

import entity.Entity;
import entity.Player;
//import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //настройки экрана
    final int originalTileSize = 16;
    final int tileScale = 3;

    //параметры картинки
    public final int tileSize = originalTileSize * tileScale;//48 на 48 пикселей
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; //768 пикселей
    public final int screenHeight = maxScreenRow * tileSize;//576 пикселей


    //параметры мира
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;

    public boolean GameFinished = false;

    int FPS = 60;

    //система
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    Thread gameThread;
    public UI ui =new UI(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    //сущности и обьекты
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public EventHandler eventHandler = new EventHandler(this);

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel() throws IOException, FontFormatException {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObjects();
        assetSetter.setNPC();
        assetSetter.setMonster();
        //playMusic(0);
        //stopMusic();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        //обновление окна игры, как перс двигается, прорисовка всей картинки
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if( gameState == playState) {
            player.update();
            for(int i = 0; i < npc.length; i++){
                if(npc[i]!= null) {
                    npc[i].update();
                }
            }
            for(int i = 0; i < monster.length; i++){
                if(monster[i]!= null) {
                    if(monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if(monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }
        }
        if (gameState == pauseState){

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //стартовый экран
        if (gameState == titleState){
            ui.draw(g2);
        } else {
            //карта
            tileManager.draw(g2);
            //добавить сущности в лист
            entityList.add(player);

            for(int i = 0; i < npc.length; i ++){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i ++){
                if (obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i ++){
                if (monster[i] != null){
                    entityList.add(monster[i]);
                }
            }


            //сортировка
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // отрисовка обьектов

            for (int i = 0; i < entityList.size(); i ++){
                entityList.get(i).draw(g2);
            }

            entityList.clear();

            //интервейс
            ui.draw(g2);

            g2.dispose();
        }

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
