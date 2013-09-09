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
			smartAdd(entity);
		}
	}
	
	public void smartAdd(Object entity) {
		if (entity instanceof InputEntity)
			input.add((InputEntity)entity);
		if (entity instanceof UpdatedEntity)
			updated.add((UpdatedEntity)entity);
		if (entity instanceof DisplayedEntity)
			displayed.add((DisplayedEntity)entity);
	}
	
	public void push() {
		Engine.inputMaster().push(input);
		Engine.updateMaster().push(updated);
		Engine.displayMaster().push(displayed);
	}
}
