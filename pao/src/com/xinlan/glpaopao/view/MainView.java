package com.xinlan.glpaopao.view;

import com.xinlan.base.components.MainRender;
import com.xinlan.glpaopao.MainActivity;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class MainView extends GLSurfaceView implements Runnable {
	private MainRender mRender;
	private Thread mThread;

	public static int screenH, screenW;

	public MainView(Context context) {
		super(context);
		screenW = MainActivity.screenW;
		screenH = MainActivity.screenH;
		mRender = new MainRender();
		this.setRenderer(mRender);
		mThread = new Thread(this);
		mThread.start();
	}

	private void initGame() {
		// Bitmap
		// bgBitmap=XinlanUtils.loadBitmapFromAssets("game_background_layer_3.png",
		// mContext);
		// mBackgroud = new Background(bgBitmap,screenW,screenH);
		// mRender.addMesh(mBackgroud.getMesh());
		//
		// mWave = new Waves(XinlanUtils.loadBitmapFromAssets("waves.png",
		// mContext),
		// screenW,screenH);
		// mRender.addMesh(mWave.getMesh());
	}

	private void gameMain() {
		// mBackgroud.update();
		// mWave.update();
	}

	@Override
	public void run() {
		initGame();
		while (MainActivity.isRunning) {
			long starttime = System.currentTimeMillis();
			gameMain();
			long timeForOneCycle = System.currentTimeMillis() - starttime;
			if (timeForOneCycle < 5) {
				try {
					Thread.sleep(5 - timeForOneCycle);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}// end if
		}
	}
}// end class
