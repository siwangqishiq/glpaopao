package com.xinlan.glpaopao.components;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.xinlan.base.components.XLDrawable;
import com.xinlan.glpaopao.R;
import com.xinlan.glpaopao.view.MainView;
import com.xinlan.utils.VectorUtil;

public class Arrow {
	private MainView context;
	private Bitmap bitmap;
	private float x, y;
	public float degrees;
	public XLDrawable mImage;

	public Arrow(MainView context) {
		this.context = context;
		init();
	}

	public void init() {
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.arrow);
		x = MainView.screenW / 2;
		y = MainView.screenH-Bubble.RADIUS;
		mImage = new XLDrawable(x, y, 0, bitmap.getWidth(), bitmap.getHeight());
		mImage.rotateCenterX=0;
		mImage.rotateCenterY=-bitmap.getHeight()/2;
		mImage.loadBitmap(bitmap, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		float textureCoordinates[] = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, };// 映射坐标
		mImage.setTextureCoordinates(textureCoordinates);
		this.mImage.shouldBeDrawn = false;// 默认不显示
		context.mRender.addMesh(mImage);// 讲此对象加入绘制流水
	}

	public void setIsShow(boolean isShow) {
		this.mImage.shouldBeDrawn = isShow;
	}

	public void resetDegrees(float pointx, float pointy, float x) {
		float angle = VectorUtil.calCosTwoVectorAngle(pointx, pointy, x,
				0);
		degrees = (float) (angle * (180 / Math.PI));
		mImage.rz = -degrees;
	}
}// end class
