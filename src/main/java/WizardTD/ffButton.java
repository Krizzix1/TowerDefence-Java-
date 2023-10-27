package WizardTD;

public class ffButton extends button{
    public ffButton(){
        super(645,50,40,40,"FF");
    }

public void function(){
    App.setFF();
}

public boolean functionActive(){
    return App.getFastFoward();
}

}
