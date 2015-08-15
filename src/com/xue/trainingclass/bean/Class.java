package com.xue.trainingclass.bean;

import java.io.File;
import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

//培训班类别
public class Class extends BmobObject implements Serializable{
private String classname; //类名
private BmobFile icon; //图标
private String type;   //parent or child
private String classid; 
private Boolean haschild; //是否有子分类
private String parentid;  //父分类id



public Boolean getHaschild() {
	return haschild;
}
public void setHaschild(Boolean haschild) {
	this.haschild = haschild;
}
public String getParentid() {
	return parentid;
}
public void setParentid(String parentid) {
	this.parentid = parentid;
}
public String getClassname() {
	return classname;
}
public void setClassname(String classname) {
	this.classname = classname;
}
public BmobFile getIcon() {
	return icon;
}
public void setIcon(BmobFile icon) {
	this.icon = icon;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getClassid() {
	return classid;
}
public void setClassid(String classid) {
	this.classid = classid;
}


}
