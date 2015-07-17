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

import com.xue.trainingclass.adapter.MenuAdapter;
import com.xue.trainingclass.fragment.ChatFragment;
import com.xue.trainingclass.fragment.HomeFragment;
import com.xue.trainingclass.fragment.MeFragment;
import com.xue.trainingclass.fragment.MessageFragment;
import com.xue.trainingclass.fragment.HomeFragment.OnSelectClassListener;

public class MainActivity extends FragmentActivity implements
		OnSelectClassListener, OnClickListener {

	DrawerLayout mDrawerLayout;
	GridView mMenu;
	MenuAdapter mMenuAdapter;
	ArrayList<HashMap<String, Object>> mMenuList = new ArrayList<HashMap<String, Object>>();
	// imgSrc->图标(String)
	// text->文字 (String)
	// bgColor->背景色 (int[3]{r,g,b})
	// type->类别(String)

	private LinearLayout mHome, mChat, mPublish, mMessage, mMe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		HashMap<String, Object> menu1 = new HashMap<String, Object>();
		menu1.put("imgSrc",
				"http://gl.oilchem.net/imgServer/attached/common/share.png");
		menu1.put("text", "舞蹈");
		menu1.put("bgColor", new int[] { 255, 182, 193 });
		menu1.put("type", "dance");
		mMenuList.add(menu1);

		HashMap<String, Object> menu2 = new HashMap<String, Object>();
		menu2.put("imgSrc",
				"http://gl.oilchem.net/imgServer/attached/common/share.png");
		menu2.put("text", "体育");
		menu2.put("bgColor", new int[] { 255, 246, 143 });
		menu2.put("type", "sport");
		mMenuList.add(menu2);

		HashMap<String, Object> menu3 = new HashMap<String, Object>();
		menu3.put("imgSrc",
				"http://gl.oilchem.net/imgServer/attached/common/share.png");
		menu3.put("text", "学习辅导");
		menu3.put("bgColor", new int[] { 0, 191, 255 });
		menu3.put("type", "class");
		mMenuList.add(menu3);
		init();

		mHome.performClick();
	}

	public void init() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mMenu = (GridView) findViewById(R.id.menu);
		mHome = (LinearLayout) findViewById(R.id.ll_home);
		mChat = (LinearLayout) findViewById(R.id.ll_chat);
		mPublish = (LinearLayout) findViewById(R.id.ll_publish);
		mMessage = (LinearLayout) findViewById(R.id.ll_message);
		mMe = (LinearLayout) findViewById(R.id.ll_me);

		mHome.setOnClickListener(this);
		mChat.setOnClickListener(this);
		mPublish.setOnClickListener(this);
		mMessage.setOnClickListener(this);
		mMe.setOnClickListener(this);

		// 禁止手势滑动
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		mMenuAdapter = new MenuAdapter(mMenuList, getApplicationContext());
		mMenu.setAdapter(mMenuAdapter);

		mMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mDrawerLayout.closeDrawers();

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_home:
			setSelected(v);
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.frame_content, new HomeFragment(this));
			ft.commit();
			break;
		case R.id.ll_chat:
			setSelected(v);
			FragmentTransaction ft1 = getSupportFragmentManager()
					.beginTransaction();
			ft1.replace(R.id.frame_content, new ChatFragment());
			ft1.commit();
			break;
		case R.id.ll_message:
			setSelected(v);
			FragmentTransaction ft2 = getSupportFragmentManager()
					.beginTransaction();
			ft2.replace(R.id.frame_content, new MessageFragment());
			ft2.commit();
			break;
		case R.id.ll_me:
			setSelected(v);
			FragmentTransaction ft3 = getSupportFragmentManager()
					.beginTransaction();
			ft3.replace(R.id.frame_content, new MeFragment());
			ft3.commit();
			break;
		case R.id.ll_publish:
			Intent intent = new Intent(MainActivity.this, PublishActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	/**
	 * 设置选中项
	 * 
	 * @param v
	 */
	private void setSelected(View v) {
		mHome.setSelected(false);
		mChat.setSelected(false);
		mMessage.setSelected(false);
		mMe.setSelected(false);
		v.setSelected(true);
	}

	// 选择分类
	@Override
	public void onSelectClass() {
		mDrawerLayout.openDrawer(mMenu);

	}

}
