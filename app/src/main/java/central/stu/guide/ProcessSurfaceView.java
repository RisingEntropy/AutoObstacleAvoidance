package central.stu.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import central.stu.guide.algorithm.ProcessPic;

public class ProcessSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mSurfaceHolder;
    //绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;
    public ProcessSurfaceView(Context context) {
        this(context, null);
    }

    public ProcessSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        //开启子线程
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        draw();
    }
    //绘图逻辑
    private void draw() {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.sample,opt).copy(Bitmap.Config.ARGB_8888,true);
        ProcessPic processor = new ProcessPic(bitmap);
        bitmap = processor.process();
        float scaleWidth = (float) (getWidth())/bitmap.getWidth();
        float scaleHeight = (float) (getHeight())/bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap newOne =  Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
        mCanvas = mSurfaceHolder.lockCanvas();
        mCanvas.drawBitmap(newOne,0,0,null);
        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    }
    private void initView(){
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }
}