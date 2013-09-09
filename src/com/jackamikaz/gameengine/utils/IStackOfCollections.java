package com.jackamikaz.gameengine.utils;

import java.util.Collection;

public interface IStackOfCollections<obj> {
	public void add(obj entity);
	public void remove(obj entity);
	
	public void push(Collection<obj> entityList);
	public void push();
	
	public void pop();
}
