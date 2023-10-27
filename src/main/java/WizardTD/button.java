package WizardTD;

import processing.core.PApplet;
import processing.core.PFont;

public abstract class button {
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected String text;
    protected boolean hovering;

    public button(int x, int y, int widgth, int height, String text){
        this.x = x;
        this.y = y;
        this.width = widgth;
        this.height = height;
        this.text = text;

    }
    public  void checkHovering(int mouseX, int mouseY, PApplet app){
        if(x<mouseX && x+width>mouseX && y<mouseY && y+height>mouseY){
            onHover(app);
            hovering = true;
        }
        else{hovering = false;}
    }

    public boolean isHovering(){
        return hovering;
    }

    public abstract void function();
    public void onHover(PApplet app){

        app.fill(155,155,155);
                if(functionActive()){app.fill(238,230,0);}
        app.stroke(0);
        app.strokeWeight(2);
        app.rect(x,y,width,height);
        PFont textGUI = app.createFont("Seaford",24,true);
        app.textFont(textGUI, 22);
        app.fill(0);
        app.text(text,x+9,y,30,30);

    }
    public abstract boolean functionActive();

    public void draw(PApplet app){

        if(functionActive()){app.fill(238,230,0);}
        else{app.fill(0,0,0,0);}

        app.stroke(0);
        app.strokeWeight(2);
        app.rect(x,y,width,height);
        app.fill(0);
        PFont textGUI = app.createFont("Seaford",24,true);
        app.textFont(textGUI, 22);
        app.text(text,x+8,y+4,30,30);
    }

}




