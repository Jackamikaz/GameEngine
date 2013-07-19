package com.jackamikaz.gameengine.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.jackamikaz.gameengine.Engine;

public class ResModel extends Resource {

	@Override
	public void Load() {
		
		Model model = null;
		
		ModelLoader<?> loader = (ModelLoader<?>)Engine.ResourceManager().AssetManager().getLoader(Model.class, files[0]);
		model = loader.loadModel(Gdx.files.internal(files[0]));
		
		resObj = model;
	}

	@Override
	protected void UnLoadImpl() {
		((Model)resObj).dispose();		
	}

	@Override
	public String GetResName() {
		return "Model";
	}


}
