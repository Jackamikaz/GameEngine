package com.jackamikaz.gameengine.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jackamikaz.gameengine.assets.Curve;
import com.jackamikaz.gameengine.assets.SoundCollection;
import com.jackamikaz.gameengine.assets.SpriteLayout;

public class ResourceManager {

	private HashMap<String, Resource> map;
	private String prefix = "data/";
	private AssetManager assetManager;
	
	public ResourceManager() {
		map = new HashMap<String, Resource>();
		assetManager = new AssetManager();
	}
	
	public AssetManager assetManager() {
		return assetManager;
	}
	
	private Object getImpl(String name) {
		Resource res = map.get(name);
		if (res != null)
			return res.get();
		
		System.out.println("Resource "+name+" was not found.");
		return null;
	}
	
	public Texture getTexture(String name) {return (Texture)(getImpl(name));}
	public TextureRegion[] getTextureSheet(String name) {return (TextureRegion[])(getImpl(name));}
	public BitmapFont getFont(String name) {return (BitmapFont)(getImpl(name));}
	public ShaderProgram getShader(String name) {return (ShaderProgram)(getImpl(name));}
	public Model getModel(String name) {return (Model)(getImpl(name));}
	public Curve getCurve(String name) {return (Curve)(getImpl(name));}
	public SpriteLayout getLayout(String name) {return (SpriteLayout)(getImpl(name));}
	public Sound getSound(String name) {return (Sound)(getImpl(name));}
	public SoundCollection getSoundCollection(String name) {return (SoundCollection)(getImpl(name));}
	public Music getMusic(String name) {return (Music)(getImpl(name));}
	public float getFloat(String name) {return (Float)(getImpl(name));}
	
	public String[] extractResourceNameOfType(Class<?> cl) {
		
		Vector<String> vec = new Vector<String>();
		
		for(String name : map.keySet()) {
			Resource res = map.get(name);
			if (res.getClass() == cl)
				vec.add(name);
		}
		
		Collections.sort(vec);
		
		return vec.toArray(new String[1]);
	}
	
	public void unloadResources() {
		for(String name : map.keySet()) {
			map.get(name).unLoad();
		}
		assetManager.dispose();
	}
	
	public void clearResources() {
		unloadResources();
		map.clear();
	}
	
	public void loadResourcesFile(String filename) {
		loadResourcesFile(filename, false);
	}
	
	public void loadResourcesFile(String filename, boolean preloadAll) {
		InputStream in = Gdx.files.internal(prefix+filename).read();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			line = reader.readLine();
			while (line != null) {
				String[] tokens = line.split("[ ]+");
				
				if (!addResource(new ResTexture()			, tokens, 1))
				if (!addResource(new ResTextureSheet()		, tokens, 0))
				if (!addResource(new ResFont()				, tokens, 2))
				if (!addResource(new ResShader()			, tokens, 2))
				if (!addResource(new ResModel()				, tokens, 1))
				if (!addResource(new ResCurve()				, tokens, 1))
				if (!addResource(new ResSpriteLayout()		, tokens, 1))
				if (!addResource(new ResSound()				, tokens, 1))
				if (!addResource(new ResSoundCollection()	, tokens, 0))
				if (!addResource(new ResMusic()				, tokens, 1))
					 addResource(new ResFloat()				, tokens, 0);
				
				line = reader.readLine();
			}
			
			reader.close();
		} catch (IOException e) {
			
		}
	}
	
	private boolean addResource(Resource res, String[] tokens, int prefixes) {
		if (tokens[0].equalsIgnoreCase(res.getResName())) {
			String[] args = new String[tokens.length-2];
			
			for(int i=0; i<args.length; ++i) {
				if (i < prefixes)
					args[i] = prefix+tokens[i+2];
				else
					args[i] = tokens[i+2];
			}
			
			res.setFiles(args);
			
			map.put(tokens[1], res);
			return true;
		}
		return false;
	}
	
	public String findNameFromResource(Object res) {
		if (res == null)
			return "";
		
		for(Entry<String, Resource> entry : map.entrySet()) {
			if (entry.getValue().getResObject() == res)
				return entry.getKey();
		}
		
		return "";
	}
}
