package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;



public class grass extends Tile {

    private static PImage Sprite;
    private static String type = "grass";
    public grass(int x, int y){
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

    //public  void onHover(){};

    public void draw(PApplet app){
        app.image(Sprite, this.x, this.y);
    }

}
