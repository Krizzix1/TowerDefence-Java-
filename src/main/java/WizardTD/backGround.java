package WizardTD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import processing.core.PApplet;

public class backGround {

    wizardHouse house;

    public Tile[][] bgTiles = new Tile[20][20];
    
    public backGround(String levelFile){
        try{
            File fobj = new File(levelFile);
            Scanner scan = new Scanner(fobj);
            int row = 0;
            int column = 0;
            while (scan.hasNextLine()){
                String data = scan.nextLine();
                column = 0;
                for(int i=0;i < 20;i++){
                    try{
                    if(data.charAt(i) == ' '){
                        bgTiles[row][column] = new grass((column)*32,(row)*32+40);
                    }
                    else if(data.charAt(i) == 'S'){
                        bgTiles[row][column] = new shrub((column)*32,(row)*32+40);
                    }
                    else if(data.charAt(i) == 'X'){
                        bgTiles[row][column] = new path((column)*32,(row)*32+40);
                    }
                    else if(data.charAt(i) == 'W'){
                        bgTiles[row][column] = new path((column)*32, (row)*32+40);
                        this.house = new wizardHouse(column*32, row*32+35);
                    }
                    } catch(StringIndexOutOfBoundsException e){bgTiles[row][column] = new grass((column)*32,(row)*32+40);column++;continue;}
                    column++;
                }
                row++;
            }
            scan.close();
        } catch(FileNotFoundException e){
            System.out.println("File was not found");
        }
        BFS.run(house,this); 
        setPaths();
    }

    public Tile getHouse(){
        return this.house;
    }

    public void setPaths(){
        for(int row = 1;row < bgTiles.length-1; row++){
            for(int col = 1; col < bgTiles[row].length-1; col++){
                if(bgTiles[row][col] instanceof path){
                    if(bgTiles[row-1][col] instanceof path && bgTiles[row][col-1] instanceof path && bgTiles[row][col+1] instanceof path && bgTiles[row+1][col] instanceof path){
                        bgTiles[row][col].changeType(3);
                    }
                    else if(bgTiles[row-1][col] instanceof path && bgTiles[row][col-1] instanceof path && bgTiles[row][col+1] instanceof path){
                        bgTiles[row][col].changeType(9);
                    }
                    else if(bgTiles[row][col-1] instanceof path && bgTiles[row][col+1] instanceof path && bgTiles[row+1][col] instanceof path){
                        bgTiles[row][col].changeType(2);        
                    }
                    else if(bgTiles[row-1][col] instanceof path && bgTiles[row][col-1] instanceof path && bgTiles[row+1][col] instanceof path){
                        bgTiles[row][col].changeType(8);
                    }
                    else if(bgTiles[row-1][col] instanceof path && bgTiles[row][col+1] instanceof path && bgTiles[row+1][col] instanceof path){
                        bgTiles[row][col].changeType(10);
                    }
                    else if(bgTiles[row+1][col] instanceof path && bgTiles[row][col-1] instanceof path){
                        bgTiles[row][col].changeType(1);
                    }
                    else if(bgTiles[row-1][col] instanceof path && bgTiles[row][col-1] instanceof path){
                        bgTiles[row][col].changeType(5);
                    }
                    else if(bgTiles[row-1][col] instanceof path && bgTiles[row][col+1] instanceof path){
                        bgTiles[row][col].changeType(6);
                    }
                    else if(bgTiles[row+1][col] instanceof path && bgTiles[row][col+1] instanceof path){
                        bgTiles[row][col].changeType(7);
                    }
                    else if(bgTiles[row-1][col] instanceof path || bgTiles[row+1][col] instanceof path){
                        bgTiles[row][col].changeType(4);
                    }
                }
            }
        }
        for(int col = 0; col<bgTiles.length; col++){
            if (bgTiles[0][col] instanceof path){
                if(col == 0){
                    if(bgTiles[1][0] instanceof path && bgTiles[0][1] instanceof path){bgTiles[0][0].changeType(7);}
                }
                else if (col == bgTiles.length-1){
                    if(bgTiles[1][col] instanceof path && bgTiles[0][col-1] instanceof path){bgTiles[0][col].changeType(1);}
                }
                else if(bgTiles[0][col-1] instanceof path && bgTiles[0][col+1] instanceof path && bgTiles[1][col] instanceof path){
                            bgTiles[0][col].changeType(2);        
                }
                else if(bgTiles[1][col] instanceof path && bgTiles[0][col-1] instanceof path){
                    bgTiles[0][col].changeType(1);
                } 
                else if(bgTiles[1][col] instanceof path && bgTiles[1][col+1] instanceof path){
                    bgTiles[0][col].changeType(7);
                } 
                else if(bgTiles[1][col] instanceof path){
                    bgTiles[0][col].changeType(4);
                }

        }
    }
        for(int col = 0; col<bgTiles.length; col++){
            if (bgTiles[bgTiles[col].length-1][col] instanceof path){
                if(col == 0){
                    if(bgTiles[bgTiles[col].length-1][col +1] instanceof path && bgTiles[bgTiles[col].length-2][col] instanceof path){bgTiles[bgTiles[col].length-1][0].changeType(6);}
                }
                else if (col == bgTiles.length-1){
                    if(bgTiles[bgTiles[col].length-1][col -1] instanceof path && bgTiles[bgTiles[col].length-2][col] instanceof path){bgTiles[bgTiles[col].length-1][col].changeType(5);}
                }
                else if(bgTiles[bgTiles[col].length-1][col-1] instanceof path && bgTiles[bgTiles[col].length-1][col+1] instanceof path && bgTiles[bgTiles[col].length-2][col] instanceof path){
                            bgTiles[bgTiles[col].length-1][col].changeType(9);        
                }
                else if(bgTiles[bgTiles[col].length-2][col] instanceof path && bgTiles[bgTiles[col].length-1][col-1] instanceof path){
                    bgTiles[bgTiles[col].length-1][col].changeType(5);
                } 
                else if(bgTiles[bgTiles[col].length-2][col] instanceof path && bgTiles[bgTiles[col].length-1][col+1] instanceof path){
                    bgTiles[bgTiles[col].length-1][col].changeType(6);
                } 
                else if(bgTiles[bgTiles[col].length-2][col] instanceof path){
                    bgTiles[bgTiles[col].length-1][col].changeType(4);
                }

        }
    }

        for(int row = 1; row<bgTiles.length-1; row++){
            if (bgTiles[row][0] instanceof path){
                if(bgTiles[row-1][0] instanceof path && bgTiles[row+1][0] instanceof path && bgTiles[row][1] instanceof path){
                            bgTiles[row][0].changeType(10);        
                }
                else if(bgTiles[row-1][0] instanceof path && bgTiles[row][1] instanceof path){bgTiles[row][0].changeType(6);}
                else if(bgTiles[row+1][0] instanceof path && bgTiles[row][1] instanceof path){bgTiles[row][0].changeType(7);}
                else if(bgTiles[row-1][0] instanceof path || bgTiles[row+1][0] instanceof path){bgTiles[row][0].changeType(4);}
            }
            if (bgTiles[row][bgTiles.length-1] instanceof path){
                if(bgTiles[row-1][bgTiles.length-1] instanceof path && bgTiles[row+1][bgTiles.length-1] instanceof path && bgTiles[row][bgTiles.length-2] instanceof path){
                    bgTiles[row][bgTiles.length-1].changeType(8);   
                } 
                else if(bgTiles[row-1][bgTiles.length-1] instanceof path && bgTiles[row][bgTiles.length-2] instanceof path){bgTiles[row][bgTiles.length-1].changeType(5);}
                else if(bgTiles[row+1][bgTiles.length-1] instanceof path && bgTiles[row][bgTiles.length-2] instanceof path){bgTiles[row][bgTiles.length-1].changeType(1);}
                else if(bgTiles[row-1][bgTiles.length-1] instanceof path || bgTiles[row+1][bgTiles.length-1] instanceof path){bgTiles[row][bgTiles.length-1].changeType(4);}
        }
    }

    }

    public Tile getTile(int xCell, int yCell){
        return bgTiles[yCell][xCell].getTile();
    }

    public void setTile(Tile tile, int xCell, int yCell){
        bgTiles[yCell][xCell] = tile;
    }

    public int getLength(){
        return bgTiles.length;
    }

    public int getHeight(){
        return bgTiles[0].length;
    }


    public void draw(PApplet app){
        for(int row = 0;row < bgTiles.length; row++){
            for(int col = 0; col < bgTiles[row].length; col++){
                bgTiles[row][col].draw(app);
            }
        }
    }

    public void drawHouse(PApplet app){
        this.house.draw(app);
    }

    public List<Integer> findPath(){
        List<Integer> path = new ArrayList<Integer>();
        

        return path;
    }

}
