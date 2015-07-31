package com.xue.trainingclass.fragment;

import java.io.File;

import cn.bmob.v3.BmobUser;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xue.trainingclass.activity.CompleteInfoActivity;
import com.xue.trainingclass.activity.MainActivity;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.activity.SettingActivity;
import com.xue.trainingclass.activity.UserInfoActivity;
import com.xue.trainingclass.application.MyApplication;
import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.event.ChangePageEvent;

import de.greenrobot.event.EventBus;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MeFragment extends Fragment implements OnClickListener {

	private ImageView mSetting;
	private FrameLayout mAvatarLayout;
	private ImageView mAvatar, mTag;
	private TextView mUserName;
	private LinearLayout mIsVip;

	// 通用
	private TextView mMyCollection, mMyPublishChat, mFeedBack;
	// 商家
	private LinearLayout mSellerLin;
	private TextView mMyPublishNews, mUpgradeToVIP, mPublishAdvertisement;
	// 普通用户
	private LinearLayout mCommonUser;
	private TextView mBecomeToSeller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.fragment_me, null);

		initView(view);
		return view;
	}

	private void initView(View view) {
		mSetting = (ImageView) view.findViewById(R.id.setting);
		mAvatarLayout = (FrameLayout) view.findViewById(R.id.avatarLayout);
		mAvatar = (ImageView) view.findViewById(R.id.avatar);
		mTag = (ImageView) view.findViewById(R.id.tag);
		mUserName = (TextView) view.findViewById(R.id.userName);
		mIsVip = (LinearLayout) view.findViewById(R.id.isVIP);
		mMyCollection = (TextView) view.findViewById(R.id.myCollection);
		mMyPublishChat = (TextView) view.findViewById(R.id.myPublishChat);
		mFeedBack = (TextView) view.findViewById(R.id.feedback);
		mSellerLin = (LinearLayout) view.findViewById(R.id.seller);
		mMyPublishNews = (TextView) view.findViewById(R.id.myPublish);
		mUpgradeToVIP = (TextView) view.findViewById(R.id.upgrade);
		mPublishAdvertisement = (TextView) view
				.findViewById(R.id.publishAdvertisement);

		mBecomeToSeller = (TextView) view.findViewById(R.id.becomeToSeller);
		mCommonUser = (LinearLayout) view.findViewById(R.id.commonUser);

		showUserInfo();

		mAvatarLayout.setOnClickListener(this);
		mSetting.setOnClickListener(this);
		mUserName.setOnClickListener(this);
		mMyCollection.setOnClickListener(this);
		mMyPublishChat.setOnClickListener(this);
		mFeedBack.setOnClickListener(this);
		mMyPublishNews.setOnClickListener(this);
		mUpgradeToVIP.setOnClickListener(this);
		mPublishAdvertisement.setOnClickListener(this);
		mBecomeToSeller.setOnClickListener(this);
	}

	private void showUserInfo() {
		User user = BmobUser.getCurrentUser(getActivity(), User.class);

		if (user.getIsSeller()) {
			mCommonUser.setVisibility(View.GONE);
			mTag.setBackgroundResource(R.drawable.icon_tag_store);
			mIsVip.setVisibility(View.VISIBLE);
		} else {
			mSellerLin.setVisibility(View.GONE);
			mTag.setBackgroundResource(R.drawable.icon_tag_user);
			mIsVip.setVisibility(View.GONE);
		}
		mUserName.setText(user.getUsername());
		if (user.getIsVIP()) {
			mIsVip.setSelected(true);
		} else {
			mIsVip.setSelected(false);
		}

		if (null == user.getAvatar() || user.getAvatar().equals("")) {
			// 默认头像
			mAvatar.setBackgroundResource(R.drawable.icon_defavatar);
		} else {
			// 获取头像
			BmobProFile.getInstance(getActivity()).download(user.getAvatar(),
					new DownloadListener() {

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub
							// 默认头像
							mAvatar.setBackgroundResource(R.drawable.icon_defavatar);
						}

						@Override
						public void onSuccess(String fullPath) {
							// TODO Auto-generated method stub
							Bitmap bit=BitmapFactory.decodeFile(fullPath);
							BitmapDrawable drawable=new BitmapDrawable(bit);
							mAvatar.setBackgroundDrawable(drawable);
						}

						@Override
						public void onProgress(String arg0, int arg1) {
							// TODO Auto-generated method stub

						}
					});
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting:
			startActivityForResult((new Intent(getActivity(),
					SettingActivity.class)), 1);
			break;
		case R.id.userName:
		case R.id.avatarLayout:
		Intent intent=new Intent(getActivity(),UserInfoActivity.class);
		startActivityForResult(intent, 11);
		break;
		case R.id.becomeToSeller:
			startActivityForResult(new Intent(getActivity(),CompleteInfoActivity.class), 1);
			break;
		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((resultCode == Constant.LOGOUT_CODE)
				&& data.getBooleanExtra("logout", false)) {
			// 注销返回首页
			EventBus.getDefault().post(
					new ChangePageEvent(MainActivity.PAGE_HOME));
		} else if (resultCode == Constant.MODIFY_USERINFO_CODE) {
			showUserInfo();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
