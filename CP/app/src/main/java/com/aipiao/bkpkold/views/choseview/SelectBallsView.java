package com.aipiao.bkpkold.views.choseview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aipiao.bkpkold.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SelectBallsView extends View {
    /**
     * 每列球个数 默认7个
     */
    private int numCount;
    /**
     * 开始数字
     */
    private int startNum;
    /**
     * 结束数字
     */
    private int endNum;
    /**
     * 球颜色
     */
    private int ballColor = Color.parseColor("#C95C58");
    /**
     * 是否为数字补全0
     */
    private boolean hasZero;
    /**
     * 是否显示遗漏
     */
    private boolean showMissValue = true;
    /**
     * 小球画笔
     */
    private Paint ballPaint;
    /**
     * 文字画笔
     */
    private Paint txtPaint;
    /**
     * 遗漏画笔
     */
    private Paint msPaint;

    private SelectBallPickListener selectBallPickListener;

    private List<Ball> balls = new ArrayList<>();
    private int width;      // 选号区宽
    private int height;     // 选号区高
    /**
     * 球之间的空格
     */
    private int space;
    private int padding;
    private int ballWidth;
    private int ballHeight;
    private int missWidth;
    private int missHeight;
    private Bitmap bitmapSelected;
    private Bitmap bitmapUnselected;
    private int txtSelectedColor = Color.parseColor("#FFFFFF");
    private int txtUnselectedColor = Color.parseColor("#2B2B2B");
    private Context mContext;
    private Paint circlePaint;
    private int strokeWidth;
    private float txtMidValue;
    private float missMidValue;
    private boolean[] check = {true, false};

    private int missValue = 8;
    private Random random;


    public void setSelectBallPickListener(SelectBallPickListener selectBallPickListener) {
        this.selectBallPickListener = selectBallPickListener;
    }

    public SelectBallsView(Context context) {
        super(context);
        initView(context, null);
    }

    public SelectBallsView(Context context, int ballColor, int txtSelectedColor, int txtUnselectedColor) {
        super(context);
        this.ballColor = ballColor;
        this.txtSelectedColor = txtSelectedColor;
        this.txtUnselectedColor = txtUnselectedColor;
        initView(context, ballColor, txtSelectedColor, txtUnselectedColor);
    }

    private void initView(Context context, int ballColor, int txtSelectedColor, int txtUnselectedColor) {
        random = new Random();
        mContext = context;
        missValue = 0;
        this.ballColor = ballColor;
        this.txtSelectedColor = txtSelectedColor;
        this.txtUnselectedColor = txtUnselectedColor;
        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);
        this.bitmapSelected = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sred_radius_style);
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#E3938B"));
        strokeWidth = dp2px(mContext, 1);
        circlePaint.setStrokeWidth(strokeWidth);


        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(sp2px(mContext, 14));
        txtPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        txtMidValue = (fontMetrics.top + fontMetrics.bottom) / 2;

        msPaint = new Paint();
        msPaint.setAntiAlias(true);
        msPaint.setTextSize(sp2px(mContext, 14));
        msPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics1 = msPaint.getFontMetrics();
        missMidValue = (fontMetrics1.top + fontMetrics1.bottom) / 2;

        padding = space = dp2px(context, 10);

        initBalls();
    }

    public SelectBallsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SelectBallsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        random = new Random();
        mContext = context;
        missValue = 0;
        Resources resources = mContext.getResources();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectBallsView);
            numCount = a.getInteger(R.styleable.SelectBallsView_numCount, 7);
            startNum = a.getInteger(R.styleable.SelectBallsView_start, 0);
            endNum = a.getInteger(R.styleable.SelectBallsView_end, 33);
            ballColor = a.getColor(R.styleable.SelectBallsView_ballColor, Color.parseColor("#D18383"));
            txtSelectedColor = a.getColor(R.styleable.SelectBallsView_txtSelectedColor, Color.parseColor("#ffffff"));
            txtUnselectedColor = a.getColor(R.styleable.SelectBallsView_txtUnselectedColor, Color.parseColor("#2B2B2B"));
            hasZero = a.getBoolean(R.styleable.SelectBallsView_hasZero, true);
            bitmapSelected = BitmapFactory.decodeResource(resources, a.getResourceId(R.styleable.SelectBallsView_drawableSelected, 0));
            bitmapUnselected = BitmapFactory.decodeResource(resources, a.getResourceId(R.styleable.SelectBallsView_drawableUnselected, 0));
        } else {
            this.ballColor = getBallColor();
            this.txtSelectedColor = getTxtSelectedColor();
            this.txtUnselectedColor = getTxtUnselectedColor();
            bitmapSelected = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sred_radius_style);
        }
        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#E3938B"));
        strokeWidth = dp2px(mContext, 1);
        circlePaint.setStrokeWidth(strokeWidth);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(sp2px(mContext, 14));
        txtPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        txtMidValue = (fontMetrics.top + fontMetrics.bottom) / 2;

        msPaint = new Paint();
        msPaint.setAntiAlias(true);
        msPaint.setTextSize(sp2px(mContext, 14));
        msPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics1 = msPaint.getFontMetrics();
        missMidValue = (fontMetrics1.top + fontMetrics1.bottom) / 2;

        padding = space = dp2px(context, 10);

        initBalls();
    }

    /**
     * 初始化球
     */
    public void initBalls() {
        balls.clear();
        for (int i = startNum; i <= endNum; i++) {
            Ball ball = new Ball();
            ball.setNumber(addZero(String.valueOf(i)));
            ball.setSelected(false);
            balls.add(ball);
        }
    }

    public void initCleanBalls() {
        balls.clear();
        for (int i = startNum; i <= endNum; i++) {
            Ball ball = new Ball();
            ball.setNumber(addZero(String.valueOf(i)));
            ball.setSelected(false);
            balls.add(ball);
        }
        postInvalidate();
    }


    public void randomSelectInitBalls() {
        balls.clear();
        Random rand = new Random();
        for (int i = startNum; i <= endNum; i++) {
            Ball ball = new Ball();
            int randNuber = rand.nextInt(2);
            ball.setNumber(addZero(String.valueOf(i)));
            ball.setSelected(check[randNuber]);
            balls.add(ball);
        }
        postInvalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        missWidth = ballWidth = ballHeight = (width - (numCount - 1) * space - padding * 2) / numCount;
        missHeight = missWidth / 2;
        computeLocation(ballWidth, ballHeight);
        int rows = balls.size() / numCount + ((balls.size() % numCount > 0) ? 1 : 0);
        if (showMissValue) {
            height = rows * (ballHeight + missHeight) + (rows - 1) * space + padding * 2;
        } else {
            height = rows * ballHeight + (rows - 1) * space + padding * 2;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 计算球位置  文字位置
     *
     * @param ballWidth
     * @param ballHeight
     */
    private void computeLocation(int ballWidth, int ballHeight) {
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball cb = balls.get(i);
            if (showMissValue) {
                cb.setRect((i % numCount) * ballWidth + i % numCount * space + padding,
                        (i / numCount) * ballHeight + i / numCount * (space + missHeight) + padding,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding,
                        (i / numCount + 1) * ballHeight + i / numCount * (space + missHeight) + padding);
                cb.setMissRect((i % numCount) * ballWidth + i % numCount * space + padding,
                        (i / numCount) * ballHeight + i / numCount * (space + missHeight) + padding + ballHeight,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding,
                        (i / numCount + 1) * ballHeight + i / numCount * (space + missHeight) + padding + missHeight);
            } else {
                cb.setRect((i % numCount) * ballWidth + i % numCount * space + padding,
                        (i / numCount) * ballHeight + i / numCount * space + padding,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding,
                        (i / numCount + 1) * ballHeight + i / numCount * space + padding);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball cb = balls.get(i);
            RectF rectF = new RectF(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom());
            if (bitmapSelected != null || bitmapUnselected != null) {
                //绘制图片
                canvas.drawBitmap(cb.isSelected() ? bitmapSelected : bitmapUnselected, null,
                        new Rect(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom()), ballPaint);
            } else {
                //绘制小球边框和小球
                canvas.drawArc(new RectF(cb.getLeft() - strokeWidth, cb.getTop() - strokeWidth,
                        cb.getRight() + strokeWidth, cb.getBottom() + strokeWidth), 0, 360, false, circlePaint);
                ballPaint.setColor(cb.isSelected() ? ballColor : Color.WHITE);
                canvas.drawOval(rectF, ballPaint);
            }
            //绘制文字
            txtPaint.setColor(cb.isSelected() ? txtSelectedColor : txtUnselectedColor);
            canvas.drawText(cb.getNumber(), rectF.centerX(), rectF.centerY() - txtMidValue, txtPaint);
            //绘制遗漏
            if (showMissValue) {
                msPaint.setColor(cb.getMissValueColor());
                RectF missRectF = new RectF(cb.getmLeft(), cb.getmTop(), cb.getmRight(), cb.getmBottom());
                canvas.drawText(String.valueOf(missValue), missRectF.centerX(), missRectF.centerY() - missMidValue, msPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                int size = balls.size();
                for (int i = 0; i < size; i++) {
                    Ball tempBall = balls.get(i);
                    if (pointAtBall(tempBall, x, y)) {
                        tempBall.setSelected(!tempBall.isSelected());
                        requestLayout();
                        invalidate();
                        if (selectBallPickListener != null) {
                            selectBallPickListener.pickOnClickListener();
                        }
                        break;
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 这个点落在球上
     *
     * @param tempBall
     * @param x
     * @param y
     * @return
     */
    private boolean pointAtBall(Ball tempBall, float x, float y) {
        Rect rect = new Rect(tempBall.getLeft(), tempBall.getTop(), tempBall.getRight(), tempBall.getBottom());
        return rect.contains((int) x, (int) y);
    }

    /**
     * 需要补0的在前面直接补0
     *
     * @param num
     * @return
     */
    private String addZero(String num) {
        if (hasZero && num.length() == 1) {
            return "0" + num;
        }
        return num;
    }

    /**
     * 设置是否需要补0
     *
     * @param hasZero
     */
    public void setHasZero(boolean hasZero) {
        this.hasZero = hasZero;
        invalidate();
    }

    public void setShowMissValue(boolean showMissValue) {
        this.showMissValue = showMissValue;
        requestLayout();
        invalidate();
    }

    public boolean getShowMissValue() {
        return showMissValue;
    }

    /**
     * 将sp值转换为px值
     */
    public int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取选中球的号码
     *
     * @return
     */
    public String getSelectBallsString() {
        StringBuilder sb = new StringBuilder();
        List<Ball> selectBalls = getSelectBalls();
        for (Ball selectBall : selectBalls) {
            sb.append(selectBall.getNumber() + ",");
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     * 获取选中的球
     *
     * @return
     */
    public List<Ball> getSelectBalls() {
        List<Ball> selectBalls = new ArrayList<>();
        for (Ball ball : balls) {
            if (ball.isSelected()) {
                selectBalls.add(ball);
            }
        }
        return selectBalls;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getNumCount() {
        return numCount;
    }

    public void setNumCount(int numCount) {
        this.numCount = numCount;
    }

    public interface SelectBallPickListener {
        void pickOnClickListener();
    }

    public int getBallColor() {
        return ballColor;
    }

    public void setBallColor(int ballColor) {
        this.ballColor = ballColor;
    }

    public boolean isHasZero() {
        return hasZero;
    }

    public Paint getTxtPaint() {
        return txtPaint;
    }

    public void setTxtPaint(Paint txtPaint) {
        this.txtPaint = txtPaint;
    }

    public int getTxtUnselectedColor() {
        return txtUnselectedColor;
    }

    public void setTxtUnselectedColor(int txtUnselectedColor) {
        this.txtUnselectedColor = txtUnselectedColor;
    }

    public int getTxtSelectedColor() {
        return txtSelectedColor;
    }

    public void setTxtSelectedColor(int txtSelectedColor) {
        this.txtSelectedColor = txtSelectedColor;
    }
}
