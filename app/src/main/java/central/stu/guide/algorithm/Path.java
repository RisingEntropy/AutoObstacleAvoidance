package central.stu.guide.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path implements Comparable<Path>{
    private ArrayList<Integer> path;
    private int length;
    public Path(){
        path = new ArrayList<>();
    }
    public Path(int start){
        path = new ArrayList<>();
        path.add(start);

    }
    public void setLength(int length){
        this.length = length;
    }
    public void add_point(int point){
        path.add(point);
    }
    public void add_point(int point,int length){
        this.length+=length;
        path.add(point);
    }
    public List<Integer> getPath(){
        return this.path;
    }
    public int getLength(){
        return this.length;
    }
    @Override
    public Object clone(){//返回当前路径的深拷贝
        Path path = new Path();
        for (Integer integer : this.path) {
            path.add_point(integer);
        }
        path.length = this.length;
        return path;
    }

    @Override
    public int compareTo(Path path) {
        return Integer.compare(this.length, path.getLength());//方便小根堆
    }
}
