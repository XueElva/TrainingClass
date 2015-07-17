package com.xue.trainingclass.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.CirclePageIndicator;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.adapter.HeadPageAdapter;
import com.xue.trainingclass.inter.IfoucePage;
import com.xue.trainingclass.view.FouceViewPager.OnSingleTouchListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotHeaderPager<T> extends LinearLayout {
	List<T> contentList;
	Handler handler;
	int count = 0;
	Context context;

	@SuppressLint("NewApi")
	public HotHeaderPager(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		this.context = context;
		weidgetInit();

		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public HotHeaderPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		weidgetInit();
		// TODO Auto-generated constructor stub
	}

	public HotHeaderPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		weidgetInit();
		// TODO Auto-generated constructor stub
	}

	public HotHeaderPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		weidgetInit();
	}

	private void weidgetInit() {

		initImageLoader();
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_hotheader, HotHeaderPager.this);
		pager = (FouceViewPager) findViewById(R.id.hhp_pager);
		cpIndicator = (CirclePageIndicator) findViewById(R.id.cp_indicator);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setVisibility(View.GONE);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:
					if (pager.getAdapter().getCount() > 0) {
						count++;
						pager.setCurrentItem(count
								% pager.getAdapter().getCount(), true);
					}

					break;

				default:
					break;
				}
			}
		};

		pager.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch(View v, int currentitem) {
				// TODO Auto-generated method stub
				ifoucePage.onItemClick(contentList.get(currentitem));
			}
		});
	}

	FouceViewPager pager;
	CirclePageIndicator cpIndicator;
	TextView tv_title;

	HeadPageAdapter hpAdapter;

	List<View> viewList = new ArrayList<View>();

	IfoucePage<T> ifoucePage;

	public void setContentList(List<T> contentList, IfoucePage ifPage) {
		this.contentList = contentList;
		this.ifoucePage = ifPage;
		initWeidget();

	}

	DisplayImageOptions options;
	ImageLoader imageLoader;

	private void initImageLoader() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.icon_pic_default)
				.showStubImage(R.drawable.icon_pic_default).cacheInMemory(true)
				.cacheOnDisc(true).build();
		imageLoader = ImageLoader.getInstance();

	}

	private void initWeidget() {
		hpAdapter = new HeadPageAdapter(viewList, context);
		pager.setAdapter(hpAdapter);
		cpIndicator.setViewPager(pager);
		beginShow();
	}

	/**
	 * Ë¢ÐÂ
	 */
	public void update() {
		if (null != contentList) {
			hpAdapter.getContentList().clear();
			for (int i = 0; i < contentList.size(); i++) {
				ImageView iv_content = new ImageView(context);
				iv_content.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				iv_content.setScaleType(ScaleType.FIT_XY);
				// iv_content.setImageResource(R.drawable.icon_default);
				imageLoader.displayImage(
						ifoucePage.getCurrentImageUrl(contentList.get(i)),
						iv_content, options);
				hpAdapter.getContentList().add(iv_content);
			}
			hpAdapter.notifyDataSetChanged();
			cpIndicator.notifyDataSetChanged();
		}
	}

	Timer showTimer;

	public void beginShow() {
		showTimer = new Timer();
		showTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		}, 3000, 3000);
	}
}
