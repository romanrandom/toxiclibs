package toxi.geom.util;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import toxi.geom.Vec3D;

/**
 * Extremely bare bones Wavefront OBJ 3D format exporter. Purely handles the
 * writing of data to the .obj file, but does not have any form of mesh
 * management. See {@link OBJMesh} for details.
 * 
 * Needs to get some more TLC in future versions.
 * 
 * @author toxi
 * @see OBJMesh
 */
public class OBJWriter {

	public final String VERSION = "0.26";

	protected FileOutputStream objStream;
	protected PrintWriter objWriter;

	protected int numVerticesWritten = 0;

	protected int numNormalsWritten = 0;

	public void beginSave(String fn) {
		try {
			objStream = new FileOutputStream(fn);
			objWriter = new PrintWriter(objStream);
			objWriter.println("# generated by OBJExport v" + VERSION);
			numVerticesWritten = 0;
			numNormalsWritten = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endSave() {
		try {
			objWriter.flush();
			objWriter.close();
			objStream.flush();
			objStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void face(int a, int b, int c) {
		objWriter.println("f " + a + " " + b + " " + c);
	}

	public void faceList() {
		objWriter.println("s off");
	}

	public void faceWithNormal(int a, int b, int c, int n) {
		objWriter.println("f " + a + "//" + n + " " + b + "//" + n + " " + c
				+ "//" + n);
	}

	public int getCurrNormalOffset() {
		return numNormalsWritten;
	}

	public int getCurrVertexOffset() {
		return numVerticesWritten;
	}

	public void newObject(String name) {
		objWriter.println("o " + name);
	}

	public void normal(Vec3D n) {
		objWriter.println("vn " + n.x + " " + n.y + " " + n.z);
		numNormalsWritten++;
	}

	public void vertex(Vec3D v) {
		objWriter.println("v " + v.x + " " + v.y + " " + v.z);
		numVerticesWritten++;
	}
}
