package tile_interactive;
import entity.Entity;
import main.GamePanel;

import java.awt.*;
public class IT_tree extends interactiveTile{

    GamePanel gp;

    public IT_tree(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp= gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/objects/tileInteractive/tree");
        destructible = true;

        life = 3;
    }

    public boolean isCorrectItem (Entity entity){
        boolean isCorrectItem = false;

        if(entity.currentWeapon.type == typeWeapon){
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void PlaySE(){
        //gp.playSE(что-то)
    }

    public interactiveTile getDestroyedForm(){
        interactiveTile tile = new IT_trunk(gp,worldX/gp.tileSize,worldY/gp.tileSize);
        return tile;
    }

    public Color getParticleColor(){
        Color color = new Color(65,50,30);
        return color;
    }

    public int getParticleSize(){
        int size = 6;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
