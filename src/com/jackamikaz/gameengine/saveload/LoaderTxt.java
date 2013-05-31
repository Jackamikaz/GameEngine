package com.jackamikaz.gameengine.saveload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class LoaderTxt implements SaverLoader {

	BufferedReader reader;
	String line;
	String[] tokens;
	int curToken;
	int curArrayIndex;
	int curArraySize;
	
	public LoaderTxt(FileHandle file, SaverLoaderObject obj) {
		reader = new BufferedReader(new InputStreamReader(file.read()));
		
		try {
			newLine();
			
			this.processCustom(obj);
			
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int processInt(int i) throws IOException {
		return Integer.parseInt(tokens[curToken++]);
	}

	@Override
	public float processFloat(float f) throws IOException {
		return Float.parseFloat(tokens[curToken++]);
	}

	@Override
	public String processString(String s) throws IOException {
		return tokens[curToken++];
	}
	
	@Override
	public void processVector2(Vector2 vec) throws IOException {
		vec.x = Float.parseFloat(tokens[curToken++]);
		vec.y = Float.parseFloat(tokens[curToken++]);
	}

	@Override
	public void processVector3(Vector3 vec) throws IOException {
		vec.x = Float.parseFloat(tokens[curToken++]);
		vec.y = Float.parseFloat(tokens[curToken++]);
		vec.z = Float.parseFloat(tokens[curToken++]);
	}

	@Override
	public void processMatrix4(Matrix4 mat) throws IOException {
		for(int i=0; i<mat.val.length; ++i) {
			mat.val[i] = Float.parseFloat(tokens[curToken++]);
		}
	}
	
	@Override
	public void processArray(String name, int size, SaverLoaderObject obj) throws IOException {
		
		if (curToken > 0)
			newLine();
		
		if (!name.equalsIgnoreCase(tokens[curToken++])) {
			throw new IOException("Unexpected collection, found "+tokens[curToken-1]+" instead of "+name);
		}
		
		int arraySize = processInt(0);
		for(int i=0; i<arraySize; ++i) {
			curArraySize = arraySize;
			curArrayIndex = i;
			newLine();
			processCustom(obj);
		}
	}

	@Override
	public void processCustom(SaverLoaderObject obj) throws IOException {
		obj.handleSaveLoad(this);
	}

	@Override
	public void newLine() throws IOException {
		line = reader.readLine();
		tokens = line.split("[ ]+");
		curToken = 0;
	}

	@Override
	public boolean isLoading() {
		return true;
	}

	@Override
	public int currentArrayIndex() {
		return curArrayIndex;
	}

	@Override
	public int currentArraySize() {
		return curArraySize;
	}

}
