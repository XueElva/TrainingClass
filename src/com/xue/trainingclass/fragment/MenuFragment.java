package com.xue.trainingclass.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.adapter.MenuAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuFragment extends Fragment {
	GridView mMenu;
	MenuAdapter mMenuAdapter;
	ArrayList<HashMap<String, Object>> mMenuList = new ArrayList<HashMap<String, Object>>();
	// imgSrc->图标(String)
	// text->文字 (String)
	// type->类别(String)
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

		HashMap<String, Object> menu1 = new HashMap<String, Object>();
		menu1.put("imgSrc",
				"http://gl.oilchem.net/imgServer/attached/common/share.png");
		menu1.put("text", "舞蹈");
		menu1.put("bgColor", new int[] { 127, 255, 212 });
		menu1.put("type", "dance");
		mMenuList.add(menu1);

		HashMap<String, Object> menu2 = new HashMap<String, Object>();
		menu2.put("imgSrc",
				"http://gl.oilchem.net/imgServer/attached/common/share.png");
		menu2.put("text", "体育");
		menu2.put("bgColor", new int[] { 132, 197, 249 });
		menu2.put("type", "sport");
		mMenuList.add(menu2);

		HashMap<String, Object> menu3 = new HashMap<String, Object>();
		menu3.put("imgSrc",
				"http://gl.oilchem.net/imgServer/attached/common/share.png");
		menu3.put("text", "学习辅导");
		menu3.put("bgColor", new int[] { 127, 255, 212 });
		menu3.put("type", "class");
		mMenuList.add(menu3);

		initView(view);
		return view;
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
				mOnClassSelectedListener.onClassSelected(mMenuList
						.get(position).get("type").toString());

			}
		});
	}

	public interface OnClassSelectedListener {
		void onClassSelected(String type);
	}
}
