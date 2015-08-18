package com.xue.trainingclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class RoleChoiceActivity extends Activity implements OnClickListener{
	
	private TextView mCommonUser,mSeller;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rolechoice);
		
		mCommonUser=(TextView) findViewById(R.id.commonUser);
		mSeller=(TextView) findViewById(R.id.seller);
		
		mCommonUser.setOnClickListener(this);
		mSeller.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(RoleChoiceActivity.this,RegisterActivity.class);
		switch (v.getId()) {
		case R.id.commonUser:
			intent.putExtra("isSeller", false);
			break;
		case R.id.seller:
			intent.putExtra("isSeller", true);
			break;
		default:
			break;
		}
		
		startActivity(intent);
		finish();
		
	};
}
