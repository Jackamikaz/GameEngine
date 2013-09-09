package com.jackamikaz.gameengine;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.jackamikaz.gameengine.utils.StackOfCollections;

public class UpdateMaster extends StackOfCollections<UpdatedEntity> {
	
	protected LinkedList<UpdatedEntity> newCollection() {
		return new LinkedList<UpdatedEntity>();
	}
	
	public void update(float deltaT) {
		// fake lag
		if (fakelag > 0.0f) {
			consumedLag -= deltaT;
			if (consumedLag <= 0.0f) {
				consumedLag = fakelag;
				deltaT = fakelag;
			}
			else
				return;
		}
		
		Collection<UpdatedEntity> listEntities = adjustAndPeek();
		
		Iterator<UpdatedEntity> it = listEntities.iterator();
		while (it.hasNext()) {
			it.next().update(deltaT*timeStretch);
		}
	}
	
	private float timeStretch = 1.0f;
	private float fakelag = 0.0f;
	private float consumedLag = 0.0f;
	
	public void stretchTime(float stretch) {
		if (stretch > 0.01f)
			timeStretch = stretch;
	}
	
	public float getTimeStretch() {
		return timeStretch;
	}
	
	public void fakeLag(float deltaT) {
		fakelag = deltaT;
	}
}
