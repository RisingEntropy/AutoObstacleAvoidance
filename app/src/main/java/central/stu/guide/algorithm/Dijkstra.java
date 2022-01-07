package central.stu.guide.algorithm;

import android.util.Log;

import java.util.PriorityQueue;

public class Dijkstra {
    private class Node implements Comparable<Node>{
        private int curr;
        private Path path;
        Node(int curr,Path path){
            this.curr = curr;
            this.path = path;
        }
        @Override
        public int compareTo(Node node) {
            return path.compareTo(node.path);
        }
        public int getCurr(){return this.curr;}
        public Path getPath(){return this.path;}
    }
    private PriorityQueue<Node> queue;
    private Graph graph;
    private Path[] paths;
    private boolean[] vis;
    private boolean hasRun = false;
    public Dijkstra(Graph graph){
        this.graph = graph;
        this.paths = new Path[graph.getPSize()+1];
        this.vis = new boolean[graph.getPSize()+1];
    }
    public void startAlgorithm(int startPoint){
        for(boolean x:vis) x = false;
        if(this.queue==null)this.queue = new PriorityQueue<Node>();
        else this.queue.clear();
        for(int i = 0;i<paths.length;i++){
            paths[i] = new Path(i);
            paths[i].setLength(0x7f7f7f7f);
        }
        paths[startPoint] = new Path(startPoint);
        Node start = new Node(startPoint,paths[startPoint]);
        queue.add(start);
        vis[startPoint] = true;
        while(!queue.isEmpty()){
            Node top = queue.peek();
            queue.poll();
            int currPoint = top.getCurr();//一定不为null
            vis[currPoint] = false;
            Log.e("Dijkstra",String.format("point:%d dis:%d ",currPoint,paths[currPoint].getLength()));
            for(int i = graph.getHead(currPoint);i!=0;i = graph.getEdge(i).getNext()){
                Edge edge = graph.getEdge(i);

                int to = edge.getTo();
                if(vis[to])continue;
                vis[to] = true;
                int totWei = top.getPath().getLength()+edge.getWeigh();
                if(totWei<paths[to].getLength()){
                    Path path = (Path) top.getPath().clone();
                    path.add_point(to, edge.getWeigh());
                    Node nxt = new Node(to,path);
                    paths[to] = path;
                    queue.add(nxt);
                }
            }
        }
        hasRun = true;
    }
    public Path getPath(int point){
        if(!hasRun||point>=graph.getPSize()){
            Log.e("Dijkstra","point error");
            return null;
        }
        return paths[point];
    }
}
