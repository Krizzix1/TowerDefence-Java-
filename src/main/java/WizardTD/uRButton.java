package WizardTD;

public class uRButton extends button{
    
    public uRButton(){
        super(645,200,40,40,"U1");
    }

    public boolean functionActive(){
        return mouseState.getU1Mode();
    }


    public void function(){
        mouseState.switchU1();
    }

}
