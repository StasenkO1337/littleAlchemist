package tile_interactive;
import entity.Entity;
import main.GamePanel;

public class interactiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public interactiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public void PlaySE(){}

    public interactiveTile getDestroyedForm(){
        interactiveTile tile = null;
        return tile;
    }

    public boolean isCorrectItem (Entity entity){
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void update(){
        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 30){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
}
