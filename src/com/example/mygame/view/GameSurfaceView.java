package com.example.mygame.view;

import com.example.mygame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Callback {
	private static final String TAG = GameSurfaceView.class.getSimpleName();
	private SurfaceHolder sfh;// 用于控制SurfaceView
	private Paint paint;
	private int textX = 50, textY = 50;
	private Thread th;// 声明一个线程
	private boolean flag;// 线程消亡的标志
	private Canvas canvas;// 声明画布
	private int screeW, screeH;// 屏幕的宽高

	public GameSurfaceView(Context context) {
		super(context);
		// 实例SurfaceHolder
		sfh = getHolder();
		// SurfaceHolder添加监听
		sfh.addCallback(this);
		// 实例一个画笔
		paint = new Paint();
		paint.setColor(Color.WHITE);
		// 设置焦点
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		screeH = this.getHeight();
		screeW = this.getWidth();
		flag = true;
		// 实例化线程
		th = new Thread(){
			@Override
			public void run() {
				while (flag) {
					long start = System.currentTimeMillis();
					myDraw();
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
		// 启动线程
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;

	}

	// 自定义绘图方法
	private void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (null != canvas) {
				// canvas.drawRect(0, 0, this.getWidth(), this.getHeight(),
				// paint);
				//canvas.drawColor(Color.WHITE);
				canvas.drawRGB(0, 0, 0);
				canvas.drawText("GameFurface", textX, textY, paint);
				//绘制一条直线
				canvas.drawLine(10, 40, 50, 40, paint);
				//绘制多条直线
				canvas.drawLines(new float[]{10,50,50,50,70,50,110,50}, paint);
				//绘制矩形
				//canvas.drawRect(50, 100, 150, 200, paint);
				//绘制矩形2
				//Rect r = new Rect(150, 400, 450, 200);
				//canvas.drawRect(r, paint);
				//绘制圆角矩形
				RectF rect = new RectF(20,140,100,170);
				canvas.drawRoundRect(rect, 20, 20, paint);
				//绘制圆
				canvas.drawCircle(40, 220, 40, paint);
				//绘制圆弧
				RectF oval = new RectF(150,20,200,70);
				//canvas.drawArc(oval, 0, 270, true, paint);
				//绘制椭圆
				canvas.drawOval(oval, paint);
				//绘制指定路径的图形
				Path path = new Path();
				path.moveTo(160, 150);
				path.lineTo(200, 150);
				path.lineTo(180, 200);
				path.close();
				canvas.drawPath(path, paint);
				
				//绘制指定图形路径
				Path path2 = new Path();
				path2.addCircle(130, 260, 20,Path.Direction.CCW);
				canvas.drawTextOnPath("pathText", path2, 10, 20, paint);
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//				Path p = new Path();
//				p.addCircle(50, 50, 50, Path.Direction.CCW);
//				canvas.clipPath(p);
				Region re = new Region();
				/**
				 * UNION显示全部
				 * XOR 不显示交集区域
				 * INTERSECT显示交集区域
				 * */
				re.op(new Rect(0, 0, 100, 100), Region.Op.UNION);
				canvas.clipRegion(re);
				canvas.drawBitmap(bm, 0, 0, paint);
				canvas.restore();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (null != canvas) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		textX = (int) event.getX();
		textY = (int) event.getY();
		//myDraw();
		return true;
	}
}
