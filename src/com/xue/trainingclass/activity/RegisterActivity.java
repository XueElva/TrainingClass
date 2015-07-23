package com.xue.trainingclass.activity;

import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.tool.CommonTools;
import com.xue.trainingclass.tool.DataFormat;
import com.xue.trainingclass.tool.MD5;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText mUserName, mPassword, mConfirmPass, mEmail, mStoreName;
	private RadioGroup mSexRadio;
	private Spinner mProvince, mCity;
	private Button mRegister;
	// 商家需填写的
	private LinearLayout mStoreNameLin, mAreaLin;
	private boolean mIsSeller;
	private String mSex = Constant.MALE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		mIsSeller = getIntent().getBooleanExtra("isSeller", false);
		initView();

	}

	private void initView() {
		mUserName = (EditText) findViewById(R.id.E_userName);
		mPassword = (EditText) findViewById(R.id.E_pwd);
		mConfirmPass = (EditText) findViewById(R.id.E_pwdConfirm);
		mEmail = (EditText) findViewById(R.id.E_email);
		mStoreName = (EditText) findViewById(R.id.E_storeName);
		mSexRadio = (RadioGroup) findViewById(R.id.R_sexGroup);
		mProvince = (Spinner) findViewById(R.id.Spin_province);
		mCity = (Spinner) findViewById(R.id.Spin_city);
		mRegister = (Button) findViewById(R.id.register);
		mStoreNameLin = (LinearLayout) findViewById(R.id.Lin_storeName);
		mAreaLin = (LinearLayout) findViewById(R.id.Lin_area);

		if (!mIsSeller) {
			mStoreNameLin.setVisibility(View.GONE);
			mAreaLin.setVisibility(View.GONE);
		}

		mSexRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.R_female:
					mSex = Constant.FEMALE;
					break;
				case R.id.R_male:
					mSex = Constant.MALE;
					break;
				default:
					break;
				}
			}
		});
		mRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mUserName.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.inputUserName), 1)
							.show();
				} else if (mPassword.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.inputPassword), 1)
							.show();
				} else if (mEmail.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.inputEmail), 1)
							.show();
				} else if (mIsSeller && mStoreName.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.inputStoreName),
							1).show();
				} else if (!mPassword.getText().toString()
						.equals(mConfirmPass.getText().toString())) {
					Toast.makeText(
							getApplicationContext(),
							getResources().getString(R.string.confirmPassError),
							1).show();
				} else if (!DataFormat.isEmail(mEmail.getText().toString())) {
					Toast.makeText(
							getApplicationContext(),
							getResources().getString(R.string.emailFormatError),
							1).show();
				} else {
					CommonTools.createLoadingDialog(RegisterActivity.this,
							getResources().getString(R.string.loading)).show();
					// 注册
					User user = new User();
					user.setUsername(mUserName.getText().toString());
					user.setPassword(MD5.GetMD5Code(mPassword.getText()
							.toString()));
					user.setSex(mSex);
					user.setEmail(mEmail.getText().toString());
					user.setIsSeller(mIsSeller);
					
//					ImageView avatar=new ImageView(RegisterActivity.this);
////					Bitmap bit=BitmapFactory.decodeResource(getResources(), R.drawable.icon_user2);
////					Bitmap bit2=Bitmap.createBitmap(bit, 0, 0, 80, 80);
////					avatar.setImageBitmap(bit2);
//					avatar.setBackgroundResource(R.drawable.icon_user2);
//					user.setAvatar(avatar);
					if (mIsSeller) {
						user.setStoreName(mStoreName.getText().toString());
						user.setCity("0,0");
					}
				

					user.signUp(RegisterActivity.this, new SaveListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							CommonTools.cancleDialog();
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.registerSucceed), 1)
									.show();
							finish();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							CommonTools.cancleDialog();
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.registerFail)
											+ arg1, 1).show();
						}
						
						
					});

				}
			}
		});
	}
}
