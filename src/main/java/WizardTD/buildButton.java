package WizardTD;

import processing.core.PApplet;
import processing.core.PFont;

public class buildButton extends button{
    public buildButton(){
        super(645,150,40,40,"T");
    }

    public void function(){
        mouseState.switchTower();
    }
    public boolean functionActive(){
        return mouseState.getTowerMode();
    }

    public void onHover(PApplet app){
        app.fill(155,155,155);
                if(functionActive()){app.fill(238,230,0);}
        app.stroke(0);
        app.strokeWeight(2);
        app.rect(super.x,super.y,super.width,super.height);
        PFont textGUI = app.createFont("Seaford",24,true);
        app.textFont(textGUI, 22);
        app.fill(255,255,255);
        if(tower.getCost() > 999){app.rect(super.x-80,super.y, 70,70);}else{app.rect(super.x-80,super.y, 70,20);}
        app.fill(0);
        app.text(super.text,super.x+9,super.y,30,30);
        app.textFont(textGUI, 15);
        app.text("Cost: "+tower.getCost(), super.x-78, super.y, 70,70);

        
    }
    
}
