package com.jackamikaz.gameengine.utils;

import com.jackamikaz.gameengine.entities.InputWatcher;

public class InputCondition implements Condition {

	private InputWatcher inputWatcher;
	
	public InputCondition(InputWatcher iw) {
		inputWatcher = iw;
	}
	
	@Override
	public boolean Test() {
		return inputWatcher.wasPressed();
	}
	
}
