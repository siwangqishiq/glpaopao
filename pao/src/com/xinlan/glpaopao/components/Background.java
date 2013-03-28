package com.xinlan.glpaopao.components;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;
import com.xinlan.base.components.Mesh;
import com.xinlan.base.components.XLDrawable;
import com.xinlan.glpaopao.view.MainView;

public class Background {
	private MainView context;
	private Bitmap mBitmap;
	private XLDrawable mImage;

	public Background(MainView context) {
		this.context = context;
	}

	public void loadImage(Bitmap _bitmap) {
		mBitmap = _bitmap;
		mImage = new XLDrawable(0, 0, 0, MainView.screenW, MainView.screenH);
		mImage.loadBitmap(mBitmap, GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		float textureCoordinates[] = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, };//映射坐标
		mImage.setTextureCoordinates(textureCoordinates);
	}

	public void logic() {
//		mImage.x -= 1;
//		if (mImage.x < -MainView.screenW * 2)
//			mImage.x = 0;
	}

	public Mesh getMesh() {
		return mImage;
	}
}// end class
