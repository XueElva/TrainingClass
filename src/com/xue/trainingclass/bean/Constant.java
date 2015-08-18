package com.xue.trainingclass.bean;

public class Constant {

	public static final String FEMALE = "female";
	public static final String MALE = "male";
	/**
	 * 要选择的图片数量key
	 */
	public static final String EXTRA_PHOTO_LIMIT = "com.ns.mutiphotochoser.extra.PHOTO_LIMIT";

	/**
	 * 选择返回结果保存key
	 */
	public static final String EXTRA_PHOTO_PATHS = "com.ns.mutiphotochoser.extra.PHOTO_PATHS";

	// resultCode
	public static final int LOGOUT_CODE = 1;
	public static final int MODIFY_USERINFO_CODE = 2;
	public static final int GET_PIC_FROM_ALBUM = 3;
	public static final int CLASS_SELECTED = 4;
	public static final int ADD_CONNECTPERSON = 5;

	// 用户信息SharedPreference
	public static final String USER_INFO_SP = "userInfoSharedPreference";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password"; // 加上用户名进行区分

	// 用户设置SharedPreference
	public static final String USER_SET_SP = "userSetSharedPreference";
	public static final String REMEMBER_PASS = "rememberPassword";// 加上用户名进行区分

}
