package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public static PFont textGUI;
    public static int frame;
    private backGround background;
    private EnemyCollection activeEnemies;
    private List<wave> waves;
    private List<tower> towers;
    private static List<button> buttons;
    public Random random = new Random();
    private int currentWave;
    private int waveTimerFrame;
    private static float mana;
    private static float maxMana;
    private static float manaPerSecond;
    private static boolean paused = false;
    private static boolean fastForward = false;
    private static int iPoolCost;
    private static int poolCostIncrease;
    private static float poolCapMulti;
    private static float poolManaGainedMulti;
    private static float poolCost;
    private static boolean lost = false;
    private static boolean won = false;
	
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);

        activeEnemies = new EnemyCollection();
        waves = new ArrayList<wave>();
        towers = new ArrayList<tower>();
        buttons = new ArrayList<button>();
        currentWave = 0;
        frame = 0;

        // Load images during setup
		// Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");

        grass.setSprite(this.loadImage("src/main/resources/WizardTD/grass.png"));
        shrub.setSprite(this.loadImage("src/main/resources/WizardTD/shrub.png"));
        path.setSprite(this.loadImage("src/main/resources/WizardTD/path0.png"), 0);
        path.setSprite(this.loadImage("src/main/resources/WizardTD/path3.png"), 3);
        PImage turnPath = this.loadImage("src/main/resources/WizardTD/path1.png");
        PImage tIntersection = this.loadImage("src/main/resources/WizardTD/path2.png");
        path.setSprite(rotateImageByDegrees(this.loadImage("src/main/resources/WizardTD/path0.png"), 90) , 4);
        path.setSprite(turnPath, 1);
        path.setSprite(rotateImageByDegrees(turnPath, 90) , 5);
        path.setSprite(rotateImageByDegrees(turnPath, 180) , 6);
        path.setSprite(rotateImageByDegrees(turnPath, 270) , 7);
        path.setSprite(tIntersection, 2);
        path.setSprite(rotateImageByDegrees(tIntersection, 90) , 8);
        path.setSprite(rotateImageByDegrees(tIntersection, 180) , 9);
        path.setSprite(rotateImageByDegrees(tIntersection, 270) , 10);
        tower.setSprite(this.loadImage("src/main/resources/WizardTD/tower0.png"),0);
        tower.setSprite(this.loadImage("src/main/resources/WizardTD/tower1.png"),1);
        tower.setSprite(this.loadImage("src/main/resources/WizardTD/tower2.png"),2);
        wizardHouse.setSprite(this.loadImage("src/main/resources/WizardTD/wizard_house.png"));
        fireball.setSprite(this.loadImage("src/main/resources/WizardTD/fireball.png"));
        Enemy.setDespawnSprite(this.loadImage("src/main/resources/WizardTD/gremlin1.png"),0);
        Enemy.setDespawnSprite(this.loadImage("src/main/resources/WizardTD/gremlin2.png"),1);
        Enemy.setDespawnSprite(this.loadImage("src/main/resources/WizardTD/gremlin3.png"),2);
        Enemy.setDespawnSprite(this.loadImage("src/main/resources/WizardTD/gremlin4.png"),3);
        Enemy.setDespawnSprite(this.loadImage("src/main/resources/WizardTD/gremlin5.png"),4);


        
        //setup gui
        fill(132,114,74);
        stroke(132,114,74);
        rect(640,400,120,680);
        textGUI = createFont("Seaford",24,true);
        //setup buttons
        buttons.add(new ffButton());
        buttons.add(new pauseButton());
        buttons.add(new buildButton());
        buttons.add(new poolButton());
        buttons.add(new uSButton());
        buttons.add(new uRButton());
        buttons.add(new uDButton());



        JSONObject jobj = this.loadJSONObject(configPath);
        this.background = new backGround(jobj.getString("layout"));
        mana = jobj.getFloat("initial_mana");
        maxMana = jobj.getFloat("initial_mana_cap");
        manaPerSecond = jobj.getFloat("initial_mana_gained_per_second");
        tower.setiCost(jobj.getInt("tower_cost"));
        tower.setiDamage(jobj.getInt("initial_tower_damage"));
        tower.setiRange(jobj.getInt("initial_tower_range"));
        tower.setiSpeed(jobj.getFloat("initial_tower_firing_speed"));
        iPoolCost = jobj.getInt("mana_pool_spell_initial_cost");
        poolCostIncrease = jobj.getInt("mana_pool_spell_cost_increase_per_use");
        poolCapMulti = jobj.getFloat("mana_pool_spell_cap_multiplier");
        poolManaGainedMulti = jobj.getFloat("mana_pool_spell_mana_gained_multiplier");

        poolCost = iPoolCost;
        JSONArray jArray = jobj.getJSONArray("waves");

        wave.setSpawnPoints(background);
        for(int i=0;i<jArray.size();i++){
            waves.add(new wave(jArray.getJSONObject(i), this));
        }

        for(int i=1;i<waves.size();i++){
            waves.get(i).addPrePause(waves.get(i-1).getPrePause()+waves.get(i-1).getDuration());
        }


        waveTimerFrame = waves.get(0).getFramesToRun();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
        //use key

    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        if(key == 't'){mouseState.switchTower();}
        else if(key == '1'){mouseState.switchU1();}
        else if(key == '2'){mouseState.switchU2();}
        else if(key == '3'){mouseState.switchU3();}
        else if(key == 'f'){fastForward = !fastForward;}
        else if(key == 'p'){paused = !paused;}
        else if(key == 'm'){manaPool();}
        else if(lost && key == 'r'){restart();}

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //USE mouseX and mouseY then divide by 32 to get cell
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouseX<640 && mouseY<680 && mouseY > 40){
            boolean[] upgrades = {mouseState.getU1Mode(),mouseState.getU2Mode(),mouseState.getU3Mode()};
            if(mouseState.getTowerMode() && background.getTile(mouseX/32,(mouseY-40)/32) instanceof grass && mana > tower.getCost() + (mouseState.getU1Mode() ? 1:0)*20 + (mouseState.getU2Mode() ? 1:0)*20 + (mouseState.getU3Mode() ? 1:0)*20){
                mana -= tower.getCost() + (mouseState.getU1Mode() ? 1:0)*20 + (mouseState.getU2Mode() ? 1:0)*20 + (mouseState.getU3Mode() ? 1:0)*20;
                tower Tower = new tower (mouseX/32, (mouseY-40)/32,upgrades, activeEnemies);
                background.setTile(Tower, mouseX/32, (mouseY-40)/32);
                towers.add(Tower);
            }
            else if(background.getTile(mouseX/32,(mouseY-40)/32) instanceof tower){
                background.getTile(mouseX/32,(mouseY-40)/32).upgrade(upgrades);
            }
            }
        for(button b : buttons){
            if(b.isHovering()){
                b.function();
            }
        }
    }


    public static void addMana(float manaToAdd){
        if(mana + manaToAdd > maxMana){
            mana = maxMana;
        }
        else if(mana + manaToAdd < 0){
            lost = true;
            mana = 0;
            //Die
        }
        else{mana += manaToAdd;}
    }
    
    public static float getPoolCost(){
        return poolCost;
    }

    public void lose(){
        strokeWeight(3);
        stroke(0);
        fill(255);
        rect(320,290,122,60);
        fill(0);
        textFont(textGUI,22);
        text("YOU LOST", 325, 320);
        textFont(textGUI,12);
        text("\"r\" to restart", 345, 335);
    }

    public static void manaPool(){
        if(mana > poolCost){
            addMana(-poolCost);
            poolCost += poolCostIncrease;
            maxMana = maxMana*poolCapMulti;
            manaPerSecond = manaPerSecond*poolManaGainedMulti;
        }
    }
    public static void setFF(){
        fastForward = !fastForward;
    }

    public static void setPause(){
        paused = !paused;
    }

    public void checkMousePos(){
        if(mouseX<640 && mouseY<680 && mouseY > 40){
            background.getTile(mouseX/32,(mouseY-40)/32).onHover(this);
    }
    }


    public void restart(){
    lost = false;
    won = false;
    paused = false;
    fastForward = false;
    mouseState.reset();
    setup();
    }

    //Run the code by current frame but do not draw (for testcases)
    public void loop(){
        checkMousePos();
        if(!paused){
            if(fastForward){
                frame += 2;
            }
            else{
                frame++;
            }
        }
        

        
        //Update values
        if(!lost){
            activeEnemies.update(background);
            if(frame % 60 == 0 || (frame%60 == 1 && fastForward)){addMana(manaPerSecond);}
            for(tower t : towers){
                t.update();
            }
            checkMousePos();
            loadGUI();
            manageWave();
    }
        if(lost){
            lose();        
        }
        else if(won){
            win();
        }
    }


    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        if(!paused){
            if(fastForward){
                frame += 2;
            }
            else{
                frame++;
            }
        }
        

        
        //Update values
        fill(132,114,74);
        stroke(132,114,74);
        rect(640,400,120,680);
        if(!lost){
            activeEnemies.update(background);
            if(frame % 60 == 0 || (frame%60 == 1 && fastForward)){addMana(manaPerSecond);}
            for(tower t : towers){
                t.update();
            }
            //Draw 
            background.draw(this);
            tower.drawBalls(this);
            activeEnemies.drawEnemies(this);
            background.drawHouse(this);
            checkMousePos();
            stroke(132,114,74);
            fill(132,114,74);
            rect(0,0,640,40);
            rect(640,0,120,400);
            loadGUI();
            manageWave();
    }
        if(lost){
            lose();        
        }
        else if(won){
            win();
        }
    }

    public void win(){
        strokeWeight(3);
        stroke(0);
        fill(255);
        rect(320,290,122,40);
        fill(0);
        textFont(textGUI,22);
        text("YOU WIN", 330, 320);
    }

    public void loadGUI(){
        textFont(textGUI, 14);
        fill(0);
        text("2x speed", 690, 65);
        text("PAUSE", 690, 120);
        text("Build tower", 690, 150, 55, 55);
        text("Upgrade range",690,200, 55, 55);
        text("Upgrade speed",690,250, 55, 55);
        text("Upgrade damage", 690, 300, 55, 55);
        text("Mana pool cost: "+Math.round(poolCost), 690, 350, 75, 80);
        textFont(textGUI,21);
        text("MANA:",320,28);
        fill(255,255,255);
        stroke(0);
        strokeWeight(3);
        rect(400,10,310,20);
        fill(0,255,255);
        rect(400,10,(mana/maxMana)*310 ,20);
        
        fill(0);
        text(Math.round(mana)+" / "+Math.round(maxMana),490,28);   
        for(button b : buttons){
            b.draw(this);
            b.checkHovering(mouseX, mouseY, this);
        }
        strokeWeight(1);
    }

    public void manageWave(){
        if(waves.size() > 0){
            if(waves.get(0).getEndFrame() <= frame){
                waves.remove(0);
            }
            else if(waves.get(0).getPrePauseFrames() <= frame){
                waves.get(0).runWave(activeEnemies);

            }
            if(waves.size()>1 && Math.round((waveTimerFrame - frame)/60) <=0){
                waveTimerFrame = waves.get(1).getPrePauseFrames();
                currentWave++;
            }


                fill(132,114,74);
                stroke(132,114,74);
                rect(0,0,320,40);
                fill(0);
                if(Math.round((waveTimerFrame - frame)/60) > 0){
                text("Wave " + str(currentWave+1) + " starts: "+ Math.round((waveTimerFrame - frame)/60), 10, 28);
                }
                else{
                    text("Wave " + str(currentWave+1), 10, 28);
                }
        }
        else if (activeEnemies.size() == 0){
            won = true;
        }
        else{
            text("Wave " + str(currentWave+1), 10, 28);
            }
    }

    public static float getMana(){
        return mana;
    }

    public static boolean getPaused(){
        return paused;
    }

    public static boolean getFastFoward(){
        return fastForward;
    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
