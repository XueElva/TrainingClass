package com.xue.trainingclass.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

 //所有用户的收藏表
public class CollectionClass extends BmobObject implements Serializable{
private String userId; //用户id
private String classId; //收藏的课程id
private String classTypeId; //课程类型id（用于匹配推送）
private String classCityId; //课程所在城市id（用于匹配推送）

public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getClassId() {
	return classId;
}
public void setClassId(String classId) {
	this.classId = classId;
}
public String getClassTypeId() {
	return classTypeId;
}
public void setClassTypeId(String classTypeId) {
	this.classTypeId = classTypeId;
}
public String getClassCityId() {
	return classCityId;
}
public void setClassCityId(String classCityId) {
	this.classCityId = classCityId;
}


}
