package com.xue.trainingclass.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.xue.trainingclass.activity.DialogChildMenu;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.adapter.MenuAdapter;
import com.xue.trainingclass.bean.Class;
import com.xue.trainingclass.bean.Constant;

public class MenuFragment extends Fragment {
	GridView mMenu;
	MenuAdapter mMenuAdapter;
	List<Class> mMenuList = new ArrayList<Class>();
	// icon->图标(String)
	// classname->文字 (String)
	// type->类别(String)
	// classid(String)
	OnClassSelectedListener mOnClassSelectedListener;

	public MenuFragment() {

	}

	public MenuFragment(OnClassSelectedListener listener) {
		this.mOnClassSelectedListener = listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, null);

		initView(view);
		queryClasses();
		return view;
	}

	/**
	 * 查询主分类
	 */
	private void queryClasses() {
		BmobQuery<Class> query = new BmobQuery<Class>();
		query.addWhereEqualTo("type", "parent");
		query.setLimit(50);

		// 执行查询方法
		query.findObjects(getActivity(), new FindListener<Class>() {
			@Override
			public void onSuccess(List<Class> object) {
				// TODO Auto-generated method stub
				mMenuList = object;
				// mMenuAdapter.notifyDataSetChanged();
				mMenuAdapter = new MenuAdapter(mMenuList, getActivity());
				mMenu.setAdapter(mMenuAdapter);

			}

			@Override
			public void onError(int code, String msg) {

			}
		});
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mMenu = (GridView) view.findViewById(R.id.menuGV);
		mMenuAdapter = new MenuAdapter(mMenuList, getActivity());
		mMenu.setAdapter(mMenuAdapter);

		mMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Class clas = mMenuList.get(position);
				if (clas.getType().equals("parent") && clas.getHaschild()) {
					// 有子分类
					Intent intent = new Intent(getActivity(),
							DialogChildMenu.class);
					intent.putExtra("parentId", clas.getClassid());
					startActivityForResult(intent, 4);
					
				} else {
					mOnClassSelectedListener.onClassSelected(clas.getClassid());
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Constant.CLASS_SELECTED){
			if(null!=data.getStringExtra("classId")){
				mOnClassSelectedListener.onClassSelected(data.getStringExtra("classId"));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	public interface OnClassSelectedListener {
		void onClassSelected(String classid);
	}
}
