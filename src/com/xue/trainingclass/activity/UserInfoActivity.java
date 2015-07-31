package com.xue.trainingclass.activity;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DeleteFileListener;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.ThumbnailListener;
import com.bmob.btp.callback.UploadListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.tool.CommonTools;
import com.xue.trainingclass.tool.DataFormat;
import com.xue.trainingclass.tool.FileSizeUtil;
import com.xue.trainingclass.tool.MD5;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UserInfoActivity extends Activity implements OnClickListener {

	private TextView mBack, mEditor;
	private LinearLayout mIsVIP;
	private ImageView mAvatar;
	private ImageView mTag;
	private TextView mModifyPass;
	private EditText mUserName, mEmail, mStoreName;
	private RadioGroup mSexRadio;
	private RadioButton mMale, mFemale;
	private Spinner mProvince, mCity1, mCity2;
	private Button mConfirm;
	private TextView mAttention;
	// 商家需填写的
	private LinearLayout mStoreNameLin, mAreaLin;
	private String mSex = Constant.MALE;
	private boolean mEditable = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_userinfo);

		initView();
	}

	private void initView() {

		mBack = (TextView) findViewById(R.id.back);
		mEditor = (TextView) findViewById(R.id.editor);
		mIsVIP = (LinearLayout) findViewById(R.id.isVIP);
		mTag = (ImageView) findViewById(R.id.tag);
		mAvatar = (ImageView) findViewById(R.id.avatar);
		mModifyPass = (TextView) findViewById(R.id.modifyPass);
		mUserName = (EditText) findViewById(R.id.E_userName);
		mEmail = (EditText) findViewById(R.id.E_email);
		mStoreName = (EditText) findViewById(R.id.E_storeName);
		mSexRadio = (RadioGroup) findViewById(R.id.R_sexGroup);
		mMale = (RadioButton) findViewById(R.id.R_male);
		mFemale = (RadioButton) findViewById(R.id.R_female);
		mProvince = (Spinner) findViewById(R.id.Spin_province);
		mCity1 = (Spinner) findViewById(R.id.Spin_city);
		mCity2 = (Spinner) findViewById(R.id.Spin_city2);
		mConfirm = (Button) findViewById(R.id.confirm);
		mStoreNameLin = (LinearLayout) findViewById(R.id.Lin_storeName);
		mAreaLin = (LinearLayout) findViewById(R.id.Lin_area);
		mAttention = (TextView) findViewById(R.id.attention);

		mBack.setOnClickListener(this);
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
		mEditor.setOnClickListener(this);
		mConfirm.setOnClickListener(this);
		mAvatar.setOnClickListener(this);
		mModifyPass.setOnClickListener(this);
		mModifyPass.setOnClickListener(this);

		showUserInfo();

		setEditable();
	}

	// 显示用户信息
	private void showUserInfo() {
		User user = BmobUser.getCurrentUser(UserInfoActivity.this, User.class);

		if (null == user.getAvatar() || user.getAvatar().equals("")) {
			// 默认头像
			mAvatar.setBackgroundResource(R.drawable.icon_defavatar);
		} else {
			// 获取头像
			BmobProFile.getInstance(UserInfoActivity.this).download(
					user.getAvatar(), new DownloadListener() {

						@Override
						public void onError(int arg0, String arg1) {
							// 默认头像
							mAvatar.setBackgroundResource(R.drawable.icon_defavatar);
						}

						@Override
						public void onSuccess(String fullPath) {
							// TODO Auto-generated method stub
							Bitmap bit = BitmapFactory.decodeFile(fullPath);
							BitmapDrawable drawable = new BitmapDrawable(bit);
							mAvatar.setBackgroundDrawable(drawable);
						}

						@Override
						public void onProgress(String arg0, int arg1) {
							// TODO Auto-generated method stub

						}
					});

		}

		mUserName.setText(user.getUsername());
		if (user.getSex().equals(Constant.FEMALE)) {
			mFemale.setChecked(true);
		} else {
			mMale.setChecked(true);
		}
		mEmail.setText(user.getEmail());

		if (!user.getIsSeller()) {
			mStoreNameLin.setVisibility(View.GONE);
			mAreaLin.setVisibility(View.GONE);
			mIsVIP.setVisibility(View.GONE);
			mTag.setBackgroundResource(R.drawable.icon_tag_user);
		} else {
			mTag.setBackgroundResource(R.drawable.icon_tag_store);
			mStoreName.setText(user.getStoreName());
			mIsVIP.setVisibility(View.VISIBLE);
			if (user.getIsVIP()) {
				mIsVIP.setSelected(true);
			} else {
				mIsVIP.setSelected(false);
			}
		}
	}

	// 确认修改用户信息
	private void confirmModify() {
		User currentUser = BmobUser.getCurrentUser(UserInfoActivity.this,
				User.class);
		if (mUserName.getText().length() == 0) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.inputUserName), 1).show();
		} else if (mEmail.getText().length() == 0) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.inputEmail), 1).show();
		} else if (currentUser.getIsSeller()
				&& mStoreName.getText().length() == 0) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.inputStoreName), 1)
					.show();
		} else if (!DataFormat.isEmail(mEmail.getText().toString())) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.emailFormatError), 1)
					.show();
		} else {
			CommonTools.createLoadingDialog(UserInfoActivity.this).show();
			// 修改
			User user = new User();
			user.setUsername(mUserName.getText().toString());
			user.setSex(mSex);
			user.setEmail(mEmail.getText().toString());

			if (currentUser.getIsSeller()) {
				user.setStoreName(mStoreName.getText().toString());
				user.setCity("0,0,0");
			}

			user.update(UserInfoActivity.this, currentUser.getObjectId(),
					new UpdateListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							CommonTools.cancleDialog();
							Toast.makeText(
									UserInfoActivity.this,
									getResources().getString(
											R.string.userinfoModifySucceed), 1)
									.show();
							mEditor.performClick();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							CommonTools.cancleDialog();
							if (arg1.contains("username")
									&& arg1.contains("already taken")) {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.registerFail)
												+ getResources().getString(
														R.string.usernameExist),
										1).show();
							} else if (arg1.contains("email")
									&& arg1.contains("already taken")) {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.registerFail)
												+ getResources().getString(
														R.string.emailExist), 1)
										.show();
							} else {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.registerFail)
												+ arg1, 1).show();
							}
						}
					});

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editor: // 编辑
			if (!mEditable) {
				mEditor.setText(getResources().getString(R.string.cancel));
			} else {
				mEditor.setText(getResources().getString(R.string.edit));
			}
			mEditable = !mEditable;
			setEditable();
			break;
		case R.id.modifyPass: // 修改密码
            startActivity(new Intent(UserInfoActivity.this,PassResetActivity.class));
			break;
		case R.id.back:
			finish();
			break;
		case R.id.avatar: // 切换头像
			getImageFromAlbum();
			break;
		case R.id.confirm:
			confirmModify();
			break;
		default:
			break;
		}

	}

	File sdcardTempFile;

	// 从相册获取图片
	private void getImageFromAlbum() {
		sdcardTempFile = new File("/mnt/sdcard/", "tmp_pic_"
				+ SystemClock.currentThreadTimeMillis() + ".jpg");
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		intent.putExtra("output", Uri.fromFile(sdcardTempFile));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);// 输出图片大小
		intent.putExtra("outputY", 100);
		startActivityForResult(intent, Constant.GET_PIC_FROM_ALBUM);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constant.GET_PIC_FROM_ALBUM) {
			CommonTools.createLoadingDialog(UserInfoActivity.this).show();
//			Uri uri = data.getData();
//			final String filePath = getImagPath(uri);
			final String filePath=sdcardTempFile.getAbsolutePath();
			File file = new File(filePath);
			if (file.exists() && null!=data) {
				// 判断文件大小
				if (FileSizeUtil.getFileOrFilesSize(filePath,
						FileSizeUtil.SIZETYPE_MB) > 1) {
					// 大于1兆
					Toast.makeText(UserInfoActivity.this,
							getResources().getString(R.string.imgTooBig), 1)
							.show();

				} else {
					// 更换头像
					changeAvatar(filePath);
				}

			}else{
				CommonTools.cancleDialog();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 更改本地及数据库头像
	private void changeAvatar(final String filePath) {

		BmobProFile.getInstance(UserInfoActivity.this).upload(filePath,
				new UploadListener() {

					@Override
					public void onError(int statuscode, String errormsg) {
						// TODO Auto-generated method stub
						CommonTools.cancleDialog();
					}

					@Override
					public void onSuccess(final String fileName, String url,
							BmobFile file) {
						// 上传头像成功
						// 首先删除原来的头像
						final User currentUser = BmobUser.getCurrentUser(
								UserInfoActivity.this, User.class);
						if (null != currentUser.getAvatar()
								&& !currentUser.getAvatar().equals("")) {
							BmobProFile.getInstance(UserInfoActivity.this)
									.deleteFile(currentUser.getAvatar(),
											new DeleteFileListener() {

												@Override
												public void onError(
														int errorcode,
														String errormsg) {
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onSuccess() {
													// TODO Auto-generated
													// method stub

												}
											});
						}
						// 得到缩略图名称
						BmobProFile.getInstance(UserInfoActivity.this)
								.submitThumnailTask(fileName, 1,
										new ThumbnailListener() {

											@Override
											public void onError(int arg0,
													String arg1) {
												// TODO Auto-generated method
												// stub
												CommonTools.cancleDialog();
											}

											@Override
											public void onSuccess(
													String thumbnailName,
													String thumbnailUrl) {
												// 删除原图
												BmobProFile
														.getInstance(
																UserInfoActivity.this)
														.deleteFile(
																fileName,
																new DeleteFileListener() {

																	@Override
																	public void onError(
																			int arg0,
																			String arg1) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub

																	}

																	@Override
																	public void onSuccess() {
																		// TODO
																		// Auto-generated
																		// method
																		// stub

																	}
																});
												// 更改数据库中头像名称
												User user = new User();
												user.setAvatar(thumbnailName);
												user.update(
														UserInfoActivity.this,
														currentUser
																.getObjectId(),
														new UpdateListener() {

															@Override
															public void onSuccess() {
																// 头像更改成功
																Bitmap bitmap = BitmapFactory
																		.decodeFile(filePath);
																BitmapDrawable drawable = new BitmapDrawable(
																		bitmap);
																mAvatar.setBackgroundDrawable(drawable);
																CommonTools
																		.cancleDialog();
															}

															@Override
															public void onFailure(
																	int arg0,
																	String arg1) {
																// TODO
																// Auto-generated
																// method stub
																CommonTools
																		.cancleDialog();
															}
														});
											}
										});

					}

					@Override
					public void onProgress(int progress) {
						// TODO Auto-generated method stub

					}
				});

	}

	// 获取图片路径
	private String getImagPath(Uri originalUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		// 好像是android多媒体数据库的封装接口，具体的看Android文档

		Cursor cursor = managedQuery(originalUri, proj, null, null, null);

		// 按我个人理解 这个是获得用户选择的图片的索引值

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		// 将光标移至开头 ，这个很重要，不小心很容易引起越界

		cursor.moveToFirst();

		// 最后根据索引值获取图片路径

		String path = cursor.getString(column_index);

		return path;
	}

	// 设置是否可编辑
	private void setEditable() {
		mUserName.setEnabled(mEditable);
		mMale.setEnabled(mEditable);
		mFemale.setEnabled(mEditable);
		mEmail.setEnabled(mEditable);
		mStoreName.setEnabled(mEditable);
		mProvince.setEnabled(mEditable);
		mCity1.setEnabled(mEditable);
		mCity2.setEnabled(mEditable);

		if (mEditable) {
			mConfirm.setVisibility(View.VISIBLE);
			mAttention.setVisibility(View.VISIBLE);
		} else {
			mConfirm.setVisibility(View.GONE);
			mAttention.setVisibility(View.GONE);
		}

	}

	@Override
	public void finish() {
		setResult(Constant.MODIFY_USERINFO_CODE);
		super.finish();
	}
}
