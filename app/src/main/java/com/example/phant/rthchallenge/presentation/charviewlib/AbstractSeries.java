package com.example.phant.rthchallenge.presentation.charviewlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class AbstractSeries {

	protected Paint mPaint = new Paint();

	private List<AbstractPoint> mPoints;
	private boolean mPointsSorted = false;
	private double mMinX = Double.MAX_VALUE;
	private double mMaxX = Double.MIN_VALUE;
	private double mMinY = Double.MAX_VALUE;
	private double mMaxY = Double.MIN_VALUE;

	protected abstract void drawPoint(Canvas canvas, AbstractPoint point, float scaleX, float scalY, Rect gridBounds);

	public AbstractSeries() {
		mPaint.setAntiAlias(true);
	}

	public void addPoint(AbstractPoint point) {
		if (mPoints == null) {
			mPoints = new ArrayList<>();
		}

		extendRange(point.getX(), point.getY());
		mPoints.add(point);

		mPointsSorted = false;
	}

	// Line properties

	public void setLineColor(int color) {
		mPaint.setColor(color);
	}

	public void setLineWidth(float width) {
		mPaint.setStrokeWidth(width);
	}

	private void sortPoints() {
		if (!mPointsSorted) {
			Collections.sort(mPoints);
			mPointsSorted = true;
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
	}

	double getMinX() {
		return mMinX;
	}

	double getMaxX() {
		return mMaxX;
	}

	double getMinY() {
		return mMinY;
	}

	double getMaxY() {
		return mMaxY;
	}

	void draw(Canvas canvas, Rect gridBounds, RectD valueBounds) {
		sortPoints();

		final float scaleX = (float) gridBounds.width() / (float) valueBounds.width();
		final float scaleY = (float) gridBounds.height() / (float) valueBounds.height();

		for (AbstractPoint point : mPoints) {
			drawPoint(canvas, point, scaleX, scaleY, gridBounds);
		}

		onDrawingComplete();
	}

	protected void onDrawingComplete() {
	}

	public static abstract class AbstractPoint implements Comparable<AbstractPoint> {
		private double mX;
		private double mY;

		public AbstractPoint() {
		}

		public AbstractPoint(double x, double y) {
			mX = x;
			mY = y;
		}

		public double getX() {
			return mX;
		}

		public double getY() {
			return mY;
		}

		@Override
		public int compareTo(AbstractPoint another) {
			return Double.compare(mX, another.mX);
		}
	}
}