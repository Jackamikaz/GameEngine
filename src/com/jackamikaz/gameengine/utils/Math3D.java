package com.jackamikaz.gameengine.utils;

import com.badlogic.gdx.math.Matrix4;
//import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jackamikaz.gameengine.DisplayMaster;

public class Math3D {
	
	private static Matrix4 tmpMat = new Matrix4();
	
	public static boolean IsMouseAtPos(final Vector3 pos, final Vector2 mousePos, final Matrix4 viewProj)
	{
		Tmp.vec2.b.set(SpaceToScreen(pos,viewProj)).sub(mousePos);
		return Tmp.vec2.b.len2() < 25.0f;
	}
	
	public static Vector2 SpaceToScreen(final Vector3 spacepos, final Matrix4 viewProj)
	{
		Vector3 tr = spacepos.cpy().prj(viewProj);
		
		Tmp.vec2.c.x = (tr.x + 1.0f)*0.5f*DisplayMaster.Width();
		Tmp.vec2.c.y = (tr.y + 1.0f)*0.5f*DisplayMaster.Height();
		
		return Tmp.vec2.c;
	}
	
	public static void FollowMouseAlongLine(Matrix4 proj, Matrix4 view, Vector2 mousePos, Vector3 origPos, Vector3 dirPos, Vector3 out)
	{
		float normMX = (mousePos.x / DisplayMaster.Width()) * 2.0f - 1.0f;
		float normMY = (mousePos.y / DisplayMaster.Height()) * 2.0f - 1.0f;
		
		tmpMat.set(view).inv();
		
		float factor = 1.0f;
		
		Vector3 mouseray = Tmp.vec3.b.set(normMX*(proj.val[Matrix4.M11]/proj.val[Matrix4.M00])*factor, normMY*factor, -proj.val[Matrix4.M11]*factor);
		mouseray.mul(tmpMat);
		
		Vector3 campos = Tmp.vec3.c.set(tmpMat.val[Matrix4.M03],tmpMat.val[Matrix4.M13],tmpMat.val[Matrix4.M23]);
		
		GetClosestPointBetweenTwoLines(origPos, dirPos, campos, mouseray, out);
	}
	
	//http://paulbourke.net/geometry/lineline3d/
	static public void GetClosestPointBetweenTwoLines(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, Vector3 out)
	{
		//float dxxxx = (p.x - p.x)*(p.x - p.x) + (p.y - p.y)*(p.y - p.y) + (p.z - p.z)*(p.z - p.z);
		float d1343 = (p1.x - p3.x)*(p4.x - p3.x) + (p1.y - p3.y)*(p4.y - p3.y) + (p1.z - p3.z)*(p4.z - p3.z);
		float d4321 = (p4.x - p3.x)*(p2.x - p1.x) + (p4.y - p3.y)*(p2.y - p1.y) + (p4.z - p3.z)*(p2.z - p1.z);
		float d1321 = (p1.x - p3.x)*(p2.x - p1.x) + (p1.y - p3.y)*(p2.y - p1.y) + (p1.z - p3.z)*(p2.z - p1.z);
		float d4343 = (p4.x - p3.x)*(p4.x - p3.x) + (p4.y - p3.y)*(p4.y - p3.y) + (p4.z - p3.z)*(p4.z - p3.z);
		float d2121 = (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y) + (p2.z - p1.z)*(p2.z - p1.z);
		
		float mua = ( d1343 * d4321 - d1321 * d4343 ) / ( d2121 * d4343 - d4321 * d4321 );
		
		// out = p1 + mua ( p2 - p1 )
		out.set(p2).sub(p1).scl(mua).add(p1);
	}
	
	/*static public void Matrix4Slerp(Matrix4 inout, Matrix4 target, float alpha) {
		Quaternion qf = Tmp.quat.a;
		Quaternion qt = Tmp.quat.b;
		
		Vector3 vf = Tmp.vec3.a;
		Vector3 vt = Tmp.vec3.b;
		
		inout.getRotation(qf);
		target.getRotation(qt);
		
		inout.getTranslation(vf);
		target.getTranslation(vt);
		
		qf.slerp(qt, alpha);
		vf.lerp(vt, alpha);
		
		inout.set(qf).trn(vf);
	}*/
}
