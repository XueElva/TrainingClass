package com.xue.trainingclass.activity;

import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.event.ChangePageEvent;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener{

	private TextView mBack;
	private TextView mMessageSet,mCheckUpdate;
	private Button mLogout;
	private boolean logout=false;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		
		mBack=(TextView) findViewById(R.id.back);
		mMessageSet=(TextView) findViewById(R.id.messageSet);
		mCheckUpdate=(TextView) findViewById(R.id.checkUpdate);
		mLogout=(Button) findViewById(R.id.logout);
		
		mBack.setOnClickListener(this);
		mMessageSet.setOnClickListener(this);
		mCheckUpdate.setOnClickListener(this);
		mLogout.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.logout:
			BmobUser.logOut(SettingActivity.this);
			Toast.makeText(SettingActivity.this, getResources().getString(R.string.logoutSucceed), 1).show();
			logout=true;
			finish();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	public void finish() {
		Intent intent=new Intent();
		intent.putExtra("logout", logout);
		setResult(Constant.LOGOUT_CODE, intent);
		super.finish();
	}
}
