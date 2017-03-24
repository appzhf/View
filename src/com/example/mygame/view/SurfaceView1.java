package com.example.mygame.view;

import com.example.mygame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class SurfaceView1 extends SurfaceView implements Callback {
	private static final String TAG = SurfaceView1.class.getSimpleName();
	private SurfaceHolder sfh;
	private Paint paint;
	private Canvas canvas;
	private Thread th;// 声明一个线程
	private boolean flag;

	
	int ids[] = { R.drawable.yu1, R.drawable.yu2, R.drawable.yu3};
	private Bitmap bm[] = new Bitmap[ids.length];
	private int index = 0;
	// 图的x，y坐标
	private int bmX, bmY;

	public SurfaceView1(Context context) {
		super(context);
		sfh = getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30f);
		
		// bm = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// bmX = -bm.getWidth()+this.getWidth();
		// bmY = bm.getHeight()-this.getHeight();
		for (int i = 0; i < ids.length; i++) {
			bm[i] = BitmapFactory.decodeResource(getResources(), ids[i]);
			bmX = -bm[i].getWidth() + this.getWidth();
			bmY = bm[i].getHeight() - this.getHeight();
		}
		// 设置焦点
		setFocusable(true);
	}
	
	public SurfaceView1(Context context, AttributeSet attrs) {
		super(context, attrs);
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
				canvas.save();
				canvas.drawRGB(0, 0, 0);
				canvas.drawBitmap(bm[index], bmX, bmY, paint);
				canvas.drawText("进度"+bmX, bmX, bmY, paint);
				canvas.restore();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (null != canvas)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	private void logic() {
		index++;
		if (index >= bm.length) {
			index = 0;
			if (bmX > this.getWidth()) {
				bmX = 0;
			}
		}
		bmX += 5;
	}
}
