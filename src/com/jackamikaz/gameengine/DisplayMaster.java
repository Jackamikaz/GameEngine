package com.jackamikaz.gameengine;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.math.Matrix4;
//import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jackamikaz.gameengine.utils.DisplayOrder;
//import com.jackamikaz.gameengine.utils.MatrixStack;
import com.jackamikaz.gameengine.utils.SortedList;
import com.jackamikaz.gameengine.utils.StackOfCollections;


public class DisplayMaster extends StackOfCollections<DisplayedEntity> {

	private class DispEntComp implements Comparator<DisplayedEntity> {

		@Override
		public int compare(DisplayedEntity a, DisplayedEntity b) {
			return a.GetDisplayRank() - b.GetDisplayRank();
		}
		
	}
	
	private SpriteBatch batch;
	private ModelBatch modelBatch;
	private Lights lights;
	//private MatrixStack matrixStack;
	private Camera camera;
	
	public DisplayMaster() {
		batch = new SpriteBatch();
		modelBatch = new ModelBatch(/*
				Gdx.files.internal("data/test.vertex.glsl"),
				Gdx.files.internal("data/test.fragment.glsl"));//*/);
		lights = new Lights();
		//matrixStack = new MatrixStack();
		camera = new PerspectiveCamera(); 
	}
	
	public static SpriteBatch Batch() {return Engine.DisplayMaster().batch;}
	public static ModelBatch ModelBatch() {return Engine.DisplayMaster().modelBatch;}
	public static Lights Lights() {return Engine.DisplayMaster().lights;}
	//public static MatrixStack MatrixStack() {return Engine.DisplayMaster().matrixStack;}
	public static Camera Camera() {return Engine.DisplayMaster().camera;}
	
	//private ShaderProgram replacementShader = null;
	private DispEntComp comparator = null;
	
	
	protected SortedList<DisplayedEntity> NewCollection() {
		if (comparator == null)
			comparator = new DispEntComp();
		return new SortedList<DisplayedEntity>(comparator);
	}
	
	public void Display(float gdt, float glerp) {
		Collection<DisplayedEntity> listEntities = AdjustAndPeek();
		
		int curDispRank = -1;
		boolean using3D = false;
		boolean using2D = false;
		
		Iterator<DisplayedEntity> it = listEntities.iterator();
		while (it.hasNext()) {
			DisplayedEntity cur = it.next();
			
			// check if we need to start or end batches
			int newDispRank = cur.GetDisplayRank();
			if (curDispRank != newDispRank) {
				
				// 3D Batch
				if (curDispRank < DisplayOrder.Start3D.ordinal()
				&&  newDispRank >= DisplayOrder.Start3D.ordinal()) {
					//camera.update();
					modelBatch.begin(camera);
					using3D = true;
				}
				
				// 2D Batch
				if (curDispRank < DisplayOrder.Start2D.ordinal()
				&&  newDispRank >= DisplayOrder.Start2D.ordinal()) {
					if (using3D) {
						modelBatch.end();
						using3D = false;
					}
					batch.begin();
					using2D = true;
				}
				
				curDispRank = newDispRank;
			}
			
			// display
			cur.Display(gdt, glerp);
		}
		
		//end batches if necessary
		if (using3D)
			modelBatch.end();
		if (using2D)
			batch.end();
	}
	
	public void ExecuteCustomLoop(int[] displayTypes, float gdt, float glerp) {
		Collection<DisplayedEntity> listEntities = Peek();
		
		int curDispType = 0;
		
		Iterator<DisplayedEntity> it = listEntities.iterator();
		DisplayedEntity itnext = null;
		
		while (it.hasNext() && curDispType < displayTypes.length) {
			
			if (itnext == null) // new itnext needs to be processed
				itnext = it.next();
			
			int curDispRank = itnext.GetDisplayRank();
			
			if (displayTypes[curDispType] < curDispRank) {
				++curDispType;
			}
			else if (displayTypes[curDispType] == curDispRank) {
				itnext.Display(gdt, glerp);
				itnext = null; //itnext processed;
			}
			else {
				itnext = null; //itnext ignored
			}
		}
	}
	
	/*public void SetReplacementShader(ShaderProgram s) {
		replacementShader = s;
	}
	
	public ShaderProgram GetCorrectShader(ShaderProgram s) {
		return replacementShader == null ? s : replacementShader;
	}*/
	
	private float width;
	private float height;
	
	public static float Width() {
		return Engine.DisplayMaster().width;
	}
	
	public static float Height() {
		return Engine.DisplayMaster().height;
	}
	
	public static float AspectRatio() {
		return Engine.DisplayMaster().width / Engine.DisplayMaster().height;
	}
	
	public void UdpateWidthHeight(float w, float h) {
		width = w;
		height = h;
		camera.viewportWidth = w;
		camera.viewportHeight = h;
	}
	
	public void dispose() {
		batch.dispose();
		modelBatch.dispose();
	}
	
	public void UpdateCamAndSetView(Matrix4 view) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		camera.view.set(view);
		camera.combined.set(camera.projection).mul(camera.view);
	}
}
