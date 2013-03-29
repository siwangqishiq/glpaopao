package com.xinlan.glpaopao.components;

import java.util.ArrayList;

import com.xinlan.glpaopao.view.MainView;

import android.graphics.Canvas;
/**
 * 装载要消失的泡泡容器
 * 
 * @author Administrator
 * 
 */
public class DisappearContainer {
	private MainView context;
	private ArrayList<Bubble> list;
	private float dr = 0.01f;

	public DisappearContainer(MainView mainview) {
		context = mainview;
		list = new ArrayList<Bubble>();
	}

	public void logic() {
		for (int i = 0; i < list.size(); i++) {
			Bubble bubble = list.get(i);
			bubble.scale -= dr;
			bubble.mImage.scale=bubble.scale;
			if (bubble.scale <= 0) {
				list.remove(bubble);
				context.mRender.removeMesh(bubble.getMesh());
				context.groupBubbles.root.remove(bubble);
				System.gc();
			}
		}// end for i
	}

	public void addDisappearBubble(Bubble bubble) {
		list.add(bubble);
	}

}// end class
