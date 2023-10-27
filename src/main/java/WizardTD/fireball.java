package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class fireball {

    private static PImage sprite;
    private Enemy target;
    private float x;
    private float y;

    public fireball(int x, int y, Enemy target){
        this.target = target;
        this.x = x;
        this.y = y;
    }

    public void TICK(){
        if(! App.getPaused()){

            double angle = Math.atan((target.getY() - y)/(target.getX() - x));
            if(angle < 0){
                if(target.getY() - y > 0){
                    angle = Math.PI+angle;
                }
            }
            else{
                if(target.getY() - y < 0){
                    angle = Math.PI+angle;
                }
            }
            if(App.getFastFoward()){
            x += Math.cos(angle)*8;
            y += Math.sin(angle)*8;
            }
            else{
            x += Math.cos(angle)*4;
            y += Math.sin(angle)*4;
            }
        }
    }

    public Enemy getTarget(){
        return target;
    }

    public static void setSprite(PImage Sprite){
        sprite = Sprite;
    }

    public float getY(){
        return y;
    }

    public float getX(){
        return x;
    }

    public void draw(PApplet app){
        app.image(sprite,x,y);
    }
}
