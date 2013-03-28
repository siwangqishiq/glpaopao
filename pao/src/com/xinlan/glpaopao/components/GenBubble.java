package com.xinlan.glpaopao.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.xinlan.glpaopao.components.Bubble;
import com.xinlan.glpaopao.view.MainView;
import com.xinlan.utils.Common;

public class GenBubble {
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_RELOAD = 1;
	public static final int STATUS_READY = 2;
	public static final int STATUS_FIRING = 3;
	public static final int STATUS_CANLOAD = 4;
	public static final int STATUS_ISFIRING = 5;

	private MainView context;
	private float x, y;
	public int status;

	private Bubble mBubble;
	private float bubbleSpeed = -0.2f;
	public static int[] colors = { Color.BLACK, Color.BLUE, Color.LTGRAY,
			Color.GREEN, Color.RED, Color.YELLOW };
	private int waitDelay = 20;

	private float absSpeed = 7f;
	private float mBubble_dx, mBubble_dy;

	public GenBubble(MainView context) {
		this.context = context;
		status = STATUS_WAIT;
		x = MainView.screenW / 2;
		y = MainView.screenH + 2 * Bubble.RADIUS;
	}

	public void genOneBubble() {

	}

	public void logic() {
		switch (status) {
		case STATUS_WAIT:// 等待
			waitDelay--;
			if (waitDelay == 0) {
				status = STATUS_CANLOAD;
			}
			break;
		case STATUS_CANLOAD:// 创建新泡泡
			mBubble = new Bubble(context, context.mContent, x, y, genColor());
			status = STATUS_RELOAD;
			break;
		case STATUS_RELOAD:// 装填新泡泡
			mBubble.y += bubbleSpeed;
			if (mBubble.y <= MainView.screenH - Bubble.RADIUS) {
				mBubble.y = MainView.screenH - Bubble.RADIUS;
				status = STATUS_READY;
			}
			mBubble.setY(mBubble.y);
			break;
		case STATUS_FIRING:// 发射泡泡
			// context.getGroupBubbles().setTempBubble(mBubble);
			status = STATUS_ISFIRING;
			break;
		case STATUS_ISFIRING:
			mBubble.moveUpdate();
			break;
		case STATUS_READY:
			break;
		}// end switch
	}

	public static int genColor() {
		return colors[Common.genRand(0, colors.length)];
	}

	public boolean onTouchEvent(MotionEvent event) {
		float touch_x = event.getX();
		float touch_y = MainView.screenH - event.getY();// 坐标转化 适应opengl的坐标系
		if (STATUS_READY == status) {
			switch (MotionEventCompat.getActionMasked(event)) {
			case MotionEvent.ACTION_DOWN:
				context.arrow.setIsShow(true);
				// context.arrow.isShow=true;
				context.arrow.resetDegrees(touch_x - mBubble.x, touch_y
						- mBubble.y, mBubble.x);
				break;
			case MotionEvent.ACTION_MOVE:
				context.arrow.setIsShow(true);
				context.arrow.resetDegrees(touch_x - mBubble.x, touch_y
						- mBubble.y, mBubble.x);
				break;
			case MotionEvent.ACTION_UP:
				calculateVector(touch_x, touch_y);
				status = STATUS_FIRING;
				context.arrow.setIsShow(false);
				break;
			}// end switch
		}
		return true;
	}

	private void calculateVector(float touch_x, float touch_y) {
		float vector_x = touch_x - mBubble.x;
		float vector_y = touch_y - mBubble.y;
		float vector_len = (float) Math.sqrt(vector_x * vector_x + vector_y
				* vector_y);// 计算单位向量
		mBubble_dx = absSpeed * (vector_x / vector_len);
		mBubble_dy = absSpeed * (vector_y / vector_len);
		mBubble.setSpeed(mBubble_dx, mBubble_dy);
	}

}// end class
