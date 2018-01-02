package com.example.phant.rthchallenge.presentation.charviewlib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.phant.rthchallenge.R;

public class ChartView extends RelativeLayout {

    private Paint mPaint = new Paint();
    private List<AbstractSeries> mSeries = new ArrayList<AbstractSeries>();
    private LinearLayout mLeftLabelLayout;
    private LinearLayout mTopLabelLayout;
    private LinearLayout mBottomLabelLayout;
    private LinearLayout mRightLabelLayout;
    private int mLeftLabelWidth;
    private int mTopLabelHeight;
    private int mRightLabelWidth;
    private int mBottomLabelHeight;
    private RectD mValueBounds = new RectD();
    private double mMinX = Double.MAX_VALUE;
    private double mMaxX = Double.MIN_VALUE;
    private double mMinY = Double.MAX_VALUE;
    private double mMaxY = Double.MIN_VALUE;
    private Rect mGridBounds = new Rect();
    private int mGridLineColor;
    private int mGridLineWidth;
    private int mGridLinesHorizontal;
    private int mGridLinesVertical;

    public ChartView(Context context) {
        this(context, null, 0);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        setBackgroundColor(Color.TRANSPARENT);
        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        mGridLineColor = attributes.getInt(R.styleable.ChartView_gridLineColor, Color.BLACK);
        mGridLineWidth = attributes.getDimensionPixelSize(R.styleable.ChartView_gridLineWidth, 1);
        mGridLinesHorizontal = attributes.getInt(R.styleable.ChartView_gridLinesHorizontal, 5);
        mGridLinesVertical = attributes.getInt(R.styleable.ChartView_gridLinesVertical, 5);
        mLeftLabelWidth = attributes.getDimensionPixelSize(R.styleable.ChartView_leftLabelWidth, 0);
        mTopLabelHeight = attributes.getDimensionPixelSize(R.styleable.ChartView_topLabelHeight, 0);
        mRightLabelWidth = attributes.getDimensionPixelSize(R.styleable.ChartView_rightLabelWidth, 0);
        mBottomLabelHeight = attributes.getDimensionPixelSize(R.styleable.ChartView_bottomLabelHeight, 0);
        // left label layout
        mLeftLabelLayout = new LinearLayout(context);
        mLeftLabelLayout.setLayoutParams(new LayoutParams(mLeftLabelWidth, LayoutParams.MATCH_PARENT));
        mLeftLabelLayout.setOrientation(LinearLayout.VERTICAL);
        // top label layout
        mTopLabelLayout = new LinearLayout(context);
        mTopLabelLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mTopLabelHeight));
        mTopLabelLayout.setOrientation(LinearLayout.HORIZONTAL);
        // right label layout
        LayoutParams rightLabelParams = new LayoutParams(mRightLabelWidth, LayoutParams.MATCH_PARENT);
        rightLabelParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightLabelLayout = new LinearLayout(context);
        mRightLabelLayout.setLayoutParams(rightLabelParams);
        mRightLabelLayout.setOrientation(LinearLayout.VERTICAL);
        // bottom label layout
        LayoutParams bottomLabelParams = new LayoutParams(LayoutParams.MATCH_PARENT, mBottomLabelHeight);
        bottomLabelParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mBottomLabelLayout = new LinearLayout(context);
        mBottomLabelLayout.setLayoutParams(bottomLabelParams);
        mBottomLabelLayout.setOrientation(LinearLayout.HORIZONTAL);
        // Add label views
        addView(mLeftLabelLayout);
        addView(mTopLabelLayout);
        addView(mRightLabelLayout);
        addView(mBottomLabelLayout);
    }

    public void addSeries(AbstractSeries series) {
        if (mSeries == null) {
            mSeries = new ArrayList<>();
        }
        extendRange(series.getMinX(), series.getMinY());
        extendRange(series.getMaxX(), series.getMaxY());
        mSeries.add(series);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int gridLeft = mLeftLabelWidth + mGridLineWidth - 1;
        final int gridTop = mTopLabelHeight + mGridLineWidth - 1;
        final int gridRight = getWidth() - mRightLabelWidth - mGridLineWidth;
        final int gridBottom = getHeight() - mBottomLabelHeight - mGridLineWidth;
        mGridBounds.set(gridLeft, gridTop, gridRight, gridBottom);
        // Set sizes
        LayoutParams leftParams = (LayoutParams) mLeftLabelLayout.getLayoutParams();
        leftParams.height = mGridBounds.height();
        mLeftLabelLayout.setLayoutParams(leftParams);
        LayoutParams topParams = (LayoutParams) mTopLabelLayout.getLayoutParams();
        topParams.width = mGridBounds.width();
        mTopLabelLayout.setLayoutParams(topParams);
        LayoutParams rightParams = (LayoutParams) mRightLabelLayout.getLayoutParams();
        rightParams.height = mGridBounds.height();
        mRightLabelLayout.setLayoutParams(rightParams);
        LayoutParams bottomParams = (LayoutParams) mBottomLabelLayout.getLayoutParams();
        bottomParams.width = mGridBounds.width();
        mBottomLabelLayout.setLayoutParams(bottomParams);
        // Set layouts
        mLeftLabelLayout.layout(0, gridTop, gridLeft, gridBottom);
        mTopLabelLayout.layout(gridLeft, 0, gridRight, gridTop);
        mRightLabelLayout.layout(gridRight, gridTop, getWidth(), gridBottom);
        mBottomLabelLayout.layout(gridLeft, gridBottom, gridRight, getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (AbstractSeries series : mSeries) {
            series.draw(canvas, mGridBounds, mValueBounds);
        }
    }

    private void extendRange(double x, double y) {
        if (x < mMinX) {
            mMinX = x;
        }
        if (x > mMaxX) {
            mMaxX = x;
        }
        if (y < mMinY) {
            mMinY = y;
        }
        if (y > mMaxY) {
            mMaxY = y;
        }
        mValueBounds.set(mMinX, mMinY, mMaxX, mMaxY);
    }
}