package com.ggwp.customled;

public class ListClass {
	
	String packageName;
	String AppName;
	
	ListClass(String packagename,String appname){
		this.packageName = packagename;
		this.AppName = appname;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getAppName() {
		return AppName;
	}
	public void setAppName(String appName) {
		AppName = appName;
	}

}
