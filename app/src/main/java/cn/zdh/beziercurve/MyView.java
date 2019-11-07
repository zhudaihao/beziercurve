package cn.zdh.beziercurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyView extends View {
    private Paint mPaint;
    private Path mPath;
    private List<PointF> mControlPoints;
    private BezierCanvasUtil bezierCanvasUtil;


    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPath = new Path();
        mControlPoints = new ArrayList<>();

        addPointF();
        //计算控制点辅助类
        bezierCanvasUtil = new BezierCanvasUtil(mControlPoints, mPath);
        //调用计算控制点的方法
        bezierCanvasUtil.buildBezierPoints();
    }

    /**
     * 采集贝塞尔曲线上点（采集越多绘制越精准）
     */
    private void addPointF() {
        PointF pointF1 = new PointF(50, 150);
        PointF pointF2 = new PointF(100, 125);
        PointF pointF3 = new PointF(150, 140);
        PointF pointF4 = new PointF(150, 175);
        PointF pointF5 = new PointF(125, 200);
        PointF pointF6 = new PointF(100, 200);
        PointF pointF7 = new PointF(100, 220);
        PointF pointF8 = new PointF(150, 230);
        PointF pointF9 = new PointF(180, 250);
        PointF pointF10 = new PointF(200, 275);

        PointF pointF11 = new PointF(170, 300);
        PointF pointF12 = new PointF(150, 320);
        PointF pointF13 = new PointF(100, 325);
        PointF pointF14 = new PointF(75, 300);
        PointF pointF15 = new PointF(70, 270);
        PointF pointF16 = new PointF(25, 250);
        PointF pointF17 = new PointF(25, 220);
        PointF pointF18 = new PointF(28, 200);
        //保存点到集合里面
        mControlPoints.add(pointF1);
        mControlPoints.add(pointF2);
        mControlPoints.add(pointF3);
        mControlPoints.add(pointF4);
        mControlPoints.add(pointF5);
        mControlPoints.add(pointF6);
        mControlPoints.add(pointF7);
        mControlPoints.add(pointF8);
        mControlPoints.add(pointF9);
        mControlPoints.add(pointF10);

        mControlPoints.add(pointF11);
        mControlPoints.add(pointF12);
        mControlPoints.add(pointF13);
        mControlPoints.add(pointF14);
        mControlPoints.add(pointF15);
        mControlPoints.add(pointF16);
        mControlPoints.add(pointF17);
        mControlPoints.add(pointF18);


    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制
        canvas.drawPath(mPath, mPaint);
    }
}
