package com.jackamikaz.gameengine.saveload;

import java.io.IOException;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public interface SaverLoader {
	
	int processInt(int i) throws IOException;
	float processFloat(float f) throws IOException;
	String processString(String s) throws IOException;
	
	void processVector2(Vector2 vec) throws IOException;
	void processVector3(Vector3 vec) throws IOException;
	void processMatrix4(Matrix4 mat) throws IOException;
	
	void processArray(String name, int size, SaverLoaderObject obj) throws IOException;
	
	void processCustom(SaverLoaderObject obj) throws IOException;
	void newLine() throws IOException;
	
	boolean isLoading();
	int currentArrayIndex();
	int currentArraySize();
}
