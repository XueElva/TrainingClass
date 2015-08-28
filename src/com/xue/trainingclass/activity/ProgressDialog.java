package com.xue.trainingclass.activity;

import java.io.Serializable;

import com.xue.trainingclass.event.FinishEvent;
import com.xue.trainingclass.event.ProgressChangeEvent;
import com.xue.trainingclass.event.UploadSuccess;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.TextView;

public class ProgressDialog extends Activity {

	TextView mProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_progress);
		
		
		mProgress=(TextView) findViewById(R.id.progress);
		
		if(!EventBus.getDefault().isRegistered(this)){
			EventBus.getDefault().register(this);
		}
	}

	public void onEvent(ProgressChangeEvent event) {
		mProgress.setText("正在上传图片"+event.imgName+"："+event.progress+"%");
		if(event.progress==100){
			finish();
		}
	}
	
	public void onEvent(UploadSuccess event){
		finish();
	}
	
}
