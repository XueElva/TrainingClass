package com.xue.trainingclass.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

import com.xue.trainingclass.adapter.MenuAdapter;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.event.ChangePageEvent;
import com.xue.trainingclass.event.FinishEvent;
import com.xue.trainingclass.fragment.ChatFragment;
import com.xue.trainingclass.fragment.HomeFragment;
import com.xue.trainingclass.fragment.MeFragment;
import com.xue.trainingclass.fragment.MenuFragment;
import com.xue.trainingclass.fragment.MessageFragment;
import com.xue.trainingclass.fragment.HomeFragment.OnSelectClassListener;
import com.xue.trainingclass.fragment.MenuFragment.OnClassSelectedListener;
import com.xue.trainingclass.tool.CommonTools;

import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity implements
		OnSelectClassListener, OnClickListener,OnClassSelectedListener {

	public static final int PAGE_HOME=0;
	public static final int PAGE_CHAT=1;
	public static final int PAGE_MESSAGE=2;
	public static final int PAGE_ME=3;
	DrawerLayout mDrawerLayout;
	FrameLayout mMenuLayout;
	

	private LinearLayout mHome, mChat, mPublish, mMessage, mMe;
	private ImageView mIconHome,mIconChat,mIconMsg,mIconMe;
	private TextView mTextHome,mTextChat,mTextMsg,mTextMe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 初始化bmob
		Bmob.initialize(this, "3fd72dc811e0bbc06e0aba38364015ea");

	
		
		init();

		mHome.performClick();

		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this);
		}
	}

	public void onEvent(FinishEvent event) {
		finish();
	}
	
	public void onEvent(ChangePageEvent event) {
		switch (event.page) {
		case PAGE_HOME:
			mHome.performClick();
			break;
		case PAGE_CHAT:
			mChat.performClick();
			break;
		case PAGE_MESSAGE:
			mMessage.performClick();
			break;
		case PAGE_ME:
			mMe.performClick();
			break;
		default:
			break;
		}
	}

	public void init() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mMenuLayout=(FrameLayout) findViewById(R.id.menuLayout);
		FragmentTransaction ft = getSupportFragmentManager()
				.beginTransaction();
		ft.replace(R.id.menuLayout, new MenuFragment(this));
		ft.commit();
		mHome = (LinearLayout) findViewById(R.id.ll_home);
		mChat = (LinearLayout) findViewById(R.id.ll_chat);
		mPublish = (LinearLayout) findViewById(R.id.ll_publish);
		mMessage = (LinearLayout) findViewById(R.id.ll_message);
		mMe = (LinearLayout) findViewById(R.id.ll_me);
		
		mIconHome=(ImageView) findViewById(R.id.icon_home);
		mIconChat=(ImageView) findViewById(R.id.icon_chat);
		mIconMsg=(ImageView) findViewById(R.id.icon_msg);
		mIconMe=(ImageView) findViewById(R.id.icon_user);
		
		mTextHome=(TextView) findViewById(R.id.text_home);
		mTextChat=(TextView) findViewById(R.id.text_chat);
		mTextMsg=(TextView) findViewById(R.id.text_msg);
		mTextMe=(TextView) findViewById(R.id.text_user);

		mHome.setOnClickListener(this);
		mChat.setOnClickListener(this);
		mPublish.setOnClickListener(this);
		mMessage.setOnClickListener(this);
		mMe.setOnClickListener(this);

		// 禁止手势滑动
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		

	}

	@Override
	public void onClick(View v) {
		User user = BmobUser.getCurrentUser(MainActivity.this, User.class);
		if (!(v.getId() == R.id.ll_home) && user == null) {
			CommonTools.loginFirst(MainActivity.this);
		} else {
			switch (v.getId()) {
			case R.id.ll_home:
				setSelected(mIconHome,mTextHome);
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_content, new HomeFragment(this));
				ft.commit();
				break;
			case R.id.ll_chat:
				setSelected(mIconChat,mTextChat);
				FragmentTransaction ft1 = getSupportFragmentManager()
						.beginTransaction();
				ft1.replace(R.id.frame_content, new ChatFragment());
				ft1.commit();
				break;
			case R.id.ll_message:
				setSelected(mIconMsg,mTextMsg);
				FragmentTransaction ft2 = getSupportFragmentManager()
						.beginTransaction();
				ft2.replace(R.id.frame_content, new MessageFragment());
				ft2.commit();
				break;
			case R.id.ll_me:
				setSelected(mIconMe,mTextMe);
				FragmentTransaction ft3 = getSupportFragmentManager()
						.beginTransaction();
				ft3.replace(R.id.frame_content, new MeFragment());
				ft3.commit();
				break;
			case R.id.ll_publish:
				Intent intent = new Intent(MainActivity.this,
						PublishActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 设置选中项
	 * 
	 * @param v
	 */
	private void setSelected(View v1,View v2) {
		mIconHome.setSelected(false);
		mTextHome.setSelected(false);
		mIconChat.setSelected(false);
		mTextChat.setSelected(false);
		mIconMsg.setSelected(false);
		mTextMsg.setSelected(false);
		mIconMe.setSelected(false);
		mTextMe.setSelected(false);
		v1.setSelected(true);
		v2.setSelected(true);
	}

	// 打开分类
	@Override
	public void onSelectClass() {
		
		mDrawerLayout.openDrawer(mMenuLayout);

	}

	//选中分类
	@Override
	public void onClassSelected(String classid) {
		mDrawerLayout.closeDrawers();
	}

}
