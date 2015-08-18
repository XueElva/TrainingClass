package com.xue.trainingclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.event.RegisterSucceedEvent;
import com.xue.trainingclass.tool.CommonTools;
import com.xue.trainingclass.tool.DataFormat;

import de.greenrobot.event.EventBus;

public class LoginActivity extends Activity implements OnClickListener {

	public static final String USERNAME_OR_PASSWORD_INCORRECT = "username or password incorrect";
	private EditText mUserName, mPassword;
	private TextView mRegister, mResetPass;
	private Button mLogin;
	private CheckBox mRememberPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		mUserName = (EditText) findViewById(R.id.userName);
		mPassword = (EditText) findViewById(R.id.password);
		mRememberPass = (CheckBox) findViewById(R.id.rememberPassword);
		mRegister = (TextView) findViewById(R.id.regist);
		mResetPass = (TextView) findViewById(R.id.forgetPass);
		mLogin = (Button) findViewById(R.id.login);

		mRegister.setOnClickListener(this);
		mResetPass.setOnClickListener(this);
		mLogin.setOnClickListener(this);
		
		final SharedPreferences sp = getSharedPreferences(Constant.USER_INFO_SP,
				Activity.MODE_PRIVATE);
		mUserName.setText(sp.getString(Constant.USERNAME, ""));
		
		if (getSharedPreferences(Constant.USER_SET_SP, Activity.MODE_PRIVATE)
				.getBoolean(Constant.REMEMBER_PASS+sp.getString(Constant.USERNAME, ""), false)) {
			mPassword.setText(sp.getString(Constant.PASSWORD+sp.getString(Constant.USERNAME, ""), ""));
			mRememberPass.setChecked(true);
		}
		
		
		mUserName.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus && mUserName.getText().length()>0){
					mPassword.setText("");
					mRememberPass.setChecked(false);
					if(getSharedPreferences(Constant.USER_SET_SP, Activity.MODE_PRIVATE)
				.getBoolean(Constant.REMEMBER_PASS+mUserName.getText().toString(), false)){
						mRememberPass.setChecked(true);
						mPassword.setText(sp.getString(Constant.PASSWORD+mUserName.getText().toString(), ""));
					}
				}
			}
		});

		if(!EventBus.getDefault().isRegistered(this)){
			EventBus.getDefault().register(this);
		}
		
	}
	//注册成功，进入主界面
	public void onEvent(RegisterSucceedEvent event) {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.forgetPass: // 忘记密码
			startActivity(new Intent(LoginActivity.this,
					PassResetActivity.class));
			break;
		case R.id.regist:
			startActivity((new Intent(LoginActivity.this,
					RoleChoiceActivity.class)));
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
				CommonTools.createLoadingDialog(LoginActivity.this).show();

				if (DataFormat.isEmail(mUserName.getText().toString())) {
					// 邮箱登录
					BmobUser.loginByAccount(LoginActivity.this, mUserName
							.getText().toString(), mPassword.getText()
							.toString(), new LogInListener<User>() {

						@Override
						public void done(User user, BmobException arg1) {
							// TODO Auto-generated method stub
							if (user != null) {
								loginSuccess();
							} else {
								CommonTools.cancleDialog();

								if (arg1.toString().contains(
										USERNAME_OR_PASSWORD_INCORRECT)) {
									Toast.makeText(
											LoginActivity.this,
											getResources().getString(
													R.string.loginFail)
													+ getResources()
															.getString(
																	R.string.usernameOrPassIncorrect),
											1).show();
								} else {
									Toast.makeText(
											LoginActivity.this,
											getResources().getString(
													R.string.loginFail)
													+ arg1, 1).show();
								}

							}
						}
					});

				} else {
					User user = new User();
					user.setUsername(mUserName.getText().toString());
					user.setPassword(mPassword.getText().toString());
					user.login(LoginActivity.this, new SaveListener() {

						@Override
						public void onSuccess() {
							loginSuccess();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							CommonTools.cancleDialog();
							Toast.makeText(
									LoginActivity.this,
									getResources()
											.getString(R.string.loginFail)
											+ arg1, 1).show();
						}
					});
				}

			}

			break;
		default:
			break;
		}

	}
	

	private void loginSuccess() {
		CommonTools.cancleDialog();
		Toast.makeText(LoginActivity.this,
				getResources().getString(R.string.loginSucceed), 1).show();

		// 保存用户名、密码
		SharedPreferences.Editor editor = getSharedPreferences(
				Constant.USER_INFO_SP, Activity.MODE_PRIVATE).edit();
		editor.putString(Constant.USERNAME, mUserName.getText().toString());
		
		SharedPreferences.Editor editor2 = getSharedPreferences(
				Constant.USER_SET_SP, Activity.MODE_PRIVATE).edit();
		if (mRememberPass.isChecked()) {
			editor.putString(Constant.PASSWORD+mUserName.getText().toString(), mPassword.getText().toString());
			
			editor2.putBoolean(Constant.REMEMBER_PASS+mUserName.getText().toString(), true).commit();

		} else {
			editor2.putBoolean(Constant.REMEMBER_PASS+mUserName.getText().toString(), false).commit();
		}
		editor.commit();
		startActivity(new Intent(LoginActivity.this, MainActivity.class));
		finish();
	}
}
