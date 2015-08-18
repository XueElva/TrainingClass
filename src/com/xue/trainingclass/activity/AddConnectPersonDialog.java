package com.xue.trainingclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xue.trainingclass.bean.Constant;

public class AddConnectPersonDialog extends Activity {
	private EditText mNameET, mPhoneET;
	private Button mAdd, mCancel;
	private String mName, mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_addconnectperson);

		mNameET = (EditText) findViewById(R.id.add_name);
		mPhoneET = (EditText) findViewById(R.id.add_phone);
		mAdd = (Button) findViewById(R.id.add_confirm);
		mCancel = (Button) findViewById(R.id.add_cancel);

		mAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mNameET.getText().length() == 0) {
					Toast.makeText(AddConnectPersonDialog.this,
							getResources().getString(R.string.inputName), 1)
							.show();
				} else if (mPhoneET.getText().length() == 0) {
					Toast.makeText(AddConnectPersonDialog.this,
							getResources().getString(R.string.inputPhone), 1)
							.show();
				} else {
					mName = mNameET.getText().toString();
					mPhone = mPhoneET.getText().toString();
					finish();
				}
			}
		});

		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
	
	@Override
	public void finish() {
		if(mName!=null){
			Intent intent=new Intent();
			intent.putExtra("name", mName);
			intent.putExtra("phone", mPhone);
			setResult(Constant.ADD_CONNECTPERSON, intent);
		}
		super.finish();
	}
}
