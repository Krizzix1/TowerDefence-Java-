package WizardTD;

public class pauseButton extends button {
    public pauseButton(){
        super(645,100,40,40,"P");
    }
    
    public void function(){
        App.setPause();
    }

    public boolean functionActive(){
        return App.getPaused();
    }

}
