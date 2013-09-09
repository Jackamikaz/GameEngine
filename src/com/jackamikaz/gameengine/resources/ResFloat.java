package com.jackamikaz.gameengine.resources;

public class ResFloat extends Resource {

	@Override
	public void load() {
		Float f = Float.parseFloat(files[0]);
		
		resObj = f;
	}

	@Override
	protected void unLoadImpl() {

	}

	@Override
	public String getResName() {
		return "Float";
	}

}
