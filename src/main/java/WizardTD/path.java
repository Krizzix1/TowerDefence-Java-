package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.Map;
import java.util.HashMap;

public class path extends Tile{


    private int key = 0;
    private static Map<Integer, PImage> paths = new HashMap<Integer, PImage>();
    private PImage Sprite;
    private static String type = "path";
    public path(int x, int y){
        super(x,y);
        this.Sprite = paths.get(0);
    }


    public static void setSprite(PImage sprite, Integer key){
        paths.put(key,sprite);
    }



    public void changeType(Integer key){
        Sprite = paths.get(key);
        this.key = key;

    }

    public path getTile(){
        return this;
    }

    public String getType(){
        return type;
    }

    public int getKey(){
        return key;
    }
    //public  void onHover(){};

    public void draw(PApplet app){
        app.image(Sprite, this.x, this.y);
    }
}
