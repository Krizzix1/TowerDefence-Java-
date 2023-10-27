package WizardTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class wave {

    private List<Enemy> enemyToSpawn = new ArrayList<Enemy>();
    private static List<Tile> spawns = new ArrayList<Tile>();
    private static int index=0;

    private float prePause;
    private float duration;
    private int quantity;
    private int framesForSpawn;
    private int enemyIndex = 0;
    public int framesToRun;
    public static int activeWave=0;


    public wave(JSONObject jobj, PApplet app){
        this.duration = jobj.getFloat("duration");
        this.prePause = jobj.getInt("pre_wave_pause");
        JSONArray jArray = jobj.getJSONArray("monsters");
        for(int i=0;i<jArray.size();i++){
            JSONObject enemyInfo = jArray.getJSONObject(i);
            quantity += enemyInfo.getInt("quantity");
            Random ran = new Random();
            for(int j=0;j<enemyInfo.getInt("quantity");j++){
                Tile Spawnpoint = spawns.get(ran.nextInt(100000) % spawns.size());
                enemyToSpawn.add(new Enemy(index, Spawnpoint, app.loadImage("src/main/resources/WizardTD/"+enemyInfo.getString("type")+".png"), enemyInfo.getInt("hp"), enemyInfo.getFloat("speed"),enemyInfo.getFloat("armour"),enemyInfo.getInt("mana_gained_on_kill")));
                index ++;
            }
        }
        framesForSpawn = Math.round((this.duration/this.quantity)*App.FPS);
        framesToRun = Math.round(prePause*60);
        

    }



    public void runWave(EnemyCollection enemyCollection){
        if(enemyIndex < enemyToSpawn.size() && (App.frame % framesForSpawn == 0) || (App.frame % framesForSpawn == 1 && App.getFastFoward())){
            enemyCollection.genEnemy(enemyToSpawn.get(enemyIndex));
            enemyIndex++;

        }
    }

    public float getDuration(){
        return this.duration;
    }
    public int getFramesToRun(){
        return framesToRun;
    }

    public float getPrePause(){
        return this.prePause;
    }

    public int getPrePauseFrames(){
        return framesToRun;
    }



    public int getEndFrame(){
        return Math.round((this.prePause + this.duration)*60);
    }

    public void addPrePause(float toAdd){
        this.prePause += toAdd;
        this.framesToRun = Math.round(prePause*60);
    }

    public static void setSpawnPoints(backGround bg){
        for(int i =0;i<20; i++){
            if(bg.getTile(i,0).getDistance() != -1 && bg.getTile(i,0).getType().equals("path") && bg.getTile(i,0).getKey() == 4){spawns.add(bg.getTile(i,0));}
            if(bg.getTile(i,19).getDistance() != -1 && bg.getTile(i,19).getType().equals("path") && bg.getTile(i,19).getKey() == 4){spawns.add(bg.getTile(i,19));}
            if(bg.getTile(0,i).getDistance() != -1 && bg.getTile(0,i).getType().equals("path") && bg.getTile(0,i).getKey() == 0){spawns.add(bg.getTile(0,i));}
            if(bg.getTile(19,i).getDistance() != -1 && bg.getTile(19,i).getType().equals("path") && bg.getTile(19,i).getKey() == 0){spawns.add(bg.getTile(19,i));}
        }
    }
    public void update(){

    }

}
