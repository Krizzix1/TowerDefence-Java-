package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class shrub extends Tile {
    private static PImage Sprite;
    private static String type = "shrub";

    public shrub(int x, int y){
        super(x,y);
    }

    public static void setSprite(PImage sprite){
        Sprite = sprite;
    }

    public Tile getTile(){
        return this;
    }

    public String getType(){
        return type;
    }

    public void draw(PApplet app){
        app.image(Sprite, this.x, this.y);
    }


}
