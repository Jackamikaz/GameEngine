package com.jackamikaz.gameengine.utils;

import java.util.Collection;
import java.util.Stack;

public abstract class StackOfCollections<obj> implements IStackOfCollections<obj> {

	private class Collec<objj> {
		
		public Collection<objj> collection;
		
		public Collection<objj> toBeAdded;
		public Collection<objj> toBeRemoved;
		
		public Collec(Collection<objj> c, Collection<objj> tba, Collection<objj> tbr) {
			collection = c;
			toBeAdded = tba;
			toBeRemoved = tbr;
		}
		
		public void adjustCollection() {
			collection.addAll(toBeAdded);
			collection.removeAll(toBeRemoved);
			
			toBeAdded.clear();
			toBeRemoved.clear();
		}
	}
	
	private Stack<Collec<obj>> entities;

	protected StackOfCollections() {
		entities = new Stack<Collec<obj>>();
		push();
	}
	
	public void add(obj entity) {
		entities.peek().toBeAdded.add(entity);
	}
	public void remove(obj entity) {
		entities.peek().toBeRemoved.add(entity);
	}
	
	public void push(Collection<obj> entityList) {
		entities.push(
				new Collec<obj>(entityList,newCollection(),newCollection()));
	}
	public void push() {push(newCollection());}
	protected abstract Collection<obj> newCollection();
	
	public void pop() {entities.pop();}
	
	public void clear() {entities.clear(); push();}
	
	protected Collection<obj> adjustAndPeek() {
		Collec<obj> collec = entities.peek();
		collec.adjustCollection();
		return collec.collection;
	}
	
	public Collection<obj> peek() {
		return entities.peek().collection;
	}
}
