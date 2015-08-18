package com.xue.trainingclass.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.EmailVerifyListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.tool.CommonTools;
import com.xue.trainingclass.tool.DataFormat;

public class PassResetActivity extends Activity {

	private TextView mBack;
	private EditText mEmail;
	private Button mSendEmail;
	private User mCurrentUser;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_passreset);
		
		mBack=(TextView) findViewById(R.id.back);
		mEmail=(EditText) findViewById(R.id.E_email_resetPass);
		mSendEmail=(Button) findViewById(R.id.sendEmail);
		
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mCurrentUser=BmobUser.getCurrentUser(PassResetActivity.this, User.class);
		if(null!=mCurrentUser){
			//已登录
			mEmail.setText(mCurrentUser.getEmail());
			mEmail.setEnabled(false);
		}
		
		mSendEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			
				final String email=mEmail.getText().toString();
				if(email.equals("")){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.inputEmail), 1).show();
				}else if(!DataFormat.isEmail(email)){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.emailFormatError), 1).show();
				}else {
					
					CommonTools.createLoadingDialog(PassResetActivity.this);
					getCurrentUser();
				}
				
			}
		});
	};
	
	
	/**
	 * 获取最新用户信息(非本地缓存)
	 */
	public void getCurrentUser(){
		User user=BmobUser.getCurrentUser(PassResetActivity.this, User.class);
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("username", user.getUsername());
		query.findObjects(PassResetActivity.this, new FindListener<User>() {
		    @Override
		    public void onSuccess(List<User> object) {
		    	CommonTools.cancleDialog();
		    	mCurrentUser=(User) object.get(0);
		    	if(!mCurrentUser.getEmailVerified()){
					//邮箱未验证
					AlertDialog.Builder builder=new AlertDialog.Builder(PassResetActivity.this);
					builder.setTitle(getResources().getString(R.string.prompt));
					builder.setMessage(getResources().getString(R.string.emailHaveNotVerified));
					builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 发送验证邮件
							BmobUser.requestEmailVerify(PassResetActivity.this, mEmail.getText().toString(),  new EmailVerifyListener() {
								
								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO Auto-generated method stub
									
								}
							});
							dialog.dismiss();
						}
					});
					
					builder.create();
					builder.show();
				}else{
					// 发送邮件，修改密码
					BmobUser.resetPasswordByEmail(PassResetActivity.this, mEmail.getText().toString(), new ResetPasswordByEmailListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.sendEmailSucceed), 1).show();
							finish();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.sendEmailFail)+":"+arg1, 1).show();
						}
					});
				}
		    	
		    	
		    	
		    }
		    @Override
		    public void onError(int code, String msg) {
		    	CommonTools.cancleDialog();
		    }
		});
	}
}
