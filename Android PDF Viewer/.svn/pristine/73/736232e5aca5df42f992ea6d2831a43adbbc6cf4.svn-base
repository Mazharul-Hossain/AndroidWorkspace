package org.icepdf.core.pobjects;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Rectangle2Df implements Serializable, Cloneable {

    private static final Logger logger =
        Logger.getLogger(Rectangle2Df.class.toString());

    /**
	 * 
	 */
	private static final long serialVersionUID = -378005857945037193L;
	public float x;
    public float y;
    public float width;
    public float height;

    public Rectangle2Df() {
        setRect(0, 0, 0, 0);
    }

    public Rectangle2Df(float x, float y, float width, float height) {
        setRect(x, y, width, height);
    }

    public void setRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

	public Rect toRect() {
		return new Rect((int)x, (int)y, (int)(x+width),(int)(y+height));
	}

	public RectF toRectF() {
		return new RectF(x, y, x+width,y+height);
	}

	public float getHeight() {
		return height;
	}
	public float getWidth() {
		return width;
	}

	/**
	 * union
	 * @param rectangle2Df
	 */
	public void add(Rectangle2Df other) {
		if (other != null) {
			RectF otherRF = other.toRectF();
			otherRF.union(toRectF());
			setRect(otherRF.left, otherRF.top, otherRF.right-otherRF.left, otherRF.bottom-otherRF.top);
		}
	}

	public float getMinX() {
		if (width > 0)
			return x;
		return x+width;
	}
	public float getMinY() {
		if (height > 0)
			return y;
		return y+height;
	}

	public float getMaxX() {
		if (width > 0)
			return x+width;
		return x;
	}
	public float getMaxY() {
		if (height > 0)
			return y+height;
		return y;
	}

	public void add(PointF dst) {
		RectF r = new RectF(x,y,x+width, y+height);
		r.union(dst.x, dst.y);
		x = r.left;
		y = r.top;
		width = r.right - r.left;
		height = r.bottom - r.top;
	}

	
	@Override
	protected Object clone() {
		
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
            logger.log(Level.FINE, "Error loading PDF file during linear parse.", e);
			e.printStackTrace();
		}
		return result;
	}

	public Rectangle2Df createIntersection(Rect clip) {
		if (clip == null)
			return new Rectangle2Df(x,y,width,height);
		Rect rThis = new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
		if (!rThis.intersect(clip))
			return new Rectangle2Df(0,0,0,0);
		return new Rectangle2Df(rThis.left,rThis.top,rThis.width(),rThis.bottom-rThis.height());
	}
}
