package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.interactiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GamePanel extends JPanel implements Runnable {
    //настройки экрана
    final int originalTileSize = 16;
    final int tileScale = 3;

    //параметры картинки
    public final int tileSize = originalTileSize * tileScale;//48 на 48 пикселей
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; //960 пикселей
    public final int screenHeight = maxScreenRow * tileSize;//576 пикселей


    //параметры мира
    public int maxWorldCol ;
    public int maxWorldRow ;
    public final int maxMap = 10;
    public int currentMap = 0;

    //экран
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    public boolean GameFinished = false;
    public boolean fullScreenOn = false;
    int FPS = 60;

    //система
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui =new UI(this);
    public AssetSetter aSetter = new AssetSetter(this);

    SaveLoad saveLoad = new SaveLoad(this);

    Sound music = new Sound();
    Sound se = new Sound();
    Thread gameThread;
    Config config = new Config(this);
    EnvironmentManager eManager = new EnvironmentManager(this);

    //сущности и обьекты
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public Entity projectile[][] = new Entity[maxMap][30];

    public interactiveTile iTile[][] = new interactiveTile[maxMap][50];
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public EventHandler eHandler = new EventHandler(this);
    public PathFinder pFinder = new PathFinder(this);
    Map map = new Map(this);

    //переключатели стадий игры
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int doorState = 9;
    public final int bedState = 10;
    public final int sleepState = 11;
    public final int mapState = 12;

    //area
    public int currentArea;
    public final int outside = 1;
    public final int indoor = 2;
    public final int dungeon = 3;

    public GamePanel() throws IOException, FontFormatException {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObjects();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setUp();
        playMusic(0);
        gameState = titleState;
        currentArea = outside;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
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
                try {
                    drawToTempScreen();
                } catch (
                        Exception e) {
                    throw new RuntimeException(e);
                }
                drawToScreen();
                //System.out.print(gameState);
                delta--;
            }
        }
    }

    public void update() {
        if( gameState == playState) {
            player.update();
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i]!= null) {
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i]!= null) {
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null) {
                    if(projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i)!= null) {
                    if(particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile[1].length; i ++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }

            eManager.update();
        }

    }

    public void setFullScreen(){
        //получение разрешение устройства
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //получаем ширину и высоту
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void setWindowScreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

    }
    public void drawToTempScreen() throws Exception {
        if ( gameState == titleState){
            ui.draw(g2);
        } else if (gameState == mapState){
            map.drawFullMapScreen(g2);
        }
        else {
            //карта
            tileManager.draw(g2);
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            //добавить сущности в лист
            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }
            //сортировка
            Collections.sort(entityList, (e1, e2) -> {
                int result = Integer.compare(e1.worldY, e2.worldY);
                return result;
            });
            // отрисовка обьектов
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();
            //окружение
            eManager.draw(g2);

            map.drawMiniMap(g2);
            //интерфейс
            ui.draw(g2);
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void resetGame(boolean restart){
        currentArea = outside;
        player.setDefaultPosition();
        player.restoreStatus();
        aSetter.setNPC();
        aSetter.setMonster();
        if (restart == true) {
            player.setDefaultValues();
            player.setItems();
            aSetter.setObjects();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }


    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
