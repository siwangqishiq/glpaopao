package com.xinlan.glpaopao;

import com.xinlan.glpaopao.view.MainView;

import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

public class MainActivity extends Activity {
	public PowerManager.WakeLock wakeLock;
	public static boolean isRunning = false;
	public static int screenW, screenH;
	private Context mContext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
				"PowerLight");
		wakeLock.acquire();//请求屏幕常亮
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		initScreenHW();
		isRunning = true;
		setContentView(new MainView(this));
    }
    
    private void initScreenHW(){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		screenW = display.getWidth();
		screenH = Math.abs(rectgle.top - rectgle.bottom);
	}
    
    @Override
	protected void onDestroy() {
		isRunning = false;
		wakeLock.release();
		System.gc();
		super.onDestroy();
	}

	@Override
	public void onResume() {
		wakeLock.acquire();
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onRestart() {
		super.onRestart();
	}

	@Override
	public void onPause() {
		wakeLock.release();
		super.onPause();
	}
}//end class
