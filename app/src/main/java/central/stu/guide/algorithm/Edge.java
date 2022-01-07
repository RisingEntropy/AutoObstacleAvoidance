package central.stu.guide.algorithm;

public class Edge {
    private int from,to,weigh,next;
    public Edge(int from,int to,int weigh,int next){
        this.from = from;
        this.to = to;
        this.weigh = weigh;
        this.next = next;
    }
    public int getFrom(){return this.from;}
    public int getTo(){return this.to;}
    public int getWeigh(){return this.weigh;}
    public int getNext(){return this.next;}
    @Override
    public Object clone(){
        return new Edge(this.from,this.to,this.weigh,this.next);
    }
}
