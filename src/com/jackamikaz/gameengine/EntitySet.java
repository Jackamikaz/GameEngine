package com.jackamikaz.gameengine;

import java.util.Vector;

public class EntitySet {

	public Vector<InputEntity> input;
	public Vector<UpdatedEntity> updated;
	public Vector<DisplayedEntity> displayed;
	
	public EntitySet() {
		input = new Vector<InputEntity>();
		updated = new Vector<UpdatedEntity>();
		displayed = new Vector<DisplayedEntity>();
	}
	
	public EntitySet(Object... entities) {
		this();
		for(Object entity : entities) {
			SmartAdd(entity);
		}
	}
	
	public void SmartAdd(Object entity) {
		if (entity instanceof InputEntity)
			input.add((InputEntity)entity);
		if (entity instanceof UpdatedEntity)
			updated.add((UpdatedEntity)entity);
		if (entity instanceof DisplayedEntity)
			displayed.add((DisplayedEntity)entity);
	}
	
	public void Push() {
		Engine.InputMaster().Push(input);
		Engine.UpdateMaster().Push(updated);
		Engine.DisplayMaster().Push(displayed);
	}
}
