package WizardTD;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {


    @Test
    public void simpleTest() {
        App app = new App();

        //for(int frame =0 ; frame < 7200; frame++){
            //app.loop();
        //}
        assertNotNull(app);
    }

    @Test
    public void addMana(){
        App app = new App();
        app.addMana(20);
        assertEquals(app.getMana(), 220);
    }

    @Test
    public void setPath(){
        backGround bg = new backGround("level1.txt");
        assertNotNull(bg);
    }
    
    @Test
    public void setPath1(){
        backGround bg = new backGround("level2.txt");
        assertNotNull(bg);
    }

    @Test
    public void setPath2(){
        backGround bg = new backGround("level3.txt");
        assertNotNull(bg);
    }

    @Test
    public void setPath3(){
        backGround bg = new backGround("level4.txt");
        assertNotNull(bg);
    }


}

