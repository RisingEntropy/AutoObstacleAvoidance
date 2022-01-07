package central.stu.guide.algorithm;

import android.util.Log;

import java.util.ArrayList;

public class Graph {
    private int PSize = 0;
    private int top;
    private ArrayList<Edge> edges;
    private int[] head;
    public Graph(int point){
        this.PSize = point;
        this.head = new int[point+1];
        edges = new ArrayList<>();
    }
    public void addEdge(int f,int t,int w){

        edges.add(new Edge(f,t,w,head[f]));
        head[f] = edges.size();//边的编号从1开始

    }
    public Edge getEdge(int index){
        if(index>edges.size())return new Edge(0,0,0,0);
        return edges.get(index-1);
    }
    public int getPSize(){
        return this.PSize;
    }
    public int getHead(int point){
        if(point>=head.length)return 0;//出错了
        return this.head[point];
    }
}
