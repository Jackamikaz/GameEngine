package com.jackamikaz.gameengine;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.jackamikaz.gameengine.utils.StackOfCollections;

public class InputMaster extends StackOfCollections<InputEntity> {

	protected LinkedList<InputEntity> newCollection() {
		return new LinkedList<InputEntity>();
	}
	
	public void update() {
		Collection<InputEntity> listEntities = adjustAndPeek();
		
		Iterator<InputEntity> it = listEntities.iterator();
		while (it.hasNext()) {
			it.next().newInput(Gdx.input);
		}
	}
	
}
