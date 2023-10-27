package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class wizardHouse extends Tile{
    private static PImage Sprite;
    private static String type = "wHouse";

    
    public wizardHouse(int x, int y){
        super(x,y);
    }



    public static void setSprite(PImage sprite){
        Sprite = sprite;
    }


    public Tile getTile(){
        return this;
    }

    public void draw(PApplet app){
        app.image(Sprite, this.x, this.y);
    }

    public String getType(){
        return type;
    }

    //public  void onHover(){};

    public int getYCell(){
        return (y-35)/32;
    }
}
