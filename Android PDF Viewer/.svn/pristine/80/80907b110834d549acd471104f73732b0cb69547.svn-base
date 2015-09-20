/*
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * "The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 *  * The Original Code is ICEpdf 3.0 open source software code, released
 * May 1st, 2009. The Initial Developer of the Original Code is ICEsoft
 * Technologies Canada, Corp. Portions created by ICEsoft are Copyright (C)
 * 2004-2009 ICEsoft Technologies Canada, Corp. All Rights Reserved.
 *
 * Contributor(s): _____________________.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"
 * License), in which case the provisions of the LGPL License are
 * applicable instead of those above. If you wish to allow use of your
 * version of this file only under the terms of the LGPL License and not to
 * allow others to use your version of this file under the MPL, indicate
 * your decision by deleting the provisions above and replace them with
 * the notice and other provisions required by the LGPL License. If you do
 * not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the LGPL License."
 *
 */
package org.icepdf.core.pobjects.annotations;

import org.icepdf.core.pobjects.*;
import org.icepdf.core.pobjects.actions.*;
import org.icepdf.core.pobjects.graphics.Shapes;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.icepdf.core.util.Library;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;

import org.icepdf.core.util.Hashtable;
import java.util.Vector;

/**
 * <p>An <code>Annotation</code> class associates an object such as a note, sound, or movie with
 * a location on a page of a PDF document, or provides a way to interact with
 * the user by means of the mouse and keyboard.</p>
 * <p/>
 * <p>This class allows direct access to the a Annotations dictionary.
 * Developers can take advantage of this information as they see fit.  It is
 * important to note that an annotations' rectangle coordinates are defined
 * in the PDF document space.  In order to map the rectangle coordinates to
 * a view, they must be converted from the Cartesian plain to the the Java2D
 * plain.  The PageView method getPageBounds() can be used to locate the position
 * of a page within its parent component.</p>
 * <p/>
 * Base class of all the specific Annotation types
 * <p/>
 * Taken from the PDF 1.6 spec, here is some relevant documentation,
 * along with some additional commentary
 * <p/>
 * <h2>8.4.1 Annotation Dictionaries</h2>
 * <table border=1>
 * <tr>
 * <td>Key</td>
 * <td>Type</td>
 * <td>Value</td>
 * </tr>
 * <tr>
 * <td><b>Type</b></td>
 * <td>name</td>
 * <td>(<i>Optional</i>) The type of PDF object that this dictionary describes;
 * if present, must be <b>Annot</b> for an annotation dictionary.</td>
 * </tr>
 * <tr>
 * <td><b>Subtype</b></td>
 * <td>name</td>
 * <td>(<i>Required</i>) The type of annotation that this dictionary describes.</td>
 * </tr>
 * <tr>
 * <td><b>Rect</b></td>
 * <td>rectangle</td>
 * <td>(<i>Required</i>) The <i>annotation rectangle</i>, defining the location of the
 * annotation on the page in default user space units.</td>
 * <td>getUserspaceLocation()</td>
 * </tr>
 * <tr>
 * <td><b>Contents</b></td>
 * <td>text string</td>
 * <td>(<i>Optional</i>) Text to be displayed for the annotation or, if this type of annotation
 * does not display text, an alternate description of the annotation’s contents
 * in human-readable form. In either case, this text is useful when
 * extracting the document’s contents in support of accessibility to users with
 * disabilities or for other purposes (see Section 10.8.2, “Alternate Descriptions”).
 * See Section 8.4.5, “Annotation Types” for more details on the meaning
 * of this entry for each annotation type.</td>
 * </tr>
 * <tr>
 * <td><b>P</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.3; not used in FDF files</i>) An indirect reference to the page
 * object with which this annotation is associated.</td>
 * </tr>
 * <tr>
 * <td><b>NM</b></td>
 * <td>text string</td>
 * <td>(<i>Optional; PDF 1.4</i>) The <i>annotation name</i>, a text string uniquely identifying it
 * among all the annotations on its page.</td>
 * </tr>
 * <tr>
 * <td><b>M</b></td>
 * <td>date or string</td>
 * <td>(<i>Optional; PDF 1.1</i>) The date and time when the annotation was most
 * recently modified. The preferred format is a date string as described in Section
 * 3.8.3, “Dates,” but viewer applications should be prepared to accept and
 * display a string in any format. (See implementation note 78 in Appendix H.)</td>
 * </tr>
 * <tr>
 * <td><b>F</b></td>
 * <td>integer</td>
 * <td>(<i>Optional; PDF 1.1</i>) A set of flags specifying various characteristics of the annotation
 * (see Section 8.4.2, “Annotation Flags”). Default value: 0.</td>
 * </tr>
 * <tr>
 * <td><b>BS</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.2</i>) A border style dictionary specifying the characteristics of
 * the annotation’s border (see Section 8.4.3, “Border Styles”; see also implementation
 * notes 79 and 86 in Appendix H).<br>
 * <br>
 * <i><b>Note:</b> This entry also specifies the width and dash pattern for the lines drawn by
 * line, square, circle, and ink annotations. See the note under <b>Border</b> (below) for
 * additional information.</i><br>
 * <br>
 * Table 8.13 summarizes the contents of the border style dictionary. If neither
 * the <b>Border</b> nor the <b>BS</b> entry is present, the border is drawn as a solid line with a
 * width of 1 point.</td>
 * </tr>
 * <tr>
 * <td><b>AP</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.2</i>) An <i>appearance dictionary</i> specifying how the annotation
 * is presented visually on the page (see Section 8.4.4, “Appearance Streams” and
 * also implementation notes 79 and 80 in Appendix H). Individual annotation
 * handlers may ignore this entry and provide their own appearances.<br>
 * <br>
 * For convenience in managing appearance streams that are used repeatedly, the AP
 * entry in a PDF document’s name dictionary (see Section 3.6.3, “Name Dictionary”)
 * can contain a name tree mapping name strings to appearance streams. The
 * name strings have no standard meanings; no PDF objects refer to appearance
 * streams by name.</td>
 * </tr>
 * <tr>
 * <td><b>AS</b></td>
 * <td>name</td>
 * <td>(<i>Required if the appearance dictionary <b>AP</b> contains one or more subdictionaries;
 * PDF 1.2</i>) The annotation’s <i>appearance state</i>, which selects the applicable
 * appearance stream from an appearance subdictionary (see Section 8.4.4, “Appearance
 * Streams” and also implementation note 79 in Appendix H).</td>
 * </tr>
 * <tr>
 * <td><b>Border</b></td>
 * <td>array</td>
 * <td>(<i>Optional</i>) An array specifying the characteristics of the annotation’s border.
 * The border is specified as a rounded rectangle.<br>
 * <br>
 * In PDF 1.0, the array consists of three numbers defining the horizontal corner
 * radius, vertical corner radius, and border width, all in default user space
 * units. If the corner radii are 0, the border has square (not rounded) corners; if
 * the border width is 0, no border is drawn. (See implementation note 81 in
 * Appendix H.) <br>
 * <br>
 * In PDF 1.1, the array may have a fourth element, an optional <i>dash array</i>
 * defining a pattern of dashes and gaps to be used in drawing the border. The
 * dash array is specified in the same format as in the line dash pattern parameter
 * of the graphics state (see “Line Dash Pattern” on page 187). For example, a
 * <b>Border</b> value of [0 0 1 [3 2]] specifies a border 1 unit wide, with square corners,
 * drawn with 3-unit dashes alternating with 2-unit gaps. Note that no
 * dash phase is specified; the phase is assumed to be 0. (See implementation
 * note 82 in Appendix H.)<br>
 * <br>
 * <i><b>Note:</b> In PDF 1.2 or later, this entry may be ignored in favor of the <b>BS</b>
 * entry (see above); see implementation note 86 in Appendix H.</i><br>
 * <br>
 * Default value: [0 0 1].</td>
 * </tr>
 * <tr>
 * <td><b>BE</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.5</i>) Some annotations (square, circle, and polygon) may
 * have a <b>BE</b> entry, which is a <i>border effect</i> dictionary that specifies an effect
 * to be applied to the border of the annotations. Its entries are listed in Table 8.14.</td>
 * </tr>
 * <tr>
 * <td><b>C</b></td>
 * <td>array</td>
 * <td>(<i>Optional; PDF 1.1</i>) An array of three numbers in the range 0.0 to 1.0, representing
 * the components of a color in the <b>DeviceRGB</b> color space. This color is
 * used for the following purposes:
 * <ul>
 * <li>The background of the annotation’s icon when closed
 * <li>The title bar of the annotation’s pop-up window
 * <li>The border of a link annotation
 * </ul></td>
 * </tr>
 * <tr>
 * <td><b>A</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.1</i>) An action to be performed when the annotation is activated
 * (see Section 8.5, “Actions”).<br>
 * <br>
 * <i><b>Note:</b> This entry is not permitted in link annotations if a Dest entry is present
 * (see “Link Annotations” on page 587). Also note that the A entry in movie annotations
 * has a different meaning (see “Movie Annotations” on page 601).</i></td>
 * </tr>
 * <tr>
 * <td><b>AA</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.2</i>) An additional-actions dictionary defining the annotation’s
 * behavior in response to various trigger events (see Section 8.5.2,
 * “Trigger Events”). At the time of publication, this entry is used only by widget
 * annotations.</td>
 * </tr>
 * <tr>
 * <td><b>StructParent</b></td>
 * <td>integer</td>
 * <td>(<i>(Required if the annotation is a structural content item; PDF 1.3</i>) The integer
 * key of the annotation’s entry in the structural parent tree (see “Finding Structure
 * Elements from Content Items” on page 797).</td>
 * </tr>
 * <tr>
 * <td><b>OC</b></td>
 * <td>dictionary</td>
 * <td>(<i>Optional; PDF 1.5</i>) An optional content group or optional content membership
 * dictionary (see Section 4.10, “Optional Content”) specifying the optional
 * content properties for the annotation. Before the annotation is drawn, its visibility
 * is determined based on this entry as well as the annotation flags specified
 * in the <b>F</b> entry (see Section 8.4.2, “Annotation Flags”). If it is determined
 * to be invisible, the annotation is skipped, as if it were not in the document.</td>
 * </tr>
 * </table>
 * <p/>
 * <p/>
 * <h2>8.4.2 Annotation Flags</h2>
 * The value of the annotation dictionary’s <b>F</b> entry is an unsigned 32-bit integer containing
 * flags specifying various characteristics of the annotation. Bit positions
 * within the flag word are numbered from 1 (low-order) to 32 (high-order). Table
 * 8.12 shows the meanings of the flags; all undefined flag bits are reserved and must
 * be set to 0.
 * <table border=1>
 * <tr>
 * <td>Bit position</td>
 * <td>Name</td>
 * <td>Meaning</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>Invisible</td>
 * <td>If set, do not display the annotation if it does not belong to one of the standard
 * annotation types and no annotation handler is available. If clear, display such an
 * unknown annotation using an appearance stream specified by its appearance
 * dictionary, if any (see Section 8.4.4, “Appearance Streams”).</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>Hidden</td>
 * <td>If set, do not display or print the annotation or allow it to interact
 * with the user, regardless of its annotation type or whether an annotation
 * handler is available. In cases where screen space is limited, the ability to hide
 * and show annotations selectively can be used in combination with appearance
 * streams (see Section 8.4.4, “Appearance Streams”) to display auxiliary pop-up
 * information similar in function to online help systems. (See implementation
 * note 83 in Appendix H.)</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>Print</td>
 * <td>If set, print the annotation when the page is printed. If clear, never
 * print the annotation, regardless of whether it is displayed on the screen. This
 * can be useful, for example, for annotations representing interactive pushbuttons,
 * which would serve no meaningful purpose on the printed page. (See
 * implementation note 83 in Appendix H.)</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>NoZoom</td>
 * <td>If set, do not scale the annotation’s appearance to match the magnification
 * of the page. The location of the annotation on the page (defined by the
 * upper-left corner of its annotation rectangle) remains fixed, regardless of the
 * page magnification. See below for further discussion.</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>NoRotate</td>
 * <td>If set, do not rotate the annotation’s appearance to match the rotation
 * of the page. The upper-left corner of the annotation rectangle remains in a fixed
 * location on the page, regardless of the page rotation. See below for further discussion.</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>NoView</td>
 * <td>If set, do not display the annotation on the screen or allow it to
 * interact with the user. The annotation may be printed (depending on the setting
 * of the Print flag) but should be considered hidden for purposes of on-screen
 * display and user interaction.</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>ReadOnly</td>
 * <td>If set, do not allow the annotation to interact with the user. The
 * annotation may be displayed or printed (depending on the settings of the
 * NoView and Print flags) but should not respond to mouse clicks or change its
 * appearance in response to mouse motions.<br>
 * <br>
 * <i><b>Note:</b> This flag is ignored for widget annotations; its function is subsumed by the
 * ReadOnly flag of the associated form field (see Table 8.66 on page 638).</i></td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>Locked</td>
 * <td>If set, do not allow the annotation to be deleted or its properties (including
 * position and size) to be modified by the user. However, this flag does
 * not restrict changes to the annotation’s contents, such as the value of a form
 * field. (See implementation note 84 in Appendix H.)</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>ToggleNoView</td>
 * <td>If set, invert the interpretation of the NoView flag for certain events. A
 * typical use is to have an annotation that appears only when a mouse cursor is
 * held over it; see implementation note 85 in Appendix H.</td>
 * </tr>
 * </table>
 *
 * @author Mark Collette
 * @since 2.5
 */

public class Annotation extends Dictionary {

	private Paint mPaint = new Paint(); 
    // borders style of the annotation, can be null
    protected BorderStyle borderStyle;
    // border color of annotation.
    protected int borderColor;

    public static Annotation buildAnnotation(Library library, Hashtable hashTable) {
        Annotation annot = null;
        Name subtype = (Name) hashTable.get("Subtype");
        if (subtype != null) {
            if (subtype.equals("Link"))
                annot = new LinkAnnotation(library, hashTable);
        }
        if (annot == null)
            annot = new Annotation(library, hashTable);
        return annot;
    }

    /**
     * Creates a new instance of an Annotation.
     *
     * @param l document library.
     * @param h dictionary entries.
     */
    public Annotation(Library l, Hashtable h) {
        super(l, h);
//System.out.println( "Construction: " + this );

        // parse out border style if available
        Hashtable BS = (Hashtable) getObject("BS");
        if (BS != null) {
            borderStyle = new BorderStyle(library, BS);
        }
    }

    /**
     * Gets the type of annotation that this dictionary describes.
     * For compatibility with the old org.icepdf.core.pobjects.Annotation.getSubType()
     *
     * @return subtype of annotation
     */
    public String getSubType() {
        return library.getName(entries, "Subtype");
    }

    /**
     * Gets the annotation rectangle, and defines the location of the annotation on
     * the page in default user space units.
     * For compatibility with the old org.icepdf.core.pobjects.Annotation.getRectangle()
     *
     * @return rectangle of annotation
     */
    public Rectangle2Df getUserSpaceRectangle() {
        return library.getRectangle(entries, "Rect");
    }

    /**
     * Gets the action to be performed when the annotation is activated.
     * For compatibility with the old org.icepdf.core.pobjects.Annotation.getAction()
     *
     * @return action to be activated, if no action, null is returned.
     */
    public org.icepdf.core.pobjects.actions.Action getAction() {

        Hashtable h1 = library.getDictionary(entries, "A");
        if (h1 != null) {
            String actionType = ((Name) h1.get("S")).getName();
            if (actionType != null) {

                if (actionType.equals(Action.ACTION_TYPE_GOTO)) {
                    return new GoToAction(library, h1);
                } else if (actionType.equals(Action.ACTION_TYPE_GOTO_REMOTE)) {
                    return new GoToRAction(library, h1);
                } else if (actionType.equals(Action.ACTION_TYPE_LAUNCH)) {
                    return new LaunchAction(library, h1);
                } else if (actionType.equals(Action.ACTION_TYPE_URI)) {
                    return new URIAction(library, h1);
                } else {
                    return new Action(library, h1);
                }
            }
        }
        return null;
    }

    public boolean allowScreenNormalMode() {
        if (!allowScreenOrPrintRenderingOrInteraction())
            return false;
        return !getFlagNoView();
    }

    public boolean allowScreenRolloverMode() {
        if (!allowScreenOrPrintRenderingOrInteraction())
            return false;
        if (getFlagNoView() && !getFlagToggleNoView())
            return false;
        return !getFlagReadOnly();
    }

    public boolean allowScreenDownMode() {
        if (!allowScreenOrPrintRenderingOrInteraction())
            return false;
        if (getFlagNoView() && !getFlagToggleNoView())
            return false;
        return !getFlagReadOnly();
    }

    public boolean allowPrintNormalMode() {
        if (!allowScreenOrPrintRenderingOrInteraction())
            return false;
        return getFlagPrint();
    }

    public boolean allowAlterProperties() {
        return !getFlagLocked();
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public Annotation getParentAnnotation() {
        Annotation parent = null;

        Object ob = getObject("Parent");
        if (ob instanceof Reference)
            ob = library.getObject((Reference) ob);
        if (ob instanceof Annotation)
            parent = (Annotation) ob;
        else if (ob instanceof Hashtable)
            parent = Annotation.buildAnnotation(library, (Hashtable) ob);

        return parent;
    }

    public Page getPage() {
        Page page = (Page) getObject("P");
        if (page == null) {
            Annotation annot = getParentAnnotation();
            if (annot != null)
                page = annot.getPage();
        }
        return page;
    }

    public void render(Canvas origG, int renderHintType,
                       float totalRotation, float userZoom,
                       boolean tabSelected) {
        if (!allowScreenOrPrintRenderingOrInteraction())
            return;
        if (renderHintType == GraphicsRenderingHints.SCREEN && !allowScreenNormalMode())
            return;
        if (renderHintType == GraphicsRenderingHints.PRINT && !allowPrintNormalMode())
            return;

//System.out.println("render(-)  " + this);
        Rectangle2Df rect = getUserSpaceRectangle();
// Show original ractangle, without taking into consideration NoZoom and NoRotate
//System.out.println("Original rectangle: " + rect);
//origG.setColor( Color.blue );
//origG.draw( rect );
//origG.setColor( Color.red );
//Line2D.Double topLine = new Line2D.Double( rect.getMinX(), rect.getMaxY(), rect.getMaxX(), rect.getMaxY() );
//origG.draw( topLine );
//origG.setColor( Color.yellow );
//Line2D.Double bottomLine = new Line2D.Double( rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMinY() );
//origG.draw( bottomLine );

        origG.save();
        Matrix oldAT = origG.getMatrix();
        Rect oldClip = origG.getClipBounds();

        // Simply uncomment the //// lines to use a different Graphics object
        Canvas g = origG;
        ////Graphics2D g = (Graphics2D) origG.create();

        Matrix at = new Matrix(oldAT);
        at.preTranslate(rect.getMinX(), rect.getMinY());

        boolean noRotate = getFlagNoRotate();
        if (noRotate) {
            float unRotation = -totalRotation;
            while (unRotation < 0.0f)
                unRotation += 360.0f;
            while (unRotation > 360.0f)
                unRotation -= 360.0f;
            if (unRotation == -0.0f)
                unRotation = 0.0f;

            if (unRotation != 0.0) {
                float radians = (float)(unRotation * Math.PI / 180.0);
                Matrix rotationTransform = new Matrix();
                rotationTransform.preRotate(radians);
                PointF origTopLeftCorner = new PointF(0.0f, Math.abs(rect.getHeight()));
                float[] pts = {origTopLeftCorner.x, origTopLeftCorner.y};
                rotationTransform.mapPoints(pts);
                PointF rotatedTopLeftCorner = new PointF(pts[0], pts[1]);
//                at.translate(origTopLeftCorner.getX() - rotatedTopLeftCorner.getX(),
//                        origTopLeftCorner.getY() - rotatedTopLeftCorner.getY());
//                at.rotate(radians);
                at.preTranslate(origTopLeftCorner.x - rotatedTopLeftCorner.x,
                        origTopLeftCorner.y - rotatedTopLeftCorner.y);
                at.preRotate(radians);
            }
        }

        boolean noZoom = getFlagNoZoom();
        if (noZoom) {
        	float[] values = new float[6];
        	at.getValues(values);
        	float scaleY = Math.abs(values[Matrix.MSCALE_Y]);
            if (scaleY != 1.0) {
                float scaleX = Math.abs(values[Matrix.MSCALE_X]);
                float rectHeight = Math.abs(rect.getHeight());
                float resizedY = rectHeight * ((scaleY - 1.0f) / scaleY);
                at.preTranslate(0.0f, resizedY);
                at.preScale(1.0f / scaleX, 1.0f / scaleY);
            }
        }

        g.setMatrix(at);
        Rect preAppearanceStreamClip = g.getClipBounds();
        g.clipRect(deriveDrawingRectangle().toRect(), Op.REPLACE);

        renderAppearanceStream(g);

        g.setMatrix(at);
        g.clipRect(preAppearanceStreamClip, Op.REPLACE);

        if (tabSelected)
            renderBorderTabSelected(g);
        else
            renderBorder(g);

        g.restore();

        ////g.dispose();

// Show the top left corner, that NoZoom and NoRotate annotations cling to
//origG.setColor( Color.blue );
//Rectangle2D.Double topLeft = new Rectangle2D.Double(
//    rect.getMinX(), rect.getMaxY()-3, 3, 3 );
//origG.fill( topLeft );
    }

    protected void renderAppearanceStream(Canvas g) {
        Object AP = getObject("AP");
        if (AP instanceof Hashtable) {
            Object N = library.getObject((Hashtable) AP, "N");
            if (N instanceof Hashtable) {
                Object AS = getObject("AS");
                if (AS != null)
                    N = library.getObject((Hashtable) N, AS.toString());
            }

            if (N instanceof Form) {
//g.setColor( Color.blue );
//Rectangle2D.Double newRect = deriveDrawingRectangle();
//g.draw( newRect );
                Form form = (Form) N;
                form.init();
                Matrix matrix = form.getMatrix();
                if (matrix != null)
                    g.concat(matrix);
//System.out.println("Form: " + form.getEntries());
//String str = new String( form.getBytes() );
//System.out.println( str );
                Shapes shapes = form.getShapes();
//System.out.println("Shapes: " + shapes + "  count: " + shapes.getShapesCount());
                shapes.paint(g);
            }
        }
    }

    protected void renderBorder(Canvas g) {
//        if( false ) {
//            float width = 1.0f;
//            Rectangle2D.Float jrect = deriveBorderDrawingRectangle( width );
//            g.setColor( Color.red );
//            g.setStroke( new BasicStroke(width) );
//            g.draw( jrect );
//            return;
//        }

        int borderColor = getBorderColor();
        if (borderColor != Color.TRANSPARENT)
        	mPaint.setColor(borderColor);

        BorderStyle bs = getBorderStyle();
        if (bs != null) {
            float width = bs.getStrokeWidth();
            if (width > 0.0f) {
                Rectangle2Df jrect = deriveBorderDrawingRectangle(width);

                if (bs.isStyleSolid()) {
                    if (borderColor != Color.TRANSPARENT) {
                    	// TODO [FHe]: basic stroke
//                    	g.setStroke(new BasicStroke(width));
                        g.drawRect(jrect.toRect(), mPaint);
                    }
                } else if (bs.isStyleDashed()) {
                    if (borderColor != Color.TRANSPARENT) {
                    	// TODO [FHe]: basic stroke
//                        BasicStroke stroke = new BasicStroke(
//                                width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//                                10.0f, bs.getDashArray(), 0.0f);
//                        g.setStroke(stroke);
                        g.drawRect(jrect.toRect(), mPaint);
                    }
                } else if (bs.isStyleBeveled()) {
                    if (borderColor != Color.TRANSPARENT) {
                        jrect = deriveDrawingRectangle();

                        // TODO [FHe]: basic stroke
//                        g.setStroke(new BasicStroke(1.0f));
                        RectF line;

                        // Upper top
                        // TODO [FHe]: check border style
//                        g.setColor(BorderStyle.LIGHT);
                        mPaint.setColor(Color.GRAY);
                        line = new RectF( // Top line
                                jrect.getMinX() + 1.0f, jrect.getMaxY() - 1.0f, jrect.getMaxX() - 2.0f, jrect.getMaxY() - 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Left line
                                jrect.getMinX() + 1.0f, jrect.getMinY() + 2.0f, jrect.getMinX() + 1.0f, jrect.getMaxY() - 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);

                        // Inner top
                        // TODO [FHe]: check border style
//                        g.setColor(BorderStyle.LIGHTEST);
                        mPaint.setColor(Color.LTGRAY);
                        line = new RectF( // Top line
                                jrect.getMinX() + 2.0f, jrect.getMaxY() - 2.0f, jrect.getMaxX() - 3.0f, jrect.getMaxY() - 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Left line
                                jrect.getMinX() + 2.0f, jrect.getMinY() + 3.0f, jrect.getMinX() + 2.0f, jrect.getMaxY() - 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);

                        // Inner bottom
                        // TODO [FHe]: check border style
//                        g.setColor(BorderStyle.DARK);
                        mPaint.setColor(Color.DKGRAY);
                        line = new RectF( // Bottom line
                                jrect.getMinX() + 2.0f, jrect.getMinY() + 2.0f, jrect.getMaxX() - 2.0f, jrect.getMinY() + 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Right line
                                jrect.getMaxX() - 2.0f, jrect.getMinY() + 2.0f, jrect.getMaxX() - 2.0f, jrect.getMaxY() - 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);

                        // Lower bottom
                        // TODO [FHe]: check border style
//                        g.setColor(BorderStyle.DARKEST);
                        mPaint.setColor(Color.BLACK);
                        line = new RectF( // Bottom line
                                jrect.getMinX() + 1.0f, jrect.getMinY() + 1.0f, jrect.getMaxX() - 1.0f, jrect.getMinY() + 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Right line
                                jrect.getMaxX() - 1.0f, jrect.getMinY() + 1.0f, jrect.getMaxX() - 1.0f, jrect.getMaxY() - 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                    }
                } else if (bs.isStyleInset()) {
                    if (borderColor != Color.TRANSPARENT) {
                        jrect = deriveDrawingRectangle();

                        // TODO [FHe]: basic stroke
//                        g.setStroke(new BasicStroke(1.0f));
                        RectF line;

                        // Upper top
                        // TODO [FHe]: check border style
//                      g.setColor(BorderStyle.DARK);
                        mPaint.setColor(Color.DKGRAY);
                        line = new RectF( // Top line
                                jrect.getMinX() + 1.0f, jrect.getMaxY() - 1.0f, jrect.getMaxX() - 1.0f, jrect.getMaxY() - 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Left line
                                jrect.getMinX() + 1.0f, jrect.getMinY() + 1.0f, jrect.getMinX() + 1.0f, jrect.getMaxY() - 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);

                        // Inner top
                        // TODO [FHe]: check border style
//                        g.setColor(BorderStyle.DARKEST);
                        mPaint.setColor(Color.BLACK);
                        line = new RectF( // Top line
                                jrect.getMinX() + 2.0f, jrect.getMaxY() - 2.0f, jrect.getMaxX() - 2.0f, jrect.getMaxY() - 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Left line
                                jrect.getMinX() + 2.0f, jrect.getMinY() + 2.0f, jrect.getMinX() + 2.0f, jrect.getMaxY() - 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);

                        // Inner bottom
                        // TODO [FHe]: check border style
//                      g.setColor(BorderStyle.LIGHTEST);
                        mPaint.setColor(Color.LTGRAY);
                        line = new RectF( // Bottom line
                                jrect.getMinX() + 3.0f, jrect.getMinY() + 2.0f, jrect.getMaxX() - 2.0f, jrect.getMinY() + 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Right line
                                jrect.getMaxX() - 2.0f, jrect.getMinY() + 2.0f, jrect.getMaxX() - 2.0f, jrect.getMaxY() - 3.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);

                        // Lower bottom
                        // TODO [FHe]: check border style
//                      g.setColor(BorderStyle.LIGHT);
                        mPaint.setColor(Color.GRAY);
                        line = new RectF( // Bottom line
                                jrect.getMinX() + 2.0f, jrect.getMinY() + 1.0f, jrect.getMaxX() - 1.0f, jrect.getMinY() + 1.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                        line = new RectF( // Right line
                                jrect.getMaxX() - 1.0f, jrect.getMinY() + 1.0f, jrect.getMaxX() - 1.0f, jrect.getMaxY() - 2.0f);
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                    }
                } else if (bs.isStyleUnderline()) {
                    if (borderColor != Color.TRANSPARENT) {
                    	// TODO [FHe]: basic stroke
//                        g.setStroke(new BasicStroke(width));
                        RectF line = new RectF(
                                jrect.getMinX(), jrect.getMinY(), jrect.getMaxX(), jrect.getMinY());
                        g.drawLine(line.left, line.top, line.right, line.bottom, mPaint);
                    }
                }
            }
        } else {
            Vector borderVector = (Vector) getObject("Border");
            if (borderVector != null) {
                if (borderColor != Color.TRANSPARENT) {
                    float horizRadius = 0.0f;
                    float vertRadius = 0.0f;
                    float width = 1.0f;
                    float[] dashArray = null;
                    if (borderVector.size() >= 1)
                        horizRadius = ((Number) borderVector.get(0)).floatValue();
                    if (borderVector.size() >= 2)
                        vertRadius = ((Number) borderVector.get(1)).floatValue();
                    if (borderVector.size() >= 3)
                        width = ((Number) borderVector.get(2)).floatValue();
                    if (borderVector.size() >= 4) {
                        Object dashObj = borderVector.get(3);
                        // I guess some encoders like having fun with us,
                        //  and feed a number when a number-array is appropriate. The problem
                        //  is that for the specific PDF given, apparently no border is to be
                        //  drawn, especially not the hugely thinck one described.  So,
                        //  instead of interpretting the 4th element (Number) into a Vector,
                        //  I'm just not going to do the border if it's the Number.  I know, hack.
                        // The only theory I have is that LinkAnnotation defaults the border
                        //  color to black, when maybe it should be to null, but that could
                        //  change a _lot_ of stuff, so I won't touch it now.
                        if (dashObj instanceof Number) {
                            // Disable border drawing
                            width = 0.0f;
                        } else if (dashObj instanceof Vector) {
                            Vector dashVector = (Vector) borderVector.get(3);
                            int sz = dashVector.size();
                            dashArray = new float[sz];
                            for (int i = 0; i < sz; i++) {
                                Number num = (Number) dashVector.get(i);
                                dashArray[i] = num.floatValue();
                            }
                        }
                    }

                    if (width > 0.0f) {
                        Rectangle2Df jrect = deriveBorderDrawingRectangle(width);
                        RectF roundRect = new RectF(
                                jrect.x, jrect.y, jrect.getWidth(), jrect.getHeight());
                        // TODO [FHe]: basic stroke
//                        BasicStroke stroke = new BasicStroke(
//                                width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//                                10.0f, dashArray, 0.0f);
//                        g.setStroke(stroke);
                        g.drawRoundRect(roundRect, horizRadius, vertRadius, mPaint);
                    }
                }
            } else {
                // Draw a solid rectangle, 1 pixel wide
                if (borderColor != Color.TRANSPARENT) {
                    float width = 1.0f;
                    Rectangle2Df jrect = deriveBorderDrawingRectangle(width);
                    // TODO [FHe]: basic stroke
//                    g.setStroke(new BasicStroke(width));
                    g.drawRect(jrect.toRect(), mPaint);
                }
            }
        }
    }

    protected void renderBorderTabSelected(Canvas g) {
        float width = 1.0f;
        Rectangle2Df jrect = deriveBorderDrawingRectangle(width);
        mPaint.setColor(Color.BLACK);
        float[] dashArray = new float[]{2.0f};
        // TODO [FHe]: basic stroke
//        BasicStroke stroke = new BasicStroke(
//                width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//                10.0f, dashArray, 0.0f);
//        g.setStroke(stroke);
        g.drawRect(jrect.toRect(), mPaint);
    }

    /**
     * @return A Color for the border, or null if none is to be used
     */
    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    private PRectangle getRectangle() {
        Vector Rect = (Vector) getObject("Rect");
        PRectangle prect = new PRectangle(Rect);
        return prect;
//        Rectangle2D.Float jrect = prect.toJava2dCoordinates();
//        return jrect;
    }

    private Rectangle2Df deriveDrawingRectangle() {
        Rectangle2Df jrect = getUserSpaceRectangle();
        jrect.x = 0.0f;
        jrect.y = 0.0f;
        return jrect;
    }

    private Rectangle2Df deriveBorderDrawingRectangle(float borderWidth) {
        Rectangle2Df jrect = deriveDrawingRectangle();

        float halfBorderWidth = borderWidth / 2.0f;
        float minX = jrect.getMinX() + halfBorderWidth;
        float minY = jrect.getMinY() + halfBorderWidth;
        float maxX = jrect.getMaxX() - halfBorderWidth;
        float maxY = jrect.getMaxY() - halfBorderWidth;
        // TODO [FHe]: set frame from diagonal
//      jrect.setFrameFromDiagonal(minX, minY, maxX, maxY);
        jrect.setRect(minX, maxX, maxX-minX, maxY-minY);
        jrect.setRect(jrect.getMinX(), jrect.getMinY(), Math.abs(jrect.getWidth()), Math.abs(jrect.getHeight()));
        return jrect;
    }

    /**
     * @return Whether this annotation may be shown in any way to the user
     */
    protected boolean allowScreenOrPrintRenderingOrInteraction() {
        // Based off of the annotation flags' Invisible and Hidden values
        if (getFlagHidden())
            return false;
        if (getFlagInvisible() && isSupportedAnnotationType())
            return false;
        return true;
    }

    /**
     * The PDF spec defines rules for displaying annotation subtypes that the viewer
     * does not recognise. But, from a product point of view, we may or may not
     * wish to make a best attempt at showing an unsupported annotation subtype,
     * as that may make users think we're quality deficient, instead of
     * feature deficient.
     * Subclasses should override this, and return true, indicating that that
     * particular annotation is supported.
     *
     * @return true, if this annotation is supported; else, false.
     */
    protected boolean isSupportedAnnotationType() {
        return true;
    }

    private boolean getFlagInvisible() {
        return ((getInt("F") & 0x0001) != 0);
    }

    private boolean getFlagHidden() {
        return ((getInt("F") & 0x0002) != 0);
    }

    private boolean getFlagPrint() {
        return ((getInt("F") & 0x0004) != 0);
    }

    private boolean getFlagNoZoom() {
        return ((getInt("F") & 0x0008) != 0);
    }

    private boolean getFlagNoRotate() {
        return ((getInt("F") & 0x0010) != 0);
    }

    private boolean getFlagNoView() {
        return ((getInt("F") & 0x0020) != 0);
    }

    private boolean getFlagReadOnly() {
        return ((getInt("F") & 0x0040) != 0);
    }

    private boolean getFlagLocked() {
        return ((getInt("F") & 0x0080) != 0);
    }

    private boolean getFlagToggleNoView() {
        return ((getInt("F") & 0x0100) != 0);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ANNOTATION= {");
        java.util.Enumeration keys = entries.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = entries.get(key);
            sb.append(key.toString());
            sb.append('=');
            if (value == null)
                sb.append("null");
            else if (value instanceof StringObject)
                sb.append(((StringObject) value).getDecryptedLiteralString(library.securityManager));
            else
                sb.append(value.toString());
            sb.append(',');
        }
        sb.append('}');
        if (getPObjectReference() != null) {
            sb.append("  ");
            sb.append(getPObjectReference());
        }
        for (int i = sb.length() - 1; i >= 0; i--) {
            if (sb.charAt(i) < 32 || sb.charAt(i) >= 127)
                sb.deleteCharAt(i);
        }
        return sb.toString();
    }
}
