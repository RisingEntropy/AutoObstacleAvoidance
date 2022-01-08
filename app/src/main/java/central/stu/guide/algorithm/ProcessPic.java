package central.stu.guide.algorithm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;
import java.util.Random;

public class ProcessPic {
    private Bitmap bitmap;
    private Graph graph;
    private int width,height;
    private Random random;//需要初始化引擎
    private int rowCnt,colCnt;//行单位数，列单位数
    private int startIdx,endIdx;
    public static final int baseWidth = 30;
    public static final int baseHeight = 30;//100X100作为单位判断
    public static final int groupPerTest = 30;//每个块取30个点
    public static final int groupTestPass = 25;//有28个点是路就过
    public ProcessPic(Bitmap bitmap){
        this.bitmap = bitmap;
        this.random = new Random(System.currentTimeMillis());
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.rowCnt = height/baseHeight;
        this.colCnt = width/baseWidth;
        this.graph = new Graph(rowCnt*colCnt+1);
        //建图

        for(int i = 1;i<rowCnt;i++){//四周不看
            for(int j = 2;j<colCnt;j++){
                int idx = i*colCnt+j;
                if(!judgeUniteValid(idx)){
                    continue;//这一块不是路，不建图
                }else{

                    if(judgeUniteValid(idx+1))graph.addEdge(idx,idx+1,1000);//右边
                    if(judgeUniteValid(idx-1))graph.addEdge(idx,idx-1,1000);//左边
                    if(judgeUniteValid(idx-colCnt)) graph.addEdge(idx,idx-colCnt,1);//上面块
                    if(judgeUniteValid(idx-colCnt-1)) graph.addEdge(idx,idx-colCnt-1,3);//左上面块
                    if(judgeUniteValid(idx-colCnt+1)) graph.addEdge(idx,idx-colCnt+1,3);//右上面块
                }
            }
        }

        //找起始点
        startIdx = (rowCnt-2)*colCnt+colCnt/2;
        boolean found = false;
        for(int i = rowCnt/2;i<rowCnt-1;i++){//四周不看
            for(int j = colCnt-1;j>=1;j--){//从上面开始找，从右边开始找
                int idx = i*colCnt+j;
                if(judgeUniteValid(idx)){
                    found = true;
                    endIdx = idx;
                    break;
                }
            }
            if(found)break;
        }
    }
    public Bitmap process(){
        Canvas canvas = new Canvas(this.bitmap);
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.startAlgorithm(startIdx);
        Path path = dijkstra.getPath(endIdx);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        if(path==null){
            Log.e("Process","null");
            return bitmap;
        }
        List<Integer> pathPoints = path.getPath();
        int last = pathPoints.get(0);
        for(int i = 1;i<pathPoints.size();i++){
            drawLineBetweenBlock(last,pathPoints.get(i),canvas,paint);
            last = pathPoints.get(i);
        }
        return bitmap;
    }
    private void drawLineBetweenBlock(int idx1,int idx2,Canvas canvas,Paint paint){
        int idx1X = (idx1%colCnt)*baseWidth+baseWidth/2;
        int idx1Y = (idx1/colCnt)*baseHeight+baseHeight/2;

        int idx2X = (idx2%colCnt)*baseWidth+baseWidth/2;
        int idx2Y = (idx2/colCnt)*baseHeight+baseHeight/2;

        canvas.drawLine(idx1X,idx1Y,idx2X,idx2Y,paint);
    }
    private boolean judgeUniteValid(int point){
        int startX = baseWidth*(point%colCnt);
        int startY = baseHeight*(point/colCnt);
        int valCnt = 0;

        for(int i = 0;i<groupPerTest;i++){
            int nx = startX+random.nextInt(Math.min(baseWidth,bitmap.getWidth()-startX)),ny = startY+random.nextInt(Math.min(baseHeight,bitmap.getHeight()-startY));

            int color = bitmap.getPixel(nx,ny);
            int red = (color & 0x00ff0000) >> 16;
            int green = (color & 0x0000ff00) >> 8; // 取中两位
            int blue = color & 0x000000ff; // 取低两位
            if(255-red<10){//允许红色有点偏差
                valCnt++;
            }
        }
        return valCnt >= groupTestPass;
    }
}
