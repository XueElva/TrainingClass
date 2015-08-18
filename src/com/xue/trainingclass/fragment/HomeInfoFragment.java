package com.xue.trainingclass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.view.XListView;

public class HomeInfoFragment extends Fragment {

	private XListView mClassLV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.fragment_home_info,
				null);
		mClassLV=(XListView) view.findViewById(R.id.classLV);
		return view;
	}
}
