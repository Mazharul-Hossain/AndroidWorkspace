package net.sf.andpdf.awtpdfrenderer;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class AffineMatrixTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//    	float[] f6 = new float[]{7.98f,0.0f,2.7293000000000003f,7.98f,320.88f,613.8803f};
    	float[] f6 = new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,3.0f};
    	String out = Arrays.toString(f6);
    	System.out.println(out);
    	
		AffineTransform at = new AffineTransform(f6);
		out = at.toString();
		System.out.println(out);
		    	
		Point2D ps = new Point2D.Float(5.0f,7.0f);
		Point2D pd = new Point2D.Float(5.0f,7.0f);
		at.transform(ps, pd);
		System.out.println("trans("+ps.getX()+","+ps.getY()+") = ("+pd.getX()+","+pd.getY()+")");
		
    	at.translate(0.4992f, 0.0f);
    	out = "trans(0.4992f,0)="+at.toString();
    	System.out.println(out);

    	pd.setLocation(ps);
		at.transform(ps, pd);
		System.out.println("trans("+ps.getX()+","+ps.getY()+") = ("+pd.getX()+","+pd.getY()+")");
	}

}
