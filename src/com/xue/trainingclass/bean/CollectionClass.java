package com.xue.trainingclass.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

 //�����û����ղر�
public class CollectionClass extends BmobObject implements Serializable{
private String userId; //�û�id
private String classId; //�ղصĿγ�id
private String classTypeId; //�γ�����id������ƥ�����ͣ�
private String classCityId; //�γ����ڳ���id������ƥ�����ͣ�

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
