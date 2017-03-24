package com.example.mygame.ui;

import com.example.mygame.view.AnimationView;
import com.example.mygame.view.GameSurfaceView;
import com.example.mygame.view.GameView;
import com.example.mygame.view.MoveSurfaceView;
import com.example.mygame.view.RectTimpactSurfaceView;
import com.example.mygame.view.SurfaceView1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class ShowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		int index = getIntent().getIntExtra("index", 0);
		if (index == 1) {
			setContentView(new GameView(this));
		} else if (index == 2) {
			setContentView(new GameSurfaceView(this));
		} else if (index == 3) {
			setContentView(new AnimationView(this));
		} else if (index == 4) {
			setContentView(new SurfaceView1(this));
		} else if (index == 5) {
			setContentView(new MoveSurfaceView(this));
		} else if (index == 6) {
			RectTimpactSurfaceView rsf = new RectTimpactSurfaceView(this);
			rsf.setIndex(0);
			setContentView(rsf);
		} else if (index == 7) {
			RectTimpactSurfaceView rsf = new RectTimpactSurfaceView(this);
			rsf.setIndex(1);
			setContentView(rsf);
		} else if (index == 8) {
			RectTimpactSurfaceView rsf = new RectTimpactSurfaceView(this);
			rsf.setIndex(2);
			setContentView(rsf);
		}
	}
}
