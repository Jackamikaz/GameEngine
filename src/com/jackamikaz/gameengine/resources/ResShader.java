package com.jackamikaz.gameengine.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ResShader extends Resource {

	/*public ResShader(String[] f) {
		super(f);
	}*/

	@Override
	public void load() {
		ShaderProgram shader = new ShaderProgram(
				Gdx.files.internal(files[0]),
				Gdx.files.internal(files[1]));
		
		System.out.println("Shader compile log :");
		System.out.println(shader.getLog());
		
		resObj = shader;
	}

	@Override
	public void unLoadImpl() {
		((ShaderProgram)resObj).dispose();
	}

	@Override
	public String getResName() {
		return "Shader";
	}


}
