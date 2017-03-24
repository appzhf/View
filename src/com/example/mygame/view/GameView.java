package com.example.mygame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View{
	private int textX = 100,textY =100;
	
	public GameView(Context context) {
		super(context);
		setFocusable(true);
	}
	/**
	 * 重写按键按下事件
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//判断用户按下是否为方向键
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP){//上键
			textY -= 5;
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){//下键
			textY += 5;
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){//左键
			textX -= 5;
		}else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){//右键
			textX += 5;
		}
		//重绘
		invalidate();
		postInvalidate();
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 重写按键抬起事件
	 * */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}
	/**
	 * 重写触屏事件
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(event.getAction() == MotionEvent.ACTION_UP){//按下屏幕
			textX = x;
			textY = y;
		}else if(event.getAction() == MotionEvent.ACTION_DOWN){//抬起
			textX = x;
			textY = y;
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){//移动屏幕
			textX = x;
			textY = y;
		}
		//重绘
		invalidate();
		postInvalidate();
		return true;
	}

	/**
	 * 重写父类的onDraw方法
	 * */
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(30f);
		canvas.drawText("Game", textX, textY, paint);
		super.onDraw(canvas);
	}
}
