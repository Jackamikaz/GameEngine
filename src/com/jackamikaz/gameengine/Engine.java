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
	
	public static ResourceManager resourceManager() {return instance.resourceManager;}
	public static InputMaster inputMaster() {return instance.inputMaster;}
	public static UpdateMaster updateMaster() {return instance.updateMaster;}
	public static DisplayMaster displayMaster() {return instance.displayMaster;}
	
	public static void init() {
		instance = new Engine();
	}
	
	public static void quit() {
		instance.resourceManager.clearResources();
		instance.displayMaster.dispose();
		instance = null;
	}
	
	public static void pushAll() {
		instance.inputMaster.push();
		instance.updateMaster.push();
		instance.displayMaster.push();
	}
	
	public static void popAll() {
		instance.inputMaster.pop();
		instance.updateMaster.pop();
		instance.displayMaster.pop();
	}
	
	public static void clearAll() {
		instance.inputMaster.clear();
		instance.updateMaster.clear();
		instance.displayMaster.clear();
	}
	
	public static void loop(float deltaT) {
		instance.inputMaster.update();
		instance.updateMaster.update(deltaT);
		instance.displayMaster.display(deltaT,0.0f); // todo : graphical lerp with fixed update
	}
	
	public static void classicLoop() {
		loop(Gdx.graphics.getDeltaTime());
	}
	
	public static void smartAdd(Object entity) {
		if (entity instanceof InputEntity)
			instance.inputMaster.add((InputEntity)entity);
		if (entity instanceof UpdatedEntity)
			instance.updateMaster.add((UpdatedEntity)entity);
		if (entity instanceof DisplayedEntity)
			instance.displayMaster.add((DisplayedEntity)entity);
	}
	
	public static void smartAdd(Object... entities) {
		for (Object entity : entities) {
			smartAdd(entity);
		}
	}
	
	public static void smartRemove(Object entity) {
		if (entity instanceof InputEntity)
			instance.inputMaster.remove((InputEntity) entity);
		if (entity instanceof UpdatedEntity)
			instance.updateMaster.remove((UpdatedEntity)entity);
		if (entity instanceof DisplayedEntity)
			instance.displayMaster.remove((DisplayedEntity)entity);
	}
	
	public static void smartRemove(Object... entities) {
		for (Object entity : entities) {
			smartRemove(entity);
		}
	}
}
