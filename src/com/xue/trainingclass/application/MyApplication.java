package com.xue.trainingclass.application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));
		super.onCreate();
	}
}
