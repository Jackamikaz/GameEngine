package com.jackamikaz.gameengine.resources;

import com.badlogic.gdx.Gdx;
import com.jackamikaz.gameengine.assets.SpriteLayout;

public class ResSpriteLayout extends Resource {

	/*public ResSpriteLayout(String[] f) {
		super(f);
	}*/

	@Override
	public void load() {
		SpriteLayout layout = new SpriteLayout(Gdx.files.internal(files[0]));
		
		resObj = layout;
	}

	@Override
	protected void unLoadImpl() {
	}

	@Override
	public String getResName() {
		return "SpriteLayout";
	}


}
