package com.example.mygame.view;

import com.example.mygame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationView extends View {

	public AnimationView(Context context) {
		super(context);
	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Animation amin = null;
		if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
			amin = new AlphaAnimation(0.1f, 1f);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
			amin = new ScaleAnimation(0.0f, 2.0f, 1.5f, 1.5f, 			
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.0f);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
			amin = new TranslateAnimation(0, 100, 0, 100);
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
			amin = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		}
		amin.setDuration(3000);
		this.startAnimation(amin);
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		canvas.drawBitmap(bm, this.getWidth()/2-bm.getWidth()/2, 
								this.getHeight()/2-bm.getHeight()/2, null);
		super.onDraw(canvas);
	}
}
