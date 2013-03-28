package com.xinlan.glpaopao.components;

import javax.microedition.khronos.opengles.GL10;

import com.xinlan.base.components.XLDrawable;
import com.xinlan.glpaopao.view.MainView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Bubble {
	public static float RADIUS;

	private MainView context;
	public Bitmap bitmap;
	private BitmapDataContent dataContent;
	public float radius;
	public float x, y;
	public float dx, dy;
	private int color;

	private float width, height;
	private XLDrawable mImage;

	public static final int STATUS_NORMAL = 1;
	public static final int STATUS_DEAD = 2;;
	public int status;
	public float scale = 1.0f;

	public Bubble(MainView context, BitmapDataContent data, float x, float y,
			int color, float dx, float dy) {
		this.context = context;
		status = STATUS_NORMAL;
		radius = RADIUS;
		dataContent = data;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.color = color;
		width = 2 * radius;
		height = 2 * radius;
		loadBitmap();
	}

	public Bubble(MainView context, BitmapDataContent data, float x, float y,
			int color) {
		this(context, data, x, y, color, 0, 0);
	}

	public int getColor() {
		return color;
	}

	private void loadBitmap() {
		if (bitmap == null) {
			bitmap = dataContent.getBitmapByColor(color);
		}
		mImage = new XLDrawable(x - radius, y - radius, 0, width, height);
		mImage.loadBitmap(bitmap, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		float textureCoordinates[] = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, };// 映射坐标
		mImage.setTextureCoordinates(textureCoordinates);
		context.mRender.addMesh(mImage);// 讲此对象加入绘制流水
	}

	public void setSpeed(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setX(float x) {
		this.x = x;
		mImage.x = x - radius;
	}

	public void setY(float y) {
		this.y = y;
		mImage.y = y - radius;
	}

	public void moveUpdate() {
		x += dx;
		y += dy;
		mImage.x = x - radius;
		mImage.y = y - radius;
		
		if(x<=-radius || x>=MainView.screenW+radius || y<=-radius || y>=MainView.screenH+radius){
			context.genBubble.status=GenBubble.STATUS_CANLOAD;
		}
	}
}// end class
