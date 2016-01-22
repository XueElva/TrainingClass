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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.adapter.ClassListAdapter;
import com.xue.trainingclass.bean.CollectionClass;
import com.xue.trainingclass.bean.Publish_Class;
import com.xue.trainingclass.bean.User;

public class HomeInfoFragment extends Fragment {

	private PullToRefreshListView mClassLV;
	private ClassListAdapter mAdapter;
	ArrayList<Publish_Class> mClassList = new ArrayList<Publish_Class>();
	ArrayList<String> mCollectedClassIdList = new ArrayList<String>(); // ���ղص��б��classid��
	ArrayList<CollectionClass> mCollectedList = new ArrayList<CollectionClass>(); // ���ղص��б�
	private String mClassTypeId = null; // �γ����id
	private int page = 0; // ��ѯҳ��

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.fragment_home_info,
				null);
		mClassLV = (PullToRefreshListView) view.findViewById(R.id.classLV);
		mClassLV.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 0;
				getClassList();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				getClassList();

			}
		});
		mAdapter = new ClassListAdapter(mClassList, getActivity(),
				mCollectedClassIdList, mCollectedList);
		mClassLV.setAdapter(mAdapter);
		getCollectedList();
		return view;
	}

	/**
	 * ��ȡ�ղ��б�
	 */
	private void getCollectedList() {
		User currentUser = BmobUser.getCurrentUser(getActivity(), User.class);
		if (currentUser != null) {
			BmobQuery<CollectionClass> query = new BmobQuery<CollectionClass>();
			query.addWhereEqualTo("userId", currentUser.getObjectId());
			// �����û�����ղ�500������
			query.setLimit(500);
			query.findObjects(getActivity(),
					new FindListener<CollectionClass>() {

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

	}

	/**
	 * ��ȡ�������ղ��б�Ŀγ�id
	 */
	private void getCollectedClassIdList() {
		for (int i = 0; i < mCollectedList.size(); i++) {
			mCollectedClassIdList.add(mCollectedList.get(i).getClassId());
		}

		getClassList();
	}

	/**
	 * ���ݿγ����id��ȡ�γ��б�
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
				mClassLV.onRefreshComplete();
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
				mClassLV.onRefreshComplete();
				Toast.makeText(
						getActivity(),
						getActivity().getResources().getString(
								R.string.loadError), 1).show();
			}
		});

	}

}
