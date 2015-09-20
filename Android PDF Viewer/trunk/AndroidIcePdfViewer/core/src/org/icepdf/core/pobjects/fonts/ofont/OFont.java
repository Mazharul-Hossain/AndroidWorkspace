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
 * The Original Code is ICEpdf 3.0 open source software code, released
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
package org.icepdf.core.pobjects.fonts.ofont;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import java.util.Collection;
import org.icepdf.core.pobjects.fonts.CMap;
import org.icepdf.core.pobjects.fonts.Encoding;
import org.icepdf.core.pobjects.fonts.FontFile;
import org.icepdf.core.pobjects.graphics.TextState;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import org.icepdf.core.pobjects.Rectangle2Df;
import org.icepdf.core.util.FontUtil;

/**
 * OFont is an Android Font wrapper used to aid in the paint of glyphs.
 *
 * @since 3.0
 */
public class OFont implements FontFile {

    private static final Logger log =
            Logger.getLogger(OFont.class.toString());
    private static final Matrix at = new Matrix();
    private static final Collection<OFont> fontnames = new HashSet<OFont>();
    private final Paint androidFont;
    private final Typeface androidTypeface;
    private final String name;
    private final String family;
    private Rectangle2Df maxCharBounds = new Rectangle2Df(0.0f, 0.0f, 1.0f, 1.0f);
    // text layout map, very expensive to create, so we'll cache them.
    private HashMap<String, PointF> echarAdvanceCache;
    protected float[] widths;
    protected Map<Integer, Float> cidWidths;
    protected float missingWidth;
    protected int firstCh;
    protected float ascent;
    protected float descent;
    protected Encoding encoding;
    protected CMap toUnicode;
    protected char[] cMap;

    /**
     * Create a font from a given string argument containing the font parameters
     *
     * @param str   string to decode
     */
    public OFont(final String str) {
        // parse parameters from given strings
        final StringTokenizer tokenizer = new StringTokenizer(str, "-");
        final String nameToken = tokenizer.nextToken();
        String sizeToken = "12";

        if(tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();

            if(Character.isDigit(token.charAt(0))) {
                sizeToken = token;
            } else {
                if(tokenizer.hasMoreTokens()) {
                    sizeToken = tokenizer.nextToken();
                }
            }
        }

        // create font based on the given parameters
        this.androidFont = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.androidTypeface = Typeface.create(nameToken, FontUtil.guessAndroidFontStyle(str));
        this.androidFont.setTypeface(this.androidTypeface);
        this.androidFont.setTextSize(Integer.parseInt(sizeToken));
        this.name = nameToken;
        this.family = nameToken;
        this.echarAdvanceCache = new HashMap<String, PointF>(256);
        fontnames.add(this);
    }

    /**
     * Create a new font from a given typeface, font name, font family and font size
     *
     * @param androidTypeface   Android typeface to use
     * @param name  font name
     * @param family    font family
     * @param size  font size
     */
    public OFont(final Typeface androidTypeface, final String name, final String family, final float size) {
        this.androidTypeface = androidTypeface;
        this.androidFont = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.androidFont.setTypeface(androidTypeface);
        this.androidFont.setTextSize(size);
        this.name = name;
        this.family = family;
        this.echarAdvanceCache = new HashMap<String, PointF>(256);
        fontnames.add(this);
    }

    private OFont(final OFont font) {
        this.echarAdvanceCache = font.echarAdvanceCache;
        this.androidFont = new Paint(font.androidFont);
        this.androidTypeface = font.androidTypeface;
        this.name = font.name;
        this.family = font.family;
        this.encoding = font.encoding;
        this.toUnicode = font.toUnicode;
        this.missingWidth = font.missingWidth;
        this.firstCh = font.firstCh;
        this.ascent = font.ascent;
        this.descent = font.descent;
        this.widths = font.widths;
        this.cidWidths = font.cidWidths;
        this.cMap = font.cMap;
        this.maxCharBounds = font.maxCharBounds;
    }

    /**
     * Get all fonts which have been created using this class
     *
     * @return  set of fonts
     */
    public static Collection<OFont> getFonts() {
        return fontnames;
    }

    public FontFile deriveFont(Encoding encoding, CMap toUnicode) {
        OFont font = new OFont(this);
        this.echarAdvanceCache.clear();
        font.encoding = encoding;
        font.toUnicode = toUnicode;
        return font;
    }

    public FontFile deriveFont(float[] widths, int firstCh, float missingWidth,
            float ascent, float descent, char[] diff) {
        OFont font = new OFont(this);
        this.echarAdvanceCache.clear();
        font.missingWidth = this.missingWidth;
        font.firstCh = firstCh;
        font.ascent = ascent;
        font.descent = descent;
        font.widths = widths;
        font.cMap = diff;
        return font;
    }

    public FontFile deriveFont(Map<Integer, Float> widths, int firstCh, float missingWidth,
            float ascent, float descent, char[] diff) {
        OFont font = new OFont(this);
        this.echarAdvanceCache.clear();
        font.missingWidth = this.missingWidth;
        font.firstCh = firstCh;
        font.ascent = ascent;
        font.descent = descent;
        font.cidWidths = widths;
        font.cMap = diff;
        return font;
    }

    public FontFile deriveFont(Matrix at) {
        OFont font = new OFont(this);
        // TODO: currently the same font is being returned and no transformation is being done!
        // clear font metric cache if we change the font's transform
        /*if (!font.getTransform().equals(this.androidFont.getTransform())){
        this.echarAdvanceCache.clear();
        }
        font.androidFont = this.androidFont.deriveFont(at);

        font.maxCharBounds = this.maxCharBounds;*/
        return font;
    }

    public boolean canDisplayEchar(char ech) {
        return true;
    }

    public FontFile deriveFont(float pointsize) {
        OFont font = new OFont(this);
        // clear font metric cache if we change the font's size
        if (font.getSize() != pointsize) {
            this.echarAdvanceCache.clear();
        }
        // TODO: currently the same font is being returned and no transformation is being done!
        //font.androidFont = this.androidFont.deriveFont(pointsize);
        font.maxCharBounds = this.maxCharBounds;
        return font;
    }

    public PointF echarAdvance(final char ech) {

        // create a glyph vector for the char
        float advance;
        float advanceY;

        // check cache for existing layout
        String text = String.valueOf(ech);
        PointF echarAdvance = echarAdvanceCache.get(text);

        // generate metrics is needed
        if (echarAdvance == null) {

            // the glyph vector should be created using any toUnicode value if present, as this is what we
            // are drawing, the method also does a check to apply differences if toUnicode is null.
            char echGlyph = getCMapping(ech);

            // get bounds, only need to do this once.
            final FontMetrics metrics = this.androidFont.getFontMetrics();
            final Rect bounds = new Rect();
            final char[] chars = {echGlyph};

            this.androidFont.getTextBounds(chars, 0, 1, bounds);

            this.maxCharBounds.x = 0;
            this.maxCharBounds.y = metrics.ascent;
            this.maxCharBounds.width = bounds.width();
            this.maxCharBounds.height = bounds.bottom;
            
            ascent = metrics.ascent;
            descent = metrics.descent;

            // TODO: is this correct?
            advance = bounds.width();
            advanceY = 0;

            echarAdvanceCache.put(text, new PointF(advance, advanceY));
        } // returned cashed value
        else {
            advance = echarAdvance.x;
            advanceY = echarAdvance.y;
        }

        // widths uses original cid's, not the converted to unicode value.
        // TODO: we use the advance calculated above. Is this OK?
        /*if (widths != null && ech - firstCh >= 0 && ech - firstCh < widths.length) {
        advance = widths[ech - firstCh] * androidFont.getSize2D();
        } else if (cidWidths != null) {
        Float width = cidWidths.get((int) ech);
        if (width != null) {
        advance = cidWidths.get((int) ech) * androidFont.getSize2D();
        }
        } // find any widths in the font descriptor
        else if (missingWidth > 0) {
        advance = missingWidth / 1000f;
        }*/

        return new PointF(advance, advanceY);
    }

    /**
     * Gets the ToUnicode character value for the given character.
     *
     * @param currentChar character to find a corresponding CMap for.
     * @return a new Character based on the CMap tranformation.  If the character
     *         can not be found in the CMap the orginal value is returned.
     */
    private char getCMapping(char currentChar) {
        if (toUnicode != null) {
            return toUnicode.toSelector(currentChar);
        }
        return currentChar;
    }

    /**
     * Return the width of the given character
     *
     * @param character character to retreive width of
     * @return width of the given <code>character</code>
     */
    private char getCharDiff(char character) {
        if (cMap != null && character < cMap.length) {
            return cMap[character];
        } else {
            return character;
        }
    }

    private char findAlternateSymbol(char character) {
        // test for known symbol aliases
        for (int i = 0; i < org.icepdf.core.pobjects.fonts.ofont.Encoding.symbolAlaises.length; i++) {
            for (int j = 0; j < org.icepdf.core.pobjects.fonts.ofont.Encoding.symbolAlaises[i].length; j++) {
                if (org.icepdf.core.pobjects.fonts.ofont.Encoding.symbolAlaises[i][j] == character) {
                    //System.out.println("found char " + Encoding.symbolAlaises[i][0]);
                    return (char) org.icepdf.core.pobjects.fonts.ofont.Encoding.symbolAlaises[i][0];
                }
            }
        }
        return character;
    }

    public CMap getToUnicode() {
        return toUnicode;
    }

    public int getStyle() {
        return this.androidTypeface.getStyle();
    }

    public String getFamily() {
        return this.family;
    }

    public float getSize() {
        return androidFont.getFontMetrics(null);
    }

    public double getAscent() {
        return ascent;
    }

    public double getDescent() {
        return descent;
    }

    public Rectangle2Df getMaxCharBounds() {
        return maxCharBounds;
    }

    public Matrix getTransform() {
        // TODO: this is a neutral transformation and not one specific to the font!
        return at;
    }

    public int getRights() {
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public boolean isHinted() {
        return false;
    }

    public int getNumGlyphs() {
        // TODO: not correctly implemented!
        return 256;
    }

    public char getSpaceEchar() {
        return 32;
    }

    public Rectangle2Df getEstringBounds(String estr, int beginIndex, int limit) {
        return null;
    }

    public String getFormat() {
        return null;
    }

    public void drawEstring(Canvas g, String displayText, float x, float y,
            long layout, int mode, int strokecolor) {

        displayText = toUnicode(displayText);

        if (TextState.MODE_FILL == mode || TextState.MODE_FILL_STROKE == mode ||
                TextState.MODE_FILL_ADD == mode || TextState.MODE_FILL_STROKE_ADD == mode) {
            //g.fill(glyphVector.getOutline());
            g.drawText(displayText, 0, displayText.length(), x, y, this.androidFont);
        }
        if (TextState.MODE_STROKE == mode || TextState.MODE_FILL_STROKE == mode ||
                TextState.MODE_STROKE_ADD == mode || TextState.MODE_FILL_STROKE_ADD == mode) {
            //g.draw(glyphVector.getOutline());
            g.drawText(displayText, 0, displayText.length(), x, y, this.androidFont);
        }

    }

    public String toUnicode(String displayText) {
        // Check string for displayable Glyphs,  try and substitute any failed ones
        StringBuffer sb = new StringBuffer(displayText.length());
        for (int i = 0; i < displayText.length(); i++) {
            // get the first char in the buffer
            char c1 = displayText.charAt(i);

            // the toUnicode map is used for font substitution and especially for CID fonts.  If toUnicode is available
            // we use it as is, if not then we can use the charDiff mapping, which takes care of font encoding
            // differences.
            char c = toUnicode == null ? getCharDiff(c1) : c1;

            // The problem here is that some CMaping only work properly if the
            // embedded font is working properly, so that's how this logic works.

            //System.out.print((int)c + " (" + (char)c + ")");
            // check for CMap ToUnicode properties.
            c = getCMapping(c);
            //System.out.print(" -> " + (int)c + " (" + (char)c + ")");
            //System.out.println();

            // try alternat representation of character
            // TODO: we always assume we can display a character. Is there a better solution?
            /*if (!androidFont.canDisplay(c)) {
            c |= 0xF000;
            }*/
            // correct the character c if possible
//            if (!textState.font.font.canDisplay(c) && textState.font.font.canDisplay(c1)) {
//                c = c1;
//            }

            // due to different character encoding for invalid embedded fonts
            // the proper font can not always be found
            // TODO: we always assume we can display a character. Is there a better solution?
            /*if (!androidFont.canDisplay(c)) {

            // try and find a similar symbol that can be displayed.
            c = findAlternateSymbol(c);
            //                System.out.println(c + " + " + (int) c + " " +
            //                                   textState.currentfont.getName() + " " +
            //                                   textState.font.font );
            }*/

            // Debug code, show any undisplayable glyphs
            // TODO: we always assume we can display a character. Is there a better solution?
            /*if (log.isLoggable(Level.FINER)) {
            if (!androidFont.canDisplay(c)) {
            log.finer(
            ((int) c1) + " " + Character.toString(c1) + " " +
            (int) c + " " + c + " " + androidFont);
            //+ " " + textState.font.font + " " + textState.font.font.getNumGlyphs());
            }
            }*/
            // Updated with displayable glyph when possible
            sb.append(c);
        }
        return sb.toString();
    }
}
