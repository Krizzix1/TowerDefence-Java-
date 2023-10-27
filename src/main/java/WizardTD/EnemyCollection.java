package WizardTD;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class EnemyCollection {
    private List<Enemy> enemies;
    private static List<Enemy> toRemove;
    private static List<Enemy> toAdd;

    public EnemyCollection(){
        enemies = new ArrayList<Enemy>();
        toRemove = new ArrayList<Enemy>();
        toAdd = new ArrayList<Enemy>();
    }



    public void genEnemy(Enemy e){
        enemies.add(e);
    }

    public Enemy getEnemy(int i){
        return enemies.get(i);
    }

    
    public void update(backGround bg){
   
        for(Enemy i : enemies){
            i.TICK(bg);
            if(i.getXCell() == bg.getHouse().getXCell() && i.getYCell() == bg.getHouse().getYCell()){
                toRemove.add(i);
                enemyAtWiz(i);
                i.die();
                Enemy respawned = new Enemy(i.getIndex(),i.getSpawn(),i.getSprite(),i.getMaxHp(),i.getSpeed(),i.getArmour(),i.getManaKill());
                respawned.setHealth(i.getHp());
                toAdd.add(respawned);
            }
        }
        for(Enemy e: toAdd){
            genEnemy(e);
        }
        toAdd.clear();
        enemies.removeAll(toRemove);
        toRemove.clear();
    }

    public void enemyAtWiz(Enemy e){
        App.addMana(-e.getHp());
    }

    public void drawEnemies(PApplet app){
        for(Enemy i : enemies){
            i.draw(app);
        }
    }
    public int size(){
        return enemies.size();
    }

    public static void delEnemy(Enemy e){
        toRemove.add(e);
    }
}
