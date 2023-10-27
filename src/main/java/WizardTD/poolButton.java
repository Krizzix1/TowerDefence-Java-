package WizardTD;

import processing.core.PApplet;
import processing.core.PFont;

public class poolButton extends button{
    public poolButton(){
        super(645,350,40,40,"M");
    }
    
    public void function(){
        App.manaPool();
    }

    public boolean functionActive(){
        return false;
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
        if(App.getPoolCost() > 999){app.rect(super.x-80,super.y, 70,70);}else{app.rect(super.x-80,super.y, 70,20);}
        app.fill(0);
        app.text(super.text,super.x+9,super.y,30,30);
        app.textFont(textGUI, 15);
        app.text("Cost: "+Math.round(App.getPoolCost()), super.x-78, super.y, 70,70);

    
    }
}
