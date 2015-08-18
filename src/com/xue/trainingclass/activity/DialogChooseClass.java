package com.xue.trainingclass.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.xue.trainingclass.adapter.ChildMenuAdapter;
import com.xue.trainingclass.bean.Class;

public class DialogChooseClass extends Activity {

	private TextView mTitle;
	private GridView mClassGV;
	ChildMenuAdapter mAdapter;
	private ImageView mBack;
	List<Class> mParentList = new ArrayList<Class>();
	List<Class> mChildList = new ArrayList<Class>();
	private boolean isParentClass=true;
	private String mParentName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_chooseclasstype);
		
		mTitle=(TextView) findViewById(R.id.title);
		mBack=(ImageView) findViewById(R.id.back);
		mBack.setVisibility(View.INVISIBLE);
		
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isParentClass){
					isParentClass=true;
					mBack.setVisibility(View.INVISIBLE);
					if(mParentList.size()>0){
						mAdapter = new ChildMenuAdapter(mParentList, DialogChooseClass.this);
						mClassGV.setAdapter(mAdapter);
						mTitle.setText(getResources().getString(R.string.chooseParentClass));
					}else {
						queryClasses();
					}
				}
			}
		});
		
		mClassGV=(GridView) findViewById(R.id.classType);
		mClassGV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(isParentClass ){
					mParentName=mParentList.get(position).getClassname();
					if( mParentList.get(position).getHaschild()){
						//选择子分类
						queryChildClassed(mParentList.get(position).getClassid());
						isParentClass=false;
						mBack.setVisibility(View.VISIBLE);
					}else {
						//没有子分类
						Intent intent=new Intent(DialogChooseClass.this,PublishActivity.class);
						intent.putExtra("className", mParentName);
						intent.putExtra("classId", mParentList.get(position).getClassid());
						startActivity(intent);
						finish();
					}
					
				}else{
					Intent intent=new Intent(DialogChooseClass.this,PublishActivity.class);
					intent.putExtra("className", mParentName+"->"+mChildList.get(position).getClassname());
					intent.putExtra("classId", mChildList.get(position).getClassid());
					startActivity(intent);
					finish();
				}
			}
		});
		
		queryClasses();
	}
	

	/**
	 * 查询主分类
	 */
	private void queryClasses() {
		BmobQuery<Class> query = new BmobQuery<Class>();
		query.addWhereEqualTo("type", "parent");
		query.setLimit(50);

		// 执行查询方法
		query.findObjects(DialogChooseClass.this, new FindListener<Class>() {
			@Override
			public void onSuccess(List<Class> object) {
				// TODO Auto-generated method stub
				mParentList = object;
				// mMenuAdapter.notifyDataSetChanged();
				mAdapter = new ChildMenuAdapter(mParentList, DialogChooseClass.this);
				mClassGV.setAdapter(mAdapter);
				mTitle.setText(getResources().getString(R.string.chooseParentClass));
			}
			@Override
			public void onError(int code, String msg) {

			}
		});
	}
	
	/**
	 * 查询子分类
	 * 
	 * @param view
	 */
	private void queryChildClassed(String parentid) {
		BmobQuery<Class> query = new BmobQuery<Class>();
		query.addWhereEqualTo("type", "child");
		query.addWhereEqualTo("parentid", parentid);
		query.setLimit(50);

		// 执行查询方法
		query.findObjects(DialogChooseClass.this, new FindListener<Class>() {
			@Override
			public void onSuccess(List<Class> object) {
				// TODO Auto-generated method stub
				mChildList = object;
				mAdapter = new ChildMenuAdapter(mChildList, DialogChooseClass.this);
				mClassGV.setAdapter(mAdapter);
				mTitle.setText(getResources().getString(R.string.chooseChildClass));
			}

			@Override
			public void onError(int code, String msg) {

			}
		});
	}
}
