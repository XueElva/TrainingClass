package com.xue.trainingclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.tool.CommonTools;

public class CompleteInfoActivity extends Activity {
	private TextView mBack;
	private EditText mStoreName, mStoreIntroduction;
	private Spinner mProvince, mCity1, mCity2;
	private Button mConfirm;
    private boolean mBecomeToSellerSucceed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_completeinfo);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBack=(TextView) findViewById(R.id.back);
		mStoreName = (EditText) findViewById(R.id.storeName);
		mProvince = (Spinner) findViewById(R.id.province);
		mCity1 = (Spinner) findViewById(R.id.city1);
		mCity2 = (Spinner) findViewById(R.id.city2);
		mStoreIntroduction = (EditText) findViewById(R.id.storeIntroduction);
		mConfirm = (Button) findViewById(R.id.confirm);

		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		mConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String storeName=mStoreName.getText().toString();
				if(storeName.equals("")){
					Toast.makeText(CompleteInfoActivity.this, getResources().getString(R.string.inputStoreName), 1).show();
				}else{
					
					CommonTools.createLoadingDialog(CompleteInfoActivity.this).show();
					User currentUser=BmobUser.getCurrentUser(CompleteInfoActivity.this, User.class);
					User newUser=new User();
					newUser.setStoreName(storeName);
					newUser.setIsSeller(true);
					newUser.setCity("0,0,0");
					newUser.setStoreIntroduction(mStoreIntroduction.getText().toString());
					
					newUser.update(CompleteInfoActivity.this, currentUser.getObjectId(), new UpdateListener() {
						
						@Override
						public void onSuccess() {
							CommonTools.cancleDialog();
							//成功成为商家
							mBecomeToSellerSucceed=true;
							startActivity(new Intent(CompleteInfoActivity.this,BecomeToSellerResultActivity.class));
							finish();
							
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							CommonTools.cancleDialog();
							Toast.makeText(CompleteInfoActivity.this, arg1, 1).show();
						}
					});
				}
			}
		});
	}
	
	@Override
	public void finish() {
		if(mBecomeToSellerSucceed){
			setResult(Constant.MODIFY_USERINFO_CODE);
		}
		super.finish();
	}
}
