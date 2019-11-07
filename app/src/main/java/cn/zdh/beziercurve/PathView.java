package cn.zdh.beziercurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class PathView extends View {
    private Path path, path1, path2;
    private Paint paint;

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        path = new Path();
        path1 = new Path();
        path2 = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);


    }


    private int tag = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        test1(canvas);
//        test2(canvas);
//        test3(canvas);
//        test4(canvas);


        //三阶贝塞尔曲线
        path1.moveTo(100,100);

        //参数（第一个弧形控制X，第一个弧形控制点Y，，第二个弧形控制X，第二个弧形控制点Y，终点X，终点Y）
        path1.cubicTo(400,200,10,500,300,700);

        //绘制 贝塞尔曲线 启点 控制点 控制点， 终点
        canvas.drawCircle(100,100,10,paint);
        canvas.drawCircle(400,200,10,paint);
        canvas.drawCircle(10,500,10,paint);
        canvas.drawCircle(300,700,10,paint);


        canvas.drawPath(path1,paint);

    }

    private void test4(Canvas canvas) {
        //二阶 贝塞尔曲线
        //参数（控制点X，控制点Y，终点想，终点Y）起点还是0，0（如果没有移动起点）

        path1.moveTo(100,100);
        //当下笔点改变 还是以屏幕的0，0未启动
        path1.quadTo(100,400,200,500);
        //当下笔点移动后 是以下笔点为启点参考点
//        path1.rQuadTo(100,400,200,500);

        //绘制 贝塞尔曲线 启点 控制点  终点
        canvas.drawCircle(100,100,10,paint);
        canvas.drawCircle(100,400,10,paint);
        canvas.drawCircle(200,500,10,paint);


        canvas.drawPath(path1,paint);
    }

    private void test3(Canvas canvas) {
        //移动下笔点，没有改变绘制坐标的画布--》坐标点的参考还是以左上角0,0为起点
        //注意你再调用 path1.moveTo(100,100);下笔点还是100,100
        path1.moveTo(100,100);

        //以当前下笔点为起点 ，再X移动dx；  Y移动dy 作为新起点--》相当moveTo(x+dx,y+dy)
        path1.rMoveTo(100,100);

        path1.lineTo(100,300);
        //相对下笔点坐标为起点 画100,300
        path1.rLineTo(0,300);

        //闭合两条线；一条线不会闭合
        path1.close();

        canvas.drawPath(path1,paint);
    }

    private void test2(Canvas canvas) {
        path1.addCircle(200, 200, 100, Path.Direction.CW);
        path2.addCircle(300, 300, 100, Path.Direction.CW);


        //Path.Op.DIFFERENCE --》path1和path2公共部分        path1的部分隐藏
        //Path.Op.UNION --》path1和path2公共部分     path1的部分隐藏

        //Path.Op.INTERSECT --》path1和path2不是公共部分     path1的部分隐藏
        //Path.Op.REVERSE_DIFFERENCE --》path1和path2不是公共部分     path1的部分隐藏

        //Path.Op.XOR --》没有变化和原来一样

        //注意那个path调用op方法，就针对这个path绘制的图形处理
        path1.op(path2, Path.Op.XOR);


        canvas.drawPath(path1, paint);
        canvas.drawPath(path2, paint);
    }

    private void test1(Canvas canvas) {
        //        canvas.rotate(270,200,200);//旋转画布

        //使用path绘制圆 （圆心X，圆心Y，半径，顺时针CW/逆时针CCW）
//        path1.addCircle(200, 200, 100, Path.Direction.CW);
//
//        //画二阶 贝塞尔
//        path1.quadTo(100,0,500,500);
//
//        //画直线
//        path1.lineTo(400,400);
        //

        //画矩形
//        path1.addRect(100,100,200,200,Path.Direction.CW);


        //画圆
//        paint.setColor(Color.BLACK);
//        canvas.drawCircle(getWidth() / 2, getWidth() / 2, (getWidth() - 100) / 2, paint);
//
//
//        /**
//         * 画弧/圆
//         * 参数（矩形的left，top，right，bottom，开始角度（下笔角度点），幅度）
//         */
//        if (tag < 361) {
//            tag = tag + 10;
//        }
//        path1.addArc(50, 50, getWidth() - 50, getWidth() - 50, 90, tag);
//        //把path设置到画布里面
//        paint.setColor(Color.RED);
//        canvas.drawPath(path1, paint);
//        postInvalidateDelayed(500);

        //画椭圆/圆
//        path1.addOval(300, 300, 400, 400, Path.Direction.CW);


        //画圆/弧线
//        if (tag < 361) {
//            tag = tag + 10;
//        }
//        path1.arcTo(200, 200, 300, 300, 0, tag, false);
//        //把path设置到画布里面
//        paint.setColor(Color.RED);
//        canvas.drawPath(path1, paint);
//        postInvalidateDelayed(500);
//
//        canvas.drawPath(path1, paint);


        //圆的矩形
//        path1.addRoundRect(100,600,300,700,60,60,Path.Direction.CW);
//        path1.addRoundRect(100,600,300,700,50,50,Path.Direction.CW);
//        path1.addRect(100,600,300,700,Path.Direction.CW);
//        path1.addRoundRect(new RectF(100,600,300,700),);

        canvas.drawPath(path1, paint);
    }
}
