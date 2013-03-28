package com.xinlan.base.sound.components;

import com.xinlan.utils.Common;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * 播放背景音乐
 * 
 * @author Panyi
 * 
 */
public class BgmPlayer {
	private MediaPlayer mediaPlayer;

	public BgmPlayer(Context context) {
		int rand=Common.genRand(1, 2);
		if (rand == 1) {
			//mediaPlayer = MediaPlayer.create(context, R.raw.bg2);
		} else if (rand == 2){
			//mediaPlayer = MediaPlayer.create(context, R.raw.bg3);
		}
		mediaPlayer.setVolume(0.5f, 0.5f);
		mediaPlayer.setLooping(true);
	}

	public void playBmg() {
		mediaPlayer.start();
	}

	public void pauseBgm() {
		mediaPlayer.pause();
	}

	public void stopBgm() {
		mediaPlayer.stop();
	}
}// end class
