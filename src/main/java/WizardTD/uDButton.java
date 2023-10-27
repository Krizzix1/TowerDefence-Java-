package WizardTD;

public class uDButton extends button{
 
    
    public uDButton(){
        super(645,300,40,40,"U3");
    }

    public boolean functionActive(){
        return mouseState.getU3Mode();
    }


    public void function(){
        mouseState.switchU3();
    }

}
