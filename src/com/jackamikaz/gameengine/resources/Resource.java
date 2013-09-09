package com.jackamikaz.gameengine.resources;

public abstract class Resource {

	protected String[] files;
	protected Object resObj;
	
	public Resource() {
		files = null;
		resObj = null;
	}
	
	public void setFiles(String[] f) {
		files = f;
	}
	
	public abstract void load();
	public void unLoad() {
		if (resObj != null) {
			unLoadImpl();
			resObj = null;
		}
	}
	
	protected abstract void unLoadImpl();
	
	public Object get() {
		if (resObj == null)
			load();
		return resObj;
	}
	
	public abstract String getResName();
	
	public Object getResObject() {
		return resObj;
	}
}
