package com.xue.trainingclass.activity;

import cn.bmob.v3.listener.SaveListener;

import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.tool.CommonTools;
import com.xue.trainingclass.tool.MD5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText mUserName, mPassword;
	private TextView mRegister, mResetPass;
	private Button mLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		mUserName = (EditText) findViewById(R.id.userName);
		mPassword = (EditText) findViewById(R.id.password);
		mRegister = (TextView) findViewById(R.id.regist);
		mResetPass = (TextView) findViewById(R.id.forgetPass);
		mLogin = (Button) findViewById(R.id.login);

		mRegister.setOnClickListener(this);
		mResetPass.setOnClickListener(this);
		mLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.forgetPass: // Íü¼ÇÃÜÂë
			startActivity(new Intent(LoginActivity.this,
					PassResetActivity.class));
			break;
		case R.id.regist:
			startActivity(new Intent(LoginActivity.this,
					RoleChoiceActivity.class));
			break;
		case R.id.login:
			if (mUserName.getText().length() == 0) {
				Toast.makeText(LoginActivity.this,
						getResources().getString(R.string.inputStoreName), 1)
						.show();
			} else if (mPassword.getText().length() == 0) {
				Toast.makeText(LoginActivity.this,
						getResources().getString(R.string.inputPassword), 1)
						.show();
			} else {
				CommonTools.createLoadingDialog(LoginActivity.this,
						getResources().getString(R.string.loading)).show();

				User user = new User();
				user.setUsername(mUserName.getText().toString());
				user.setPassword(MD5.GetMD5Code(mPassword.getText().toString()));
				user.login(LoginActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						CommonTools.cancleDialog();
						Toast.makeText(
								LoginActivity.this,
								getResources().getString(R.string.loginSucceed),
								1).show();
						startActivity(new Intent(LoginActivity.this,
								MainActivity.class));
						finish();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						CommonTools.cancleDialog();
						Toast.makeText(
								LoginActivity.this,
								getResources().getString(R.string.loginFail)
										+ arg1, 1).show();
					}
				});
			}

			break;
		default:
			break;
		}

	}
}
