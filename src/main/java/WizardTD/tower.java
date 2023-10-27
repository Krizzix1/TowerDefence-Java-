package WizardTD;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class tower extends Tile{

    private int rangeLevel=0;
    private int speedLevel=0;
    private int damageLevel=0;
    private static int icost;
    private static int idamage;
    private static int irange;
    private static float ispeed;
    private int damage;
    private int range;
    private float speed;
    private PImage sprite;
    private String type = "tower";
    private int upgradeEffectAdjust=0;
    private static Map<Integer, PImage> towers = new HashMap<Integer, PImage>();
    private static List<fireball> balls = new ArrayList<fireball>();
    private static List<fireball> toRemove = new ArrayList<fireball>();
    private EnemyCollection enemies;
    private PFont textGUI;

    public tower(int x, int y, boolean[] upgrades, EnemyCollection enemies){
        super(x*32, y*32+40);
        this.enemies = enemies;
        this.damage = idamage;
        this.range = irange;
        this.speed = ispeed;

        if(upgrades[0]){this.rangeLevel = 1;}
        if(upgrades[1]){this.speedLevel = 1;}
        if(upgrades[2]){this.damageLevel = 1;}

        upgradeSprite();
        updateUpgrades();



    }

    public void updateUpgrades(){
        range = irange + rangeLevel*32;
        speed = ispeed + speedLevel/2;
        damage = idamage + damageLevel*idamage/2;
    }

    public void upgrade(boolean[] upgrades){
        if(upgrades[0] && App.getMana() >= 20 + rangeLevel*10){
            App.addMana(-(20+rangeLevel*10));
            rangeLevel ++;
            range = irange + rangeLevel*32;
            upgradeSprite();
        }
        if(upgrades[1] && App.getMana() >= 20 + speedLevel*10){
            App.addMana(-(20+speedLevel*10));
            speedLevel++;
            speed = ispeed + speedLevel/2;
            upgradeSprite();
        }
        if(upgrades[2] && App.getMana() >= 20 + damageLevel*10){
            App.addMana(-(20+damageLevel*10));
            damageLevel++;
            damage = idamage + damageLevel*idamage/2;
            upgradeSprite();
        }
    }

    public static void setiCost(int Cost){
        icost = Cost;
    }
    public static void setiRange(int Range){
        irange = Range;
    }
    public static void setiDamage(int Damage){
        idamage = Damage;
    }
    public static void setiSpeed(float Speed){
        ispeed = Speed;
    }

    public static void setSprite(PImage sprite, Integer key){
        towers.put(key,sprite);
    }

    public void addDamage(int add){
        this.damage += add;
    }
    public void addRange(int add){
        this.range += add;
    }
    public void addSpeed(float add){
        this.speed += add;
    }

    public tower getTile(){
        return this;
    }

    public static int getCost(){
        return icost;
    }

    public void changeTower(int key){
        this.sprite = towers.get(key);
    }

    public String getType(){
        return type;
    }

    public void onHover(PApplet app){
        String text;
        int upBoxHeight;
        int totalUpCost;
        app.fill(255,255,0,0);
        app.stroke(255,255,0);
        app.ellipse(x+16,y+16,range*2,range*2);
        app.stroke(0);
        if(mouseState.getU1Mode() || mouseState.getU2Mode() || mouseState.getU3Mode()){
            textGUI = app.createFont("Seaford",24,true);
            app.textFont(textGUI, 14);
            app.fill(255,255,255);
            app.rect(650, 500, 100,20);
            if(mouseState.getU1Mode() && mouseState.getU2Mode() && mouseState.getU3Mode()){
                text = "range:      " + (20+rangeLevel*10) +"\nspeed:     " + (20+speedLevel*10) +"\ndamage:   " + (20+damageLevel*10);
                upBoxHeight = 70;
                totalUpCost = 60+rangeLevel*10 + damageLevel*10+ speedLevel*10;
            }
            else if(mouseState.getU1Mode() && mouseState.getU2Mode()){
                text = "range:      " + (20+rangeLevel*10) +"\nspeed:     " + (20+speedLevel*10);
                upBoxHeight = 45;
                totalUpCost = 60+rangeLevel*10 +  speedLevel*10;
            }
            else if(mouseState.getU2Mode() && mouseState.getU3Mode()){
                text = "speed:     " + (20+speedLevel*10) +"\ndamage:   " + (20+damageLevel*10);
                upBoxHeight = 45;
                totalUpCost = 60+ damageLevel*10+ speedLevel*10;
            }
            else if(mouseState.getU1Mode() && mouseState.getU3Mode()){
                text = "range:      " + (20+rangeLevel*10) +"\ndamage:   " + (20+damageLevel*10);
                upBoxHeight = 45;
                totalUpCost = 60+rangeLevel*10 + damageLevel*10;
            }
            else if(mouseState.getU1Mode()){
                text = "range:      " + (20+rangeLevel*10); upBoxHeight = 20;totalUpCost = 20 + rangeLevel*10;
            }
            else if(mouseState.getU2Mode()){
                text = "speed:     " + (20+speedLevel*10); upBoxHeight = 20;totalUpCost = 20 + speedLevel*10;
            }
                
            else{text = "damage:   " + (20+damageLevel*10); upBoxHeight = 20;totalUpCost = 20 + damageLevel*10;}


        app.rect(650, 520, 100,upBoxHeight);
        app.rect(650,520+upBoxHeight, 100,20);
        app.fill(0);
        app.text("Total:        " + totalUpCost,650,535+upBoxHeight);
        app.text("Upgrade cost", 652,515);
        app.text(text,652, 535);
        

        }

        
    };

    public void update(){
        for(fireball ball : balls){
            ball.TICK();
            if(ball.getTarget().isDead()){
                toRemove.add(ball);
            }
            else if(Math.abs(ball.getTarget().getY() - ball.getY()) < 2 && Math.abs(ball.getTarget().getX() - ball.getX()) < 2 ){
                ball.getTarget().takeDamage(damage);
                toRemove.add(ball);
            }
        }
        balls.removeAll(toRemove);
        toRemove.clear();
        if(App.frame % Math.round((1/speed)*App.FPS) == 0 || (App.getFastFoward() && App.frame % Math.round((1/speed)*App.FPS) == 1)){
            for(int i =0;i<enemies.size();i++){
                if(Math.sqrt(Math.pow(enemies.getEnemy(i).getX()-x,2) + Math.pow(enemies.getEnemy(i).getY()-y,2)) <= range){
                    balls.add(new fireball(this.x +16, this.y+16, enemies.getEnemy(i)));
                    break;
                } 
            }
        }

    }

    public static void removeBall(fireball ball){
        toRemove.add(ball);
    }

    public static void drawBalls(PApplet app){
        for(fireball ball : balls){
            ball.draw(app);
        }
    }

    public void upgradeSprite(){
        if(speedLevel > 1 && rangeLevel >1 && damageLevel >1){
            this.changeTower(2);
            upgradeEffectAdjust = 2;
        }
        else if(speedLevel > 0 && rangeLevel > 0 && damageLevel > 0){
            this.changeTower(1);
            upgradeEffectAdjust = 1;
        }
        else{
            this.changeTower(0);
        }
    }

    public void draw(PApplet app){
        app.image(sprite,this.x,this.y);
        app.fill(0,0,0,0);
        app.stroke(0,171,240);
        app.strokeWeight(speedLevel - upgradeEffectAdjust);
        app.rect(x+5,y+6,21,21);
        app.stroke(200,0,200);
        app.strokeWeight(2);
        for(int i=0;i<rangeLevel-upgradeEffectAdjust;i++){
        app.ellipse(x+2+i*8,y+1,7,7);
        }
        app.fill(200,0,200);
        app.textFont(App.textGUI,17);
        for(int i=0;i<damageLevel-upgradeEffectAdjust;i++){
        
        app.text("x",x+i*7,y+30);
        }
        app.strokeWeight(1);
        app.stroke(0);


    }
}
