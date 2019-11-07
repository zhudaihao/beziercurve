package cn.zdh.beziercurve;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘制n阶贝塞尔曲线，辅助工具
 */
public class BezierCanvasUtil {
    //采集贝塞尔曲线上点（采集越多绘制越精准）
    private List<PointF> mControlPoints;
    private Path mPath;

    public BezierCanvasUtil(List<PointF> mControlPoints, Path mPath) {
        this.mControlPoints = mControlPoints;
        this.mPath = mPath;
    }

    /**
     * 建议使用
     * --------------------------------------使用杨辉三角 计算控制点------------------------------------------
     */
    public ArrayList<PointF> calculate() {
        mPath.reset();
        //控制点个数(number-1阶)
        int number = mControlPoints.size();
        //小于2阶省略
        if (number < 2) {
            return null;
        }
        ArrayList<PointF> points = new ArrayList<>();

        //计算杨辉三角
        int[] mi = new int[number];
        mi[0] = mi[1] = 1;//第二层（一阶常数项）
        for (int i = 3; i <= number; i++) {
            //得到上一层的数据
            int[] t = new int[i - 1];
            for (int j = 0; j < t.length; j++) {
                t[j] = mi[j];
            }
            //计算当前行的数据
            mi[0] = mi[i - 1] = 1;
            for (int j = 0; j < i - 2; j++) {
                mi[j + 1] = t[j] + t[j + 1];
            }
        }
        //计算坐标点
        for (int i = 0; i < 1000; i++) {
            float t = (float) i / 1000;
            //分别计算x,y坐标
            //计算各项和(𝑛¦𝑖) 𝑃_𝑖 〖(1−𝑡)〗^(𝑛−i) 𝑡^𝑖
            PointF pointF = new PointF();
            for (int j = 0; j < number; j++) {
                pointF.x += mi[j] * mControlPoints.get(j).x * Math.pow(1 - t, number - 1 - j) * Math.pow(t, j);
                pointF.y += mi[j] * mControlPoints.get(j).y * Math.pow(1 - t, number - 1 - j) * Math.pow(t, j);
            }
            points.add(pointF);
            //0 moveTo
            if (i == 0) {
                mPath.moveTo(pointF.x, pointF.y);
            } else {
                mPath.lineTo(pointF.x, pointF.y);
            }
        }
        return points;
    }


    /**
     * 测试性能慢,
     * 绘制简单的贝塞尔曲线
     * --------------------------------------使用一阶画贝塞尔线公式，计算控制点------------------------------------------
     */

    public ArrayList<PointF> buildBezierPoints() {
        mPath.reset();
        ArrayList<PointF> points = new ArrayList<>();
        int order = mControlPoints.size() - 1;//阶数
        //份数(把塞贝尔曲线拆分多少份)
        float delta = 1.0f / 1000;
        for (float t = 0; t <= 1; t += delta) {
            //bezier点集
            PointF pointF = new PointF(deCasteJau(order, 0, t, true), deCasteJau(order, 0, t, false));//计算在曲线上点位置
            points.add(pointF);
            if (points.size() == 1) {
                mPath.moveTo(points.get(0).x, points.get(0).y);
            } else {
                mPath.lineTo(pointF.x, pointF.y);
            }
        }
        return points;
    }

    /**
     * p(i,j) =  (1-t) * p(i-1,j)  +  t * p(i-1,j+1);
     *
     * @param i          阶数（几级贝塞尔）
     * @param j          控制点
     * @param t          时间
     * @param calculateX 计算哪个坐标值 true=x
     * @return
     */
    private float deCasteJau(int i, int j, float t, boolean calculateX) {
//        Log.e("zdh", "---------------j  " + j);
        if (i == 1) {
            //一阶 贝塞尔曲线公式-->B(t) = P0+(P1-P0)t = (1-t)P0+t*p1,t∈[0,1]   (P0起点，P1终点，t控制点)
            //例如X点算法：  (1-t)P0+t*p1,t∈[0,1]  --->(1-t)P0+t*p1 -->(1 - t) * mControlPoints.get(j).x + t * mControlPoints.get(j + 1).x
            return calculateX ? (1 - t) * mControlPoints.get(j).x + t * mControlPoints.get(j + 1).x :
                                (1 - t) * mControlPoints.get(j).y + t * mControlPoints.get(j + 1).y;
        } else {
            //其他阶 贝塞尔曲线公式---》把多阶拆分成一阶 --》通过递归 注意j+1了
            return (1 - t) * deCasteJau(i - 1, j, t, calculateX) + t * deCasteJau(i - 1, j + 1, t, calculateX);
        }
    }


    /**
     * --------------------------------------通用计算公式 计算控制点------------------------------------------
     */


    /**
     * 通用计算公式
     *
     * @param positions 贝塞尔曲线控制点坐标
     * @param precision 精度，需要计算的该条贝塞尔曲线上的点的数目
     * @return 该条贝塞尔曲线上的点（二维坐标）
     */
    public float[][] calculate(float[][] positions, int precision) {
        //维度，坐标轴数（二维坐标，三维坐标...）
        int dimension = positions[0].length;

        //贝塞尔曲线控制点数（number-1阶数）
        int number = positions.length;

        //控制点数不小于 2 ，至少为二维坐标系
        if (number < 2 || dimension < 2)
            return null;

        float[][] result = new float[precision][dimension];

        //计算杨辉三角
        int[] mi = new int[number];
        mi[0] = mi[1] = 1;//第二层（一阶时常数项）
        for (int i = 3; i <= number; i++) {
            //得到上一层的数据
            int[] t = new int[i - 1];
            for (int j = 0; j < t.length; j++) {
                t[j] = mi[j];
            }
            //计算当前行的数据
            mi[0] = mi[i - 1] = 1;
            for (int j = 0; j < i - 2; j++) {
                mi[j + 1] = t[j] + t[j + 1];
            }
        }

        //计算坐标点
        for (int i = 0; i < precision; i++) {
            float t = (float) i / precision;
            //分别计算各轴上的坐标
            for (int j = 0; j < dimension; j++) {
                //计算各项和(𝑛¦𝑖) 𝑃_𝑖 〖(1−𝑡)〗^(𝑛−i) 𝑡^𝑖
                float temp = 0.0f;
                for (int k = 0; k < number; k++) {
                    temp += mi[k] * positions[k][j] * Math.pow(1 - t, number - 1 - k) * Math.pow(t, k);
                }
                result[i][j] = temp;
            }
        }
        return result;
    }


}
