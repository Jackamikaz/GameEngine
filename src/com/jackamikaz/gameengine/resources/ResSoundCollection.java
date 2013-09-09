package com.jackamikaz.gameengine.resources;

import java.util.Vector;

import com.badlogic.gdx.audio.Sound;
import com.jackamikaz.gameengine.Engine;
import com.jackamikaz.gameengine.assets.SoundCollection;

public class ResSoundCollection extends Resource {

	/*public ResSoundCollection(String[] f) {
		super(f);
	}*/
	
	@Override
	public void load() {
		Vector<Sound> sounds = new Vector<Sound>();
		
		for(int i=0; i<files.length; ++i) {
			Sound snd = Engine.resourceManager().getSound(files[i]);
			if (snd != null) {
				sounds.add(snd);
			}
		}
		
		resObj = new SoundCollection(sounds.toArray(new Sound[sounds.size()]));
	}

	@Override
	protected void unLoadImpl() {
	}

	
	@Override
	public String getResName() {
		return "SoundCollection";
	}


}
