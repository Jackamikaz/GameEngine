package com.jackamikaz.gameengine.saveload;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SaverTxt implements SaverLoader {

	private FileWriter fw;
	int curArrayIndex;
	int curArraySize;
	boolean somethingWritten;
	
	public SaverTxt(File file, SaverLoaderObject obj) {
		
		somethingWritten = false;
		
		try {
			fw = new FileWriter(file);
			
			processCustom(obj);
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean processBoolean(boolean b) throws IOException {
		somethingWritten = true;
		fw.write(b + " ");
		return b;
	}
	
	@Override
	public int processInt(int i) throws IOException {
		somethingWritten = true;
		fw.write(i + " ");
		return i;
	}

	@Override
	public float processFloat(float f) throws IOException {
		somethingWritten = true;
		fw.write(f + " ");
		return f;
	}

	@Override
	public String processString(String s) throws IOException {
		somethingWritten = true;
		fw.write(s + " ");
		return s;
	}

	@Override
	public void processVector2(Vector2 vec) throws IOException {
		somethingWritten = true;
		fw.write(vec.x + " " + vec.y + " ");
	}

	@Override
	public void processVector3(Vector3 vec) throws IOException {
		somethingWritten = true;
		fw.write(vec.x + " " + vec.y + " " + vec.z + " ");
	}

	@Override
	public void processMatrix4(Matrix4 mat) throws IOException {
		somethingWritten = true;
		for(float f : mat.val)
			processFloat(f);
	}

	@Override
	public int[] processIntArray(String name, int[] iarray) throws IOException {
		newLine();
		somethingWritten = true;
		fw.write(name + " " + iarray.length);
		for(int i=0; i<iarray.length; ++i) {
			fw.write(" " + iarray[i]);
		}
		newLine();
		return iarray;
	}
	
	@Override
	public void processArray(String name, int size, SaverLoaderObject obj) throws IOException {
		newLine();
		somethingWritten = true;
		fw.write(name + " " + size);
		newLine();
		for(int i=0; i<size; ++i) {
			curArrayIndex = i;
			curArraySize = size;
			processCustom(obj);
			newLine();
		}
	}

	@Override
	public void processCustom(SaverLoaderObject obj) throws IOException {
		obj.handleSaveLoad(this);
	}

	@Override
	public void newLine() throws IOException {
		if (somethingWritten) {
			fw.write("\r\n");
			somethingWritten = false;
		}
	}

	@Override
	public boolean isLoading() {
		return false;
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
