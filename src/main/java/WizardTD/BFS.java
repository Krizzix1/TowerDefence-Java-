package WizardTD;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private static Queue<Tile> que;

    public BFS(){
        
    }

    public static void run(Tile dest, backGround bg){
        que = new LinkedList<Tile>();
        boolean[][] visited = new boolean[bg.getLength()][bg.getHeight()];
        Tile n;
        int repeated;
        que.add(dest);
        int distance = 0;
        int skip = 0;
        bg.getTile(dest.getXCell(),dest.getYCell()).setDistance(0);
        while(que.size()!=0){
            n = que.poll();
            try{
            if(visited[n.getXCell()][n.getYCell()]){
                continue;
            }
            n.setDistance(distance);
            if(skip == 0){distance += 1;}
            else{skip --;}
            repeated = 0;
            if (n.getXCell() > 0 && !visited[n.getXCell()-1][n.getYCell()] && bg.getTile(n.getXCell()-1, n.getYCell()).getType().equals("path")){
                que.add(bg.getTile(n.getXCell()-1, n.getYCell()));
                repeated += 1;
            }
            if (n.getYCell() > 0 && !visited[n.getXCell()][n.getYCell()-1] && bg.getTile(n.getXCell(), n.getYCell()-1).getType().equals("path")){
                que.add(bg.getTile(n.getXCell(), n.getYCell()-1));
                repeated += 1;
            }
            if (n.getXCell() < 19 && !visited[n.getXCell()+1][n.getYCell()] && bg.getTile(n.getXCell()+1, n.getYCell()).getType().equals("path")){
                que.add(bg.getTile(n.getXCell()+1, n.getYCell()));
                repeated += 1;
            }
            if (n.getYCell() < 19 && !visited[n.getXCell()][n.getYCell()+1] && bg.getTile(n.getXCell(), n.getYCell()+1).getType().equals("path")){
                que.add(bg.getTile(n.getXCell(), n.getYCell()+1));
                repeated += 1;
            }
            else{repeated = 0;}
            visited[n.getXCell()][n.getYCell()] = true;
            if(repeated > 1){skip += repeated-1;}
        }catch(ArrayIndexOutOfBoundsException e){continue;}
    }
}
}
