package WizardTD;

public class uSButton extends button{
    
    public uSButton(){
        super(645,250,40,40,"U2");
    }

    public boolean functionActive(){
        return mouseState.getU2Mode();
    }

    public void function(){
        mouseState.switchU2();
    }

}
