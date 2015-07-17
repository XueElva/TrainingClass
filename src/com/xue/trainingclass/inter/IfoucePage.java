package com.xue.trainingclass.inter;

public interface IfoucePage<T> {
	public String getCurrentImageUrl(T t);

	public void onItemClick(T t);

}
