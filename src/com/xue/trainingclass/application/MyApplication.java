package com.xue.trainingclass.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {
	public static MyApplication mApplication;

	@Override
	public void onCreate() {

		mApplication = this;
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));
		super.onCreate();
	}
}
