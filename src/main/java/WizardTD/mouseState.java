package WizardTD;

public class mouseState {
    private static boolean towerMode = false;
    private static boolean u1Mode = false;
    private static boolean u2Mode = false;
    private static boolean u3Mode = false;

    public static void reset(){
    towerMode = false;
    u1Mode = false;
    u2Mode = false;
    u3Mode = false;      
    }


    public static void switchTower(){
        towerMode = !towerMode;
    }

    public static boolean getTowerMode(){
        return towerMode;
    }

    public static void switchU1(){
        u1Mode = !u1Mode;
    }
    
    public static void switchU2(){
        u2Mode = !u2Mode;
    }
    public static void switchU3(){
        u3Mode = !u3Mode;
    }

    public static boolean getU1Mode(){
        return u1Mode;
    }
    public static boolean getU2Mode(){
        return u2Mode;
    }
    public static boolean getU3Mode(){
        return u3Mode;
    }

}
