package tile_interactive;
import entity.Entity;
import main.GamePanel;

public class IT_trunk extends interactiveTile{
    GamePanel gp;

    public IT_trunk(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp= gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/objects/tileInteractive/trunk");

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }


}
