package com.example.mygame.view;

import com.example.mygame.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class RectTimpactSurfaceView extends SurfaceView implements Callback {
	private static final String TAG = RectTimpactSurfaceView.class
			.getSimpleName();
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private Thread th;
	private boolean flag;
	// 定义两个矩形的宽高
	private int x1 = 20, y1 = 110, w1 = 80, h1 = 80;
	private int x2 = 200, y2 = 110, w2 = 80, h2 = 80;
	// 圆的半径
	private int r1 = 20, r2 = 20;
	//自定义撞击矩形
	private Rect rect = new Rect(0, 0, 50, 50);
	//region实例
	private Region region = new Region(rect);
	
	// 判断是否发生撞击
	private boolean isCollsion;
	// 下标
	private int index = 0;

	public void setIndex(int index) {
		this.index = index;
	}

	public RectTimpactSurfaceView(Context context) {
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
				if (isCollsion) {
					paint.setColor(Color.RED);
					paint.setTextSize(30f);
					canvas.drawText("砸！砸！砸！", 0, 30, paint);
				} else {
					paint.setColor(Color.WHITE);
				}
				if (index == 0) {
					// 绘制两个矩形
					canvas.drawRect(x1, y1, x1 + w1, y1 + h1, paint);
					canvas.drawRect(x2, y2, x2 + w2, y2 + h2, paint);
				} else if (index == 1) {
					// 绘制两个圆形
					canvas.drawCircle(x1, y1, r1, paint);
					canvas.drawCircle(x2, y2, r2, paint);
				}else if(index == 2){
					if(isCollsion){
						canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yu1)
										, 100, 300, paint);
					}
					canvas.drawRect(rect, paint);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (null != canvas)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	private void logic() {

	}

	/**
	 * 触屏事件
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x1 = (int) (event.getX() - w1 / 2);
		y1 = (int) (event.getY() - h1 / 2);
		if (index == 0) {
			if (isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2)) {
				isCollsion = true;
			} else {
				isCollsion = false;
			}
		} else if (index == 1) {
			// 圆形
			if (isCollsionWithCircle()) {
				isCollsion = true;
			} else {
				isCollsion = false;
			}
		}else if(index==2){
			if(region.contains((int)event.getX(), (int)event.getY())){
				isCollsion = true;
			}else{
				isCollsion = false;
			}
		}
		return true;
	}

	// 矩形
	private boolean isCollsionWithRect(int x1, int y1, int w1, int h1, int x2,
			int y2, int w2, int h2) {
		if (x1 >= x2 && x1 >= x2 + w2) {// 当矩形1位于矩形2的左侧
			return false;
		} else if (x1 <= x2 && x1 + w1 <= x2) {// 当矩形1位于矩形2的右侧
			return false;
		} else if (y1 >= y2 && y1 >= y2 + h2) {// 当矩形1位于矩形2的上方
			return false;
		} else if (y1 <= y2 && y1 + h1 <= y2) {// 当矩形1位于矩形2的下方
			return false;
		}
		// 所有都不满足时，就是碰撞
		return true;
	}

	// 圆形
	private boolean isCollsionWithCircle() {
		//Math.sqrt()开平方
		//Math.pow(double x,double y)x的y次方
		if (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) <= (r1 + r2)) {
			return true;
		}
		// 所有都不满足时，就是碰撞
		return false;
	}
}
