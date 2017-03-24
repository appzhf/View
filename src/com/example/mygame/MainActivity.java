package com.example.mygame;

import com.example.mygame.ui.ShowActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private int ids[] = { R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
			R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8 };
	private int index = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_main);
		for (int i = 0; i < ids.length; i++) {
			Button btn = (Button) findViewById(ids[i]);
			btn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn1:
			index = 1;
			break;
		case R.id.btn2:
			index = 2;
			break;
		case R.id.btn3:
			index = 3;
			break;
		case R.id.btn4:
			index = 4;
			break;
		case R.id.btn5:
			index = 5;
			break;
		case R.id.btn6:
			index = 6;
			break;
		case R.id.btn7:
			index = 7;
			break;
		case R.id.btn8:
			index = 8;
			break;

		}
		startActivity(new Intent(this, ShowActivity.class).putExtra("index",
				index));
	}
}
