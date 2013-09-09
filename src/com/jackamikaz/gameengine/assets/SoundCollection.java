package com.jackamikaz.gameengine.assets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class SoundCollection {

	public Sound[] sounds;
	
	public SoundCollection(Sound[] snds) {
		sounds = snds;
	}
	
	public Sound get(int id) {
		return sounds[id];
	}
	
	public Sound loopGet(int id) {
		id = id % sounds.length;
		if (id < 0)
			id += sounds.length;
		return sounds[id];
	}
	
	public Sound randomGet() {
		return sounds[MathUtils.random(sounds.length-1)];
	}
}
