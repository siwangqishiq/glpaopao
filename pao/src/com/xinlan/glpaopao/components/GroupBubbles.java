package com.xinlan.glpaopao.components;

import java.util.LinkedList;
import com.xinlan.glpaopao.view.MainView;
import com.xinlan.utils.Common;
import com.xinlan.utils.VectorUtil;
import android.graphics.Paint;

/**
 * 
 * @author Panyi
 * 
 */
public class GroupBubbles {
	public static final int ROW_NUM = 5;
	public static final int COL_NUM = 5;

	private MainView context;
	private float center_x, center_y;
	public LinkedList<Bubble> root;
	private LinkedList<Bubble> hitList;
	private Bubble tempBubble;// 临时泡泡
	private Paint paint;

	//private double dRotate = 0.01f;
	private float rotateSpeed = 0.0f;
	private float descdRotate = 0.0005f;

	private float rotateSinA, rotateCosA;// 转动三角函数

	public GroupBubbles(MainView context) {
		this.context = context;
		root = new LinkedList<Bubble>();
		hitList = new LinkedList<Bubble>();
		paint = new Paint();
		paint.setAntiAlias(true);
		center_x = MainView.screenW / 2;
		center_y = MainView.screenH / 2;
	}

	public void init() {
		genBubbles(4);
	}

	private void genBubbles(int layer) {
		for (int i = 0; i < layer; i++) {
			if (i == 0) {
				root.add(new Bubble(context,context.mContent, center_x, center_y,
						GenBubble.genColor()));
			} else {
				genInitBubble(center_x, center_y, 6 * i, i
						* (Bubble.RADIUS + Bubble.RADIUS));
			}
		}// end for i
	}

	public void setTempBubble(Bubble bubble) {
		//context.soundPlayer.playSound(R.raw.fire_bubble);// 发射泡泡
		this.tempBubble = bubble;
	}

	private void removeBubble(Bubble bubble) {
		//context.disappear.addDisappearBubble(bubble);
	}

	public void logic() {
		if (tempBubble != null) {
			tempBubble.x += tempBubble.dx;
			tempBubble.y += tempBubble.dy;
			tempBubble.setX(tempBubble.x);
			tempBubble.setY(tempBubble.y);
			if (tempBubble.x <= tempBubble.radius
					|| tempBubble.x > MainView.screenW - tempBubble.radius) {
				tempBubble.dx *= -1;
				//context.soundPlayer.playSound(R.raw.click2);
			}

			if (tempBubble.y<=0 ||
					tempBubble.y > MainView.screenH - tempBubble.radius) {// 碰到墙壁
				tempBubble.dy *= -1;
				//context.soundPlayer.playSound(R.raw.click2);
			}

			if (tempBubble.x <= -tempBubble.radius// 越过边界
					|| tempBubble.x > MainView.screenW + tempBubble.radius
					|| tempBubble.y <= -tempBubble.radius
					|| tempBubble.y > MainView.screenH + tempBubble.radius) {
				//context.getGenBubble().status = GenBubble.STATUS_CANLOAD;
				tempBubble = null;
				System.gc();
			} else {
				// 在界面内 做碰撞检测
				for (Bubble bubble : root) {
					if (isBubbleHit(tempBubble, bubble)) {// 碰撞
						hitList.add(bubble);
					}
				}// end for
				if (hitList.size() >= 1) {// 碰撞
					if (tempBubble.getColor() == hitList.get(0).getColor()) {// 消除事件
						//context.soundPlayer.playSound(R.raw.kill_bubble);
						removeBubble(hitList.get(0));
						removeBubble(tempBubble);
					} else {// 无消除
						//context.soundPlayer.playSound(R.raw.click1);
					}
					for (Bubble bubble : hitList) {
						hitRelocation(tempBubble, bubble);
					}
					hitRotate(tempBubble);
					tempBubble.dx = 0;
					tempBubble.dy = 0;
					root.add(tempBubble);
					tempBubble = null;
					context.genBubble.status = GenBubble.STATUS_CANLOAD;
					hitList.remove();
					hitList.clear();
					System.gc();
					// System.out.println(rotateSpeed);
				}
			}
		}// end if
		
//		for (Bubble bubble : root) {// 所有泡泡旋转
//			rotateItem(bubble);
//		}// end for
		
		if (Math.abs(rotateSpeed) > 0.001f) {
			// 提前计算出转动三角函数值
			rotateSinA = (float) Math.sin(rotateSpeed);
			rotateCosA = (float) Math.cos(rotateSpeed);
			for (Bubble bubble : root) {// 所有泡泡旋转
				rotateItem(bubble, rotateSpeed);
			}// end for
			int flag = -1 * Common.getFlag(rotateSpeed);
			rotateSpeed += flag * descdRotate;
		} else {
			rotateSpeed = 0.0f;
			rotateSinA = 0;
			rotateCosA = 0;
		}

	}

	private void hitRotate(Bubble bubble) {
		float distance = Common
				.distance(bubble.x, MainView.screenH-bubble.y, center_x, center_y);
		float force = VectorUtil.calCosTwoVector(bubble.dx, bubble.dy,
				center_x, 0);
		rotateSpeed = distance * force / 2000;
	}

	private void genInitBubble(float x, float y, int totals, float r) {
		float dAngle = (float) ((2 * Math.PI) / totals);
		float angle = 0.0f;
		for (int i = 0; i < totals; i++) {
			Bubble newBubble = new Bubble(context,context.mContent, center_x
					+ (float) (r * Math.cos(angle)), center_y
					+ (float) (r * Math.sin(angle)), GenBubble.genColor());
			root.add(newBubble);
			angle += dAngle;
		}
	}

	/**
	 * 恢复形状
	 * 
	 * @param bubble
	 * @param hitBubble
	 */
	private void hitRelocation(Bubble bubble, Bubble hitBubble) {
		float back_dx = -bubble.dx;
		float back_dy = -bubble.dy;
		float deltaX = hitBubble.x - bubble.x;
		float deltaY = hitBubble.y - bubble.y;
		float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		if (bubble.radius + hitBubble.radius > distance) {
			float len = Math.abs(bubble.radius + hitBubble.radius - distance);
			float lens = (float) Math.sqrt(back_dx * back_dx + back_dy
					* back_dy);
			bubble.x = bubble.x + len * (back_dx / lens);
			bubble.y = bubble.y + len * (back_dy / lens);
		}
	}

	/**
	 * +(p1.y-p0.y) * cos(a) + (p1.x-p0.x) * sin(a);
	 * 
	 * @param bubble
	 * @param centerX
	 * @param centerY
	 */
//	private void rotateItem(Bubble bubble) {
//		float x = bubble.x;
//		float y = bubble.y;
//		float sinA = (float) Math.sin(dRotate);
//		float cosA = (float) Math.cos(dRotate);
//		float newX = center_x + (x - center_x) * cosA - (y - center_y) * sinA;
//		float newY = center_y + (y - center_y) * cosA + (x - center_x) * sinA;
//		bubble.setX(newX);
//		bubble.setY(newY);
//	}

	private void rotateItem(Bubble bubble, double angle) {
		float deltaX = bubble.x - center_x, deltaY = bubble.y - center_y;
		float newX = center_x + deltaX * rotateCosA - deltaY * rotateSinA;
		float newY = center_y + deltaY * rotateCosA + deltaX * rotateSinA;
		bubble.setX(newX);
		bubble.setY(newY);
	}

	private void rotateAllWithAngle(double angle) {
		for (Bubble bubble : root) {
			rotateItem(bubble, angle);
		}// end for
	}

	private boolean isBubbleHit(final Bubble moveBubble, final Bubble bubble) {
		return Common.isCircleHit(moveBubble.x, moveBubble.y,
				moveBubble.radius, bubble.x, bubble.y, bubble.radius);
	}

	public void addBubble(Bubble bubble) {
		root.add(bubble);
	}
}// end class
