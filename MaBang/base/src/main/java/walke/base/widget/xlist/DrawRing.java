package walke.base.widget.xlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import walke.base.R;


/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/2/7.
 */
public class DrawRing extends View {

    /**
     * 直径
     */
    private float diameter = 100;
    /**
     * 圆环宽度
     */
    private float ringWidth = 30;
    /**
     * 开始角度
     */
    private int startAngle = 0;
    /**
     * 旋转速度
     */
    private int angleSpeed = 8;

    private float padding = 6;

    public boolean rotate;
    private float sweepAngle = 0;
    private int aboveColor = Color.parseColor("#ffE61A5F");
    private int underColor = Color.parseColor("#ffCAD4E3");


    public DrawRing(Context context) {
        this(context, null);
    }

    public DrawRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DrawRing, defStyleAttr, 0);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.DrawRing_aboveColor) {
                aboveColor = a.getColor(index, Color.parseColor("#ffE61A5F"));
            } else if (index == R.styleable.DrawRing_underColor) {
                underColor = a.getColor(index, Color.parseColor("#ffCAD4E3"));
            }
            /*
            switch (index) {
                case R.styleable.DrawRing_aboveColor:
                    aboveColor = a.getColor(index, Color.parseColor("#ffE61A5F"));
                    break;
                case R.styleable.DrawRing_underColor:
                    underColor = a.getColor(index, Color.parseColor("#ffCAD4E3"));
                    break;
            }
            */

        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float height = getHeight();
        float width = getWidth();
        ringWidth = width / 10;
        padding = ringWidth / 2 - 0.1f;//-0.3f
        if (width < height) {
            diameter = width;
        } else {
            diameter = height;
        }
        CircleRing mCRing = new CircleRing(diameter, this.ringWidth);
        mCRing.drawCircleRingUnder(canvas);//底层
        if (rotate) {
            if (sweepAngle > 0 && sweepAngle < 181) {
                stopRotate();
            }
            mCRing.drawCircleRingRotate(canvas, startAngle);//表层
            startAngle += angleSpeed;
            invalidate();
        } else {
            mCRing.drawCircleRingAbove(canvas, sweepAngle);//表层
        }
    }


    public void startRotate(int speed) {
        startAngle = 0;
        rotate = true;
        angleSpeed = speed;
        invalidate();
    }

    public void stopRotate() {
        if (sweepAngle > 181) {
            sweepAngle = 181;
        }
        rotate = false;
    }

    public void setProgress(float progress) {
        if (progress > 181)
            progress = 181;
        sweepAngle = progress;
        invalidate();
    }

    public float getProgress() {

        return sweepAngle;
    }

    /**
     * 吾日三省吾身：看脸，看秤，看余额。
     * Created by lanso on 2017/2/7.
     * 圆环
     */
    public class CircleRing {
        /**
         * 宽
         */
        float ringX;
        /**
         * 高
         */
        float ringY;
        /**
         * 圆环宽度
         */
        float ringWidth;
        /**
         * 画笔
         */
        Paint paint;

        /**
         * @param diameter  直径
         * @param ringWidth 圆环宽度
         */
        public CircleRing(float diameter, float ringWidth) {
            ringX = ringY = diameter;
            this.ringWidth = ringWidth;
            paint = new Paint();
            paint.setAntiAlias(true); //消除锯齿
            paint.setStrokeWidth(ringWidth);
            paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
        }

        /**
         * 表层 旋转函数
         *
         * @param canvas     画布
         * @param startAngle 圆弧起始角度，单位为度。
         */
        public void drawCircleRingRotate(Canvas canvas, int startAngle) {

            RectF rect = new RectF(padding, padding, ringX - padding, ringY - padding);
            if (sweepAngle == 0) {
                //设置  圆环颜色
                paint.setColor(underColor);
            } else {
                paint.setColor(aboveColor);
            }
            /**
             oval :指定圆弧的外轮廓矩形区域。
             startAngle: 圆弧起始角度，单位为度。
             sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
             useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。//false 不画圆心
             paint: 绘制圆弧的画板属性，如颜色，是否填充等。*/
            canvas.drawArc(rect, startAngle + 88, sweepAngle, false, paint);//顺时针画图
        }

        /**
         * 表层 渐变函数
         *
         * @param canvas 画布
         */
        public void drawCircleRingAbove(Canvas canvas, float sweepAngle) {

            RectF rect = new RectF(padding, padding, ringX - padding, ringY - padding);

            if (sweepAngle == 0) {
                //设置  圆环颜色
                paint.setColor(underColor);
            } else {
                paint.setColor(aboveColor);
            }
            if (sweepAngle <= 181) {
                //false 不画圆心
                canvas.drawArc(rect, 88, sweepAngle, false, paint);
                if (sweepAngle == 181) {
                    startRotate(3);
                }
            } else {
                //startRotate(3);
            }

        }

        /**
         * @param canvas 底层
         */
        public void drawCircleRingUnder(Canvas canvas) {
            RectF rect = new RectF(padding, padding, ringX - padding, ringY - padding);

            //设置  圆环颜色
            paint.setColor(underColor);

            canvas.drawArc(rect, 0, 360, false, paint);
        }

    }

}
