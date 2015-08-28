package com.xue.trainingclass.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.xue.trainingclass.adapter.ChildMenuAdapter;
import com.xue.trainingclass.bean.Class;
import com.xue.trainingclass.bean.Constant;

public class ChildMenuDialog extends Activity {

	GridView mChildMenuGV;
	ChildMenuAdapter mChildAdapter;
	List<Class> mChildList = new ArrayList<Class>();
	private String classId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contentview_childclass);

		String parentId = getIntent().getStringExtra("parentId");

		init();
		queryChildClassed(parentId);
	}

	private void init() {
		mChildMenuGV = (GridView) findViewById(R.id.childClassGV);
//		mChildAdapter = new ChildMenuAdapter(mChildList, DialogChildMenu.this);
//		mChildMenuGV.setAdapter(mChildAdapter);

		mChildMenuGV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				classId = mChildList.get(position).getClassid();
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
		query.findObjects(ChildMenuDialog.this, new FindListener<Class>() {
			@Override
			public void onSuccess(List<Class> object) {
				// TODO Auto-generated method stub
				mChildList = object;
				mChildAdapter = new ChildMenuAdapter(mChildList, ChildMenuDialog.this);
				mChildMenuGV.setAdapter(mChildAdapter);

			}

			@Override
			public void onError(int code, String msg) {

			}
		});
	}
	
	@Override
	public void finish() {
		Intent intent=new Intent();
		if(null!=classId){
			intent.putExtra("classId", classId);
		}
		setResult(Constant.CLASS_SELECTED, intent);
		super.finish();
	}
}
