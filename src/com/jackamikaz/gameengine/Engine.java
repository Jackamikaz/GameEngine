package com.jackamikaz.gameengine;

import com.badlogic.gdx.Gdx;
import com.jackamikaz.gameengine.resources.ResourceManager;

public class Engine {

	private static Engine instance = null;
	
	private ResourceManager resourceManager;
	
	private InputMaster inputMaster;
	private UpdateMaster updateMaster;
	private DisplayMaster displayMaster;

	
	public Engine() {
		resourceManager = new ResourceManager();
		inputMaster = new InputMaster();
		updateMaster = new UpdateMaster();
		displayMaster = new DisplayMaster();
	}
	
	public static ResourceManager ResourceManager() {return instance.resourceManager;}
	public static InputMaster InputMaster() {return instance.inputMaster;}
	public static UpdateMaster UpdateMaster() {return instance.updateMaster;}
	public static DisplayMaster DisplayMaster() {return instance.displayMaster;}
	
	public static void Init() {
		instance = new Engine();
	}
	
	public static void Quit() {
		instance.resourceManager.ClearResources();
		instance.displayMaster.dispose();
		instance = null;
	}
	
	public static void PushAll() {
		instance.inputMaster.Push();
		instance.updateMaster.Push();
		instance.displayMaster.Push();
	}
	
	public static void PopAll() {
		instance.inputMaster.Pop();
		instance.updateMaster.Pop();
		instance.displayMaster.Pop();
	}
	
	public static void ClearAll() {
		instance.inputMaster.Clear();
		instance.updateMaster.Clear();
		instance.displayMaster.Clear();
	}
	
	public static void Loop(float deltaT) {
		instance.inputMaster.Update();
		instance.updateMaster.Update(deltaT);
		instance.displayMaster.Display(deltaT,0.0f); // todo : graphical lerp with fixed update
	}
	
	public static void ClassicLoop() {
		Loop(Gdx.graphics.getDeltaTime());
	}
	
	public static void SmartAdd(Object entity) {
		if (entity instanceof InputEntity)
			instance.inputMaster.Add((InputEntity)entity);
		if (entity instanceof UpdatedEntity)
			instance.updateMaster.Add((UpdatedEntity)entity);
		if (entity instanceof DisplayedEntity)
			instance.displayMaster.Add((DisplayedEntity)entity);
	}
	
	public static void SmartAdd(Object... entities) {
		for (Object entity : entities) {
			SmartAdd(entity);
		}
	}
	
	public static void SmartRemove(Object entity) {
		if (entity instanceof InputEntity)
			instance.inputMaster.Remove((InputEntity) entity);
		if (entity instanceof UpdatedEntity)
			instance.updateMaster.Remove((UpdatedEntity)entity);
		if (entity instanceof DisplayedEntity)
			instance.displayMaster.Remove((DisplayedEntity)entity);
	}
	
	public static void SmartRemove(Object... entities) {
		for (Object entity : entities) {
			SmartRemove(entity);
		}
	}
}
