package WizardTD;
import java.util.Map;


import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class Enemy {
    private float x;
    private float y;
    private Tile firstTile;
    private int[] direction = new int[2];
    private PImage sprite;
    private int xCell;
    private int yCell;
    private float xTravelled= 0;
    private float yTravelled= 0;
    private int index;
    private float hp;
    private float armour;
    private int mana_gained_on_kill;
    private float speed;
    private float maxHp;
    private boolean isDead = false;
    private float ispeed;
    private int despawnTimerFrame;
    private static Map<Integer, PImage> despawnSprites = new HashMap<Integer, PImage>();
    private int count=0;
    private boolean initialL = false;
    private boolean initialT = false;
    private boolean initialR = false;



    public Enemy(int index, Tile firstTile, PImage type, float maxHp, float speed, float armour, int mana_gained_on_kill){
        this.index = index;
        this.x = firstTile.getXCell() * 32+ 7;
        this.y = firstTile.getYCell()* 32 + 40;
        this.firstTile = firstTile;
        this.sprite = type;
        this.xCell = firstTile.getXCell();
        this.yCell = firstTile.getYCell();
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;
        this.speed = speed;
        this.ispeed = speed;
        if(firstTile.getKey() == 0 && firstTile.getXCell() == 0){this.x -= 32*speed; initialL = true;}
        else if(firstTile.getKey() == 0 && firstTile.getXCell() == 19){this.x += 32*speed; initialR = true;}
        else if(firstTile.getKey() == 4 && firstTile.getYCell() == 0){this.y -= 32*speed; initialT = true;}
        else if(firstTile.getKey() == 4 && firstTile.getYCell() == 19){this.y += 32*speed;}
    }

    public static void setDespawnSprite(PImage sprite, int key){
        despawnSprites.put(key,sprite);
    }

    public void TICK(backGround bg){
        if(App.getPaused()){return;}
        if(App.getFastFoward()){speed = ispeed*2;}
        else{speed = ispeed;}
        if (count < 32){
            if(initialL){this.x += speed;}
            else if(initialR){this.x -= speed;}
            else if(initialT){this.y += speed;}
            else{this.y -= speed;}
            if(App.getFastFoward()){
                count += 2;
            }
            else{count ++;}
        }

        else{


        if(xTravelled >= 32){xTravelled = 0; xCell ++;}
        else if(xTravelled <= -32){xTravelled = 0; xCell --;}
        else if(yTravelled >= 32){yTravelled = 0; yCell ++;}
        else if(yTravelled <= -32){yTravelled = 0; yCell --;}

        if(xCell > 0 && bg.getTile(xCell-1,yCell).getDistance() >= 0 && bg.getTile(xCell-1,yCell).getDistance() < bg.getTile(xCell, yCell).getDistance()){
            direction[0] = -1;
            direction[1] = 0;
        }
        else if(xCell < 19 && bg.getTile(xCell+1,yCell).getDistance() >= 0 &&  bg.getTile(xCell+1,yCell).getDistance() < bg.getTile(xCell, yCell).getDistance()){
            direction[0] = 1;
            direction[1] = 0;
        }
        else if(yCell > 0 && bg.getTile(xCell,yCell-1).getDistance() >= 0 && bg.getTile(xCell,yCell-1).getDistance() < bg.getTile(xCell, yCell).getDistance()){
            direction[0] = 0;
            direction[1] = -1;
        }
        else if(yCell < 19 && bg.getTile(xCell,yCell+1).getDistance() >= 0 && bg.getTile(xCell,yCell+1).getDistance() < bg.getTile(xCell, yCell).getDistance()){
            direction[0] = 0;
            direction[1] = 1;
        }
        

        if(xTravelled + direction[0]*speed > 32){
            this.x += direction[0]*speed - (xTravelled + direction[0]*speed - 32);
            xTravelled += direction[0]*speed - (xTravelled + direction[0]*speed - 32);
        }
        else if(xTravelled + direction[0]*speed < -32){
            this.x += direction[0]*speed - (xTravelled + direction[0]*speed + 32);
            xTravelled += direction[0]*speed  - (xTravelled + direction[0]*speed + 32);
        }
        else{this.x += direction[0]*speed; xTravelled += direction[0]*speed;}
        if(yTravelled+ direction[1]*speed > 32){
            this.y += direction[1]*speed - (yTravelled + direction[1]*speed - 32);
            yTravelled += direction[1]*speed - (yTravelled + direction[1]*speed - 32);
        }
        else if(yTravelled + direction[1]*speed < -32){
            this.y += direction[1]*speed - (yTravelled + direction[1]*speed + 32);
            yTravelled += direction[1]*speed - (yTravelled + direction[1]*speed + 32);
        }
        else{this.y += direction[1]*speed;  yTravelled += direction[1]*speed;}
        

        }

    }

    public void takeDamage(int amount){
        if(hp - amount <= 0){
            if(!isDead){App.addMana(mana_gained_on_kill);}
            isDead = true;
        }
        else{hp -= amount;}
    }

    public void despawn(){
        if(App.getPaused()){return;}
        else if(App.getFastFoward()){despawnTimerFrame+=2;}
        else{despawnTimerFrame++;}
        if (despawnTimerFrame >= 23){EnemyCollection.delEnemy(this);}
        else if(despawnTimerFrame >= 20){sprite = despawnSprites.get(4);}
        else if(despawnTimerFrame >= 16){sprite = despawnSprites.get(3);}
        else if(despawnTimerFrame >= 12){sprite = despawnSprites.get(2);}
        else if(despawnTimerFrame >= 8){sprite = despawnSprites.get(1);}
        else if(despawnTimerFrame >= 4){sprite = despawnSprites.get(0);}
        
    }

    public boolean isDead(){
        return isDead;
    }

    public void setHealth(float hp){
        this.hp = hp;
    }

    public void die(){
        this.isDead = true;
    }

    public PImage getSprite(){
        return sprite;
    }
    public float getArmour(){
        return armour;
    }
    public float getSpeed(){
        return ispeed;
    }
    public Tile getSpawn(){
        return firstTile;
    }
    public int getManaKill(){
        return mana_gained_on_kill;
    }
    public float getMaxHp(){
        return maxHp;
    }

    public int getYCell(){
        return yCell;
    }

    public int getXCell(){
        return xCell;
    }

    public int getIndex(){
        return index;
    }
    public int getHp(){
        return Math.round(hp);
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void draw(PApplet app){
        if(isDead){
            despawn();
        }
        app.stroke(0);
        app.image(sprite, x,y);
        app.fill(255,0,0);
        app.rect(x-8,y-11,35,4);
        app.fill(0,255,0);
        app.rect(x-8,y-11,Math.round(((hp/maxHp)*35)) ,4);
    }
}
