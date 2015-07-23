package com.xue.trainingclass.fragment;

import cn.bmob.v3.BmobUser;

import com.xue.trainingclass.activity.MainActivity;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.activity.SettingActivity;
import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.event.ChangePageEvent;

import de.greenrobot.event.EventBus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MeFragment extends Fragment implements OnClickListener {

	private ImageView mSetting;
	private ImageView mAvatar;
	private TextView mUserName;
	
	//通用
	private TextView mMyCollection,mMyPublishChat,mFeedBack;
	//商家
	private LinearLayout mSellerLin;
	private TextView mMyPublishNews,mUpgradeToVIP,mPublishAdvertisement;
    //普通用户
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
		mAvatar=(ImageView) view.findViewById(R.id.avatar);
		mUserName=(TextView) view.findViewById(R.id.userName);
		mMyCollection=(TextView) view.findViewById(R.id.myCollection);
		mMyPublishChat=(TextView) view.findViewById(R.id.myPublishChat);
		mFeedBack=(TextView) view.findViewById(R.id.feedback);
		
		mSellerLin=(LinearLayout) view.findViewById(R.id.seller);
		mMyPublishNews=(TextView) view.findViewById(R.id.myPublish);
		mUpgradeToVIP=(TextView) view.findViewById(R.id.upgrade);
		mPublishAdvertisement=(TextView) view.findViewById(R.id.publishAdvertisement);
		
		mCommonUser=(LinearLayout) view.findViewById(R.id.commonUser);
		mBecomeToSeller=(TextView) view.findViewById(R.id.becomeToSeller);
		
		User user=BmobUser.getCurrentUser(null, User.class);
		if(user.getIsSeller()){
			mCommonUser.setVisibility(View.GONE);
		}else{
			mSellerLin.setVisibility(View.GONE);
		}
		
        mSetting.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mMyCollection.setOnClickListener(this);
        mMyPublishChat.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);
        mMyPublishNews.setOnClickListener(this);
        mUpgradeToVIP.setOnClickListener(this);
        mPublishAdvertisement.setOnClickListener(this);
        mBecomeToSeller.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting:
			startActivityForResult((new Intent(getActivity(), SettingActivity.class)),1);
			break;

		default:
			break;
		}

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if((resultCode==Constant.LOGOUT_CODE) && data.getBooleanExtra("logout", false)){
			EventBus.getDefault().post(new ChangePageEvent(MainActivity.PAGE_HOME));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
