package tile_interactive;

import main.GamePanel;

public class IT_plate extends  interactiveTile{

    public static final String itName = "Plate";

    public IT_plate(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp= gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/objects/tileInteractive/plate");
        name = itName;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
