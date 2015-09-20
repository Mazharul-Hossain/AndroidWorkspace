package net.sf.andpdf.utils;

import android.graphics.Matrix;
import android.graphics.PointF;

public class Util {

	/**
	 * MSCALE_X MSKEW_X  MTRANS_X
	 * MSKEW_Y  MSCALE_Y MTRANS_Y
	 * MPERSP_0 MPERSP_1 MPERSP_2
	 * 
	 * @param mat
	 * @param m00 MSCALE_X
	 * @param m01 MSKEW_X
	 * @param m10 MSKEW_Y
	 * @param m11 MSCALE_Y
	 * @param m02 MTRANS_X
	 * @param m12 MTRANS_Y
	 */
	public static void setMatValues(Matrix mat, float m00, float m01, float m10, float m11, float m02, float m12) {
	   	float[] fs = {m00, m01, m02, m10, m11, m12, 0,0,1};
	   	mat.setValues(fs);
	}

	public static void setMatValues(Matrix mat, float[] m6) {
		setMatValues(mat, m6[0], m6[1], m6[2], m6[3], m6[4], m6[5]);
	}
	
	public static Matrix createMatrix(float m00, float m01, float m10, float m11, float m02, float m12) {
		Matrix result = new Matrix();
		setMatValues(result, m00,m01,m10,m11,m02,m12);
		return result;
	}
	public static Matrix createMatrix(float[] m6) {
		return createMatrix(m6[0], m6[1], m6[2], m6[3], m6[4], m6[5]);
	}

	public static PointF transform(Matrix mat, PointF src, PointF dst) {
		float[] pts = {src.x, src.y};
		mat.mapPoints(pts);
		if (dst == null)
			return new PointF(pts[0], pts[1]);
		dst.set(pts[0], pts[1]);
		return dst;
	}

}
