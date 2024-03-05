package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;
    boolean drawPath = true;

    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        tile = new Tile[fileNames.size() + 50];
        getTileImage();

        is = getClass().getResourceAsStream("/maps/testMap.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
            br.close();
        }catch (Exception e){
            System.out.println("проебался братик");
            e.printStackTrace();
        }

        loadMap("/maps/townMap.txt",0);
        loadMap("/maps/testMap.txt",1);
        loadMap("/maps/forest.txt",2);
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getTileImage() {
        String fileName ;
        boolean collision;

        for (int i = 0; i < fileNames.size();i++){

            //Get a file name
            fileName = fileNames.get(i);
            //collision
            if (collisionStatus.get(i).equals("true")){
                collision = true;
            }
            else {
                collision = false;
            }
            setup(i,fileName,collision);
        }

    }



    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles1.0/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX + gp.player.screenX - gp.player.worldX;
            int screenY = worldY + gp.player.screenY - gp.player.worldY;

            if (    worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                if (tile[tileNum].image != null) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                } else {
                    g2.drawImage(tile[126].image, screenX, screenY, null);
                }
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        if(drawPath == true){
            g2.setColor((new Color(255,0,0,70)));
            for(int i = 0; i < gp.pathFinder.pathList.size(); i++){
                int worldX = gp.pathFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pathFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX + gp.player.screenX - gp.player.worldX;
                int screenY = worldY + gp.player.screenY - gp.player.worldY;

                g2.fillRect(screenX,screenY,gp.tileSize, gp.tileSize);
            }
        }
    }
}
