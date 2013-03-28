package com.xinlan.glpaopao.view;

import com.xinlan.base.components.MainRender;
import com.xinlan.glpaopao.MainActivity;
import com.xinlan.glpaopao.components.Arrow;
import com.xinlan.glpaopao.components.Background;
import com.xinlan.glpaopao.components.BitmapDataContent;
import com.xinlan.glpaopao.components.Bubble;
import com.xinlan.glpaopao.components.GenBubble;
import com.xinlan.glpaopao.components.GroupBubbles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainView extends GLSurfaceView implements Runnable {
	public MainRender mRender;
	private Thread mThread;

	public static int screenH, screenW;

	// 控件
	public BitmapDataContent mContent;
	public Background mBackground;
	public GenBubble genBubble;
	public Arrow arrow;
	public GroupBubbles groupBubbles;

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
		Bubble.RADIUS = MainView.screenW / 25;

		mContent = new BitmapDataContent(this);
		mContent.loadImages();

		mBackground = new Background(this);
		mBackground.loadImage(mContent.getBgBitmap());
		mRender.addMesh(mBackground.getMesh());

		arrow =new Arrow(this);
		genBubble = new GenBubble(this);
		groupBubbles = new GroupBubbles(this);
		groupBubbles.init();
	}

	private void gameMain() {
		mBackground.logic();
		genBubble.logic();
		groupBubbles.logic();
	}

	@Override
	public void run() {
		initGame();
		while (MainActivity.isRunning) {
			long starttime = System.currentTimeMillis();
			gameMain();
			long timeForOneCycle = System.currentTimeMillis() - starttime;
			if (timeForOneCycle < 12) {
				try {
					Thread.sleep(12 - timeForOneCycle);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}// end if
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// genBubble.onTouchEvent(event);
		genBubble.onTouchEvent(event);
		return true;
	}
}// end class
