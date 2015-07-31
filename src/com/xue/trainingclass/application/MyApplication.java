package com.xue.trainingclass.application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {
	public static MyApplication mApplication;
	@Override
	public void onCreate() {
		
		mApplication=this;
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));
		super.onCreate();
	}
}
