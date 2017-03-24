package com.example.mygame.view;

import com.example.mygame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MoveSurfaceView extends SurfaceView implements Callback {
	private static final String TAG = MoveSurfaceView.class.getSimpleName();
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private Thread th;
	private boolean flag;
	// 鱼的位图
	Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.yu1);
	// 方向常量
	final int DIR_RIGHT = 0;
	final int DIR_LEFT = 0;
	// 当前方向（默认朝右走）
	int dir = DIR_RIGHT;
	// 动作帧
	int currentFrame;
	// 鱼的想x，y的位置
	int _x, _y;
	// 上下左右
	private boolean isUp, isDown, isRiht, isLeft;

	public MoveSurfaceView(Context context) {
		super(context);
		// 初始化SurfaceHolder
		sfh = getHolder();
		// SurfaceHolder添加监听
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		// 设置焦点
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		flag = true;
		th = new Thread() {
			@Override
			public void run() {
				while (flag) {
					long start = System.currentTimeMillis();
					myDraw();
					logic();
					long end = System.currentTimeMillis();
					if (end - start < 50) {
						try {
							Thread.sleep(50 - (end - start));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				super.run();
			}
		};
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;

	}

	private void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (null != canvas) {
				canvas.drawRGB(0, 0, 0);
				drawFrame(currentFrame, canvas, paint);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (null != canvas)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	private void drawFrame(int currentFrame, Canvas canvas, Paint paint) {
		int frameW = bm.getWidth() / 6;
		int frameH = bm.getHeight() / 2;
		// 得到位图的列数
		int col = bm.getWidth() / frameW;
		// 得到当前帧相对的x坐标
		int x = -currentFrame % col * frameW;
		// 得到当前帧相对的y坐标
		int y = -currentFrame / col * frameH;
		canvas.save();
		// 设置一个宽高与小鱼每帧大小相同的可视区域
		// canvas.clipRect(_x, _y, _x + bm.getWidth() / 6, _y + bm.getHeight() /
		// 2);
		// if (dir == DIR_LEFT) {// 如果是向左侧移动
		// canvas.scale(-1, 1, _x - x + bm.getHeight() / 2,
		// _y - y + bm.getWidth() / 2);
		// }
		canvas.drawBitmap(bm, _x - x, _y - y, paint);
		canvas.restore();
	}

	private void logic() {
		if (isUp) {
			_y -= 5;
		}
		if (isDown) {
			_y += 5;
		}
		if (isRiht) {
			_x += 5;
		}
		if (isLeft) {
			_x -= 5;
		}
	}

	/**
	 * 按下事件
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
			dir = DIR_LEFT;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRiht = true;
			dir = DIR_LEFT;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 抬起事件
	 * */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRiht = false;
		}
		return super.onKeyUp(keyCode, event);
	}
}
