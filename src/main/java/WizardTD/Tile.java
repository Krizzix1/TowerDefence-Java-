package WizardTD;

import processing.core.PImage;
import processing.core.PApplet;

public abstract class Tile {
    
    protected int x;
    protected int y;
    protected int distance = -1;
    protected String type;

    public PImage Sprite;

    
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

public void changeType(Integer key){}

public abstract Tile getTile();

public int getKey(){return -1;}

public void onHover(PApplet app){
    app.stroke(0);
    if(mouseState.getTowerMode()){
    if(this.getType().equals("grass")){
        app.fill(255,255,0,100);
    }
    else{app.fill(255,0,0,100);}
    
    app.rect(x,y,32,32);
}}

public abstract void draw(PApplet app);

public void setCords(int x, int y){
    this.x = x;
    this.y =y;
}

public void setDistance(int distance){
    this.distance = distance;
}

public int getDistance(){
    return distance;
}

public int getXCell(){
    return x/32;
}

public int getYCell(){
    return (y-40)/32;
}

public void upgrade(boolean[] upgrades){}
public abstract String getType();
}
