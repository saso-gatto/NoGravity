package Game.View;

import Game.Model.Protagonista;
import Game.Setting.Data;

public class Camera {

    int x,y;

    private int GameWidth, GameHeight;
    private int offsetMinX, offsetMinY, offsetMaxX, offsetMaxY;
    
    public Camera(Protagonista p, int GameWidth, int GameHeight, int mapWidth, int mapHeight){
        this.x = p.getX();
        this.y = p.getY();
        this.GameWidth = GameWidth; 	//larghezza reale schermo es. 1920
        this.GameHeight = GameHeight;	//alt. reale
        
        offsetMinX = 0;
        offsetMinY = 0;
        offsetMaxX = mapHeight - GameHeight; 	
        offsetMaxY = mapWidth - GameWidth;		
    }
 
    public void update(Protagonista p){
    	
        this.x = p.getX()*Data.size - GameWidth/2; 
        this.y = p.getY()*Data.size - GameHeight/2;

        if(x < offsetMinX) {
        	x = offsetMinX;
        }
        else if(x > offsetMaxX) {
        	x = offsetMaxX;
            }
        if(y < offsetMinY) {
        	y = offsetMinY;
        }
        else if(y > offsetMaxY) {
        	y = offsetMaxY;
        }
	}
}
