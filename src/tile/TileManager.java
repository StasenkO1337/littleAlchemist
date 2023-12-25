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

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        loadMap("/maps/map.txt");
        getTileImage();
    }

    public void loadMap(String map) {
        try {
            InputStream is = getClass().getResourceAsStream(map);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
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
        setup(0,"99", false);
        setup(1,"book1", true);
        setup(2,"book2", true);
        setup(3,"book3", true);
        setup(4,"flor", false);
        setup(5,"grass0", false);
        setup(6,"grass1", false);
        setup(7,"grass01", false);
        setup(8,"grass2", false);
        setup(9,"grass3", false);
        setup(10,"grass4", false);
        setup(11,"grass5", false);
        setup(12,"grass6", false);
        setup(13,"grass7", false);
        setup(14,"grass8", false);
        setup(15,"grass9", false);
        setup(16,"grass10", false);
        setup(17,"grass11", false);
        setup(18,"grass12", false);
        setup(19,"grass13", false);
        setup(20,"GS0", false);
        setup(21,"GS1", false);
        setup(22,"GS2", false);
        setup(23,"GS3", false);
        setup(24,"GS4", false);
        setup(25,"GS5", false);
        setup(26,"GS6", false);
        setup(27,"GS7", false);
        setup(28,"GS8", false);
        setup(29,"GS9", false);
        setup(30,"GS10", false);
        setup(31,"GS11", false);
        setup(32,"road0", false);
        setup(33,"road1", false);
        setup(34,"road2", false);
        setup(35,"road3", false);
        setup(36,"road4", false);
        setup(37,"road5", false);
        setup(38,"road6", false);
        setup(39,"road7", false);
        setup(40,"road8", false);
        setup(41,"road9", false);
        setup(42,"road10", false);
        setup(43,"road11", false);
        setup(44,"road12", false);
        setup(45,"sand0", false);
        setup(46,"sand1", false);
        setup(47,"sand2", false);
        setup(48,"sand3", false);
        setup(49,"sand4", false);
        setup(50,"sand5", false);
        setup(51,"sand6", false);
        setup(52,"sand7", false);
        setup(53,"sand8", false);
        setup(54,"sand9", false);
        setup(55,"sand10", false);
        setup(56,"sand11", false);
        setup(57,"sand12", false);
        setup(58,"sand13", false);
        setup(59,"tree", true);
        setup(60,"wall", true);
        setup(61,"wall1", true);
        setup(62,"water", false);
        setup(63,"water1", false);

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX + gp.player.screenX - gp.player.worldX;
            int screenY = worldY + gp.player.screenY - gp.player.worldY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
