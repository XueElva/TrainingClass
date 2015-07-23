package com.xue.trainingclass.activity;

import android.app.Activity;
import android.view.Window;

public class PassResetActivity extends Activity {

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_passreset);
	};
}
