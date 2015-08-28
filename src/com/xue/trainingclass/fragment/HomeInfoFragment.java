package com.xue.trainingclass.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.adapter.ClassListAdapter;
import com.xue.trainingclass.bean.CollectionClass;
import com.xue.trainingclass.bean.Publish_Class;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.view.XListView;
import com.xue.trainingclass.view.XListView.IXListViewListener;

public class HomeInfoFragment extends Fragment implements IXListViewListener {

	private XListView mClassLV;
	private ClassListAdapter mAdapter;
	ArrayList<Publish_Class> mClassList = new ArrayList<Publish_Class>();
	ArrayList<String> mCollectedClassIdList = new ArrayList<String>(); // 已收藏的列表的classid列
	ArrayList<CollectionClass> mCollectedList = new ArrayList<CollectionClass>(); // 已收藏的列表
	private String mClassTypeId = null; // 课程类别id
	private int page = 0; // 查询页数

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.fragment_home_info,
				null);
		mClassLV = (XListView) view.findViewById(R.id.classLV);
		mClassLV.setPullLoadEnable(true);
		mClassLV.setPullRefreshEnable(true);
		mClassLV.setXListViewListener(this);
		mAdapter = new ClassListAdapter(mClassList, getActivity(),
				mCollectedClassIdList, mCollectedList);
		mClassLV.setAdapter(mAdapter);

		getCollectedList();
		return view;
	}

	/**
	 * 获取收藏列表
	 */
	private void getCollectedList() {
		BmobQuery<CollectionClass> query = new BmobQuery<CollectionClass>();
		query.addWhereEqualTo("userId",
				BmobUser.getCurrentUser(getActivity(), User.class)
						.getObjectId());
		// 限制用户最多收藏500条数据
		query.setLimit(500);
		query.findObjects(getActivity(), new FindListener<CollectionClass>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<CollectionClass> arg0) {
				mCollectedList = (ArrayList<CollectionClass>) arg0;
				getCollectedClassIdList();
			}

		});

	}

	/**
	 * 获取所有已收藏列表的课程id
	 */
	private void getCollectedClassIdList() {
		for (int i = 0; i < mCollectedList.size(); i++) {
			mCollectedClassIdList.add(mCollectedList.get(i).getClassId());
		}

		getClassList();
	}

	/**
	 * 根据课程类别id获取课程列表
	 */
	private void getClassList() {
		BmobQuery<Publish_Class> query = new BmobQuery<Publish_Class>();
		if (mClassTypeId != null) {
			query.addWhereEqualTo("classTypeId", mClassTypeId);
		}
		query.setLimit(10);
		query.setSkip(page * 10);
		if (page == 0) {
			mClassList.clear();
		}
		query.findObjects(getActivity(), new FindListener<Publish_Class>() {

			@Override
			public void onSuccess(List<Publish_Class> classList) {
				if (classList.size() > 0) {
					mClassList.addAll(classList);
					mAdapter.notifyDataSetChanged();

					page++;
				} else {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.noMoreData), 1).show();
				}

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.loadError), 1).show();
			}
		});

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 0;
		getClassList();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		getClassList();
	}
}
