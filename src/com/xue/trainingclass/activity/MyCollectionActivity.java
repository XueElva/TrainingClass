package com.xue.trainingclass.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xue.trainingclass.bean.CollectionClass;
import com.xue.trainingclass.bean.Publish_Class;
import com.xue.trainingclass.bean.User;

public class MyCollectionActivity extends Activity {
	TextView mBack;
	PullToRefreshListView mCollectionLV;
	ArrayList<Publish_Class> mClassList = new ArrayList<Publish_Class>();
	ArrayList<CollectionClass> mCollectedList = new ArrayList<CollectionClass>(); // 已收藏的列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mycollection);

		initView();
		
		getCollectionList();
	}

	private void initView() {
		mBack = (TextView) findViewById(R.id.back);
		mCollectionLV = (PullToRefreshListView) findViewById(R.id.collectionLV);
		mCollectionLV.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page=0;
				getCollectionList();
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				getCollectionList();
			}
		});
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	/**
	 * 获取收藏列表
	 */

	int page = 0;

	private void getCollectionList() {
		User currentUser = BmobUser.getCurrentUser(MyCollectionActivity.this,
				User.class);
		BmobQuery<CollectionClass> query = new BmobQuery<CollectionClass>();
		query.addWhereEqualTo("userId", currentUser.getObjectId());
		query.setLimit(10);
		query.setSkip(page * 10);

		if (page == 0) {
			mClassList.clear();
			mCollectedList.clear();
		}
		query.findObjects(MyCollectionActivity.this,
				new FindListener<CollectionClass>() {

					@Override
					public void onSuccess(List<CollectionClass> collectionList) {
						if (collectionList.size() > 0) {
							mCollectedList.addAll(collectionList);

							getClassList(collectionList);
							page++;
						} else {
							Toast.makeText(
									MyCollectionActivity.this,
									getResources().getString(
											R.string.noMoreData), 1).show();
						}

					}

					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void getClassList(List<CollectionClass> collectionList) {

	}

}
