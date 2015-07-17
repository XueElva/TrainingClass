package com.xue.trainingclass.fragment;

import com.xue.trainingclass.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageFragment extends Fragment {
	private TextView mEdit;
	private RelativeLayout mEditBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_message,
				null);
		
		init(view);
		return view;
	}

	private void init(View view) {
		mEdit=(TextView) view.findViewById(R.id.edit);
		mEditBar=(RelativeLayout) view.findViewById(R.id.editBar);
		mEditBar.setVisibility(View.GONE);
		
		mEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mEditBar.getVisibility()==View.VISIBLE){
					mEditBar.setVisibility(View.GONE);
					mEdit.setText(getResources().getString(R.string.edit));
				}else{
					mEditBar.setVisibility(View.VISIBLE);
					mEdit.setText(getResources().getString(R.string.cancel));
				}
				
			}
		});
		
	}
}
