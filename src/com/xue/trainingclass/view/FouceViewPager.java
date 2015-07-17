package com.xue.trainingclass.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FouceViewPager extends ViewPager {
	public FouceViewPager(Context context) {
		super(context);
	}

	public FouceViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	PointF downPoint = new PointF();
	OnSingleTouchListener onSingleTouchListener;

	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		switch (evt.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ��¼����ʱ�������
			downPoint.x = evt.getX();
			downPoint.y = evt.getY();
			if (this.getChildCount() > 1) { // �����ݣ�����1��ʱ
				// ֪ͨ�丸�ؼ������ڽ��е��Ǳ��ؼ��Ĳ���������������
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (this.getChildCount() > 1) { // �����ݣ�����1��ʱ
				// ֪ͨ�丸�ؼ������ڽ��е��Ǳ��ؼ��Ĳ���������������
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:
			// ��upʱ�ж��Ƿ��º����ֵ�����Ϊһ����
			if (PointF.length(evt.getX() - downPoint.x, evt.getY()
					- downPoint.y) < (float) 10.0) {
				onSingleTouch(this, getCurrentItem());
				return true;
			}
			break;
		}
		return super.onTouchEvent(evt);
	}

	public void onSingleTouch(View v, int currentitem) {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch(v, currentitem);
		}
	}

	public interface OnSingleTouchListener {
		public void onSingleTouch(View v, int currentitem);

	}

	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}
}
