package net.sf.andpdf.icepdfviewer;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import net.sf.andpdf.utils.Util;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


/**
 * U:\Android\android-sdk-windows-1.5_r1\tools\adb push u:\Android\simple_T.pdf /data/test.pdf
 * @author ferenc.hechler
 */
public class IcePdfViewerActivity extends Activity {

	private static final String TAG = "PDFVIEWER";
	
	private final static int MEN_NEXT_PAGE = 1;
	private final static int MEN_PREV_PAGE = 2;
	private final static int MEN_ZOOM_IN   = 3;
	private final static int MEN_ZOOM_OUT  = 4;
	private final static int MEN_BACK      = 5;
	
	private GraphView mGraphView;
	private String pdffilename;
	private Document mDocument; 
	private int mPage;
	private float mZoom;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGraphView = new GraphView(this);
        
        pdffilename = getIntent().getStringExtra(IcePdfFileSelectActivity.EXTRA_PDFFILENAME);
        if (pdffilename == null)
        	pdffilename = "no file selected";
        boolean useFillStyle = getIntent().getBooleanExtra(IcePdfFileSelectActivity.EXTRA_USEFILLSTYLE, IcePdfFileSelectActivity.DEFAULTUSEFILLSTYLE);
//        PDFRenderer.sUseFillStyle = useFillStyle;
        
        parsePDF(pdffilename);
        mPage = 1;
        mZoom = 1;
        if (mDocument != null) {
        	showPage(mPage, mZoom);
        }
        setContentView(mGraphView);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
        // clean up resources
    	if (mDocument != null)
    		mDocument.dispose();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MEN_NEXT_PAGE, Menu.NONE, "Next Page");
        menu.add(Menu.NONE, MEN_PREV_PAGE, Menu.NONE, "Previous Page");
        menu.add(Menu.NONE, MEN_ZOOM_IN, Menu.NONE, "Zoom In");
        menu.add(Menu.NONE, MEN_ZOOM_OUT, Menu.NONE, "Zoom Out");
        menu.add(Menu.NONE, MEN_BACK, Menu.NONE, "Back");
        return true;
    }
    
    /**
     * Called when a menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
    	case MEN_NEXT_PAGE: {
    		nextPage();
    		break;
    	}
    	case MEN_PREV_PAGE: {
    		prevPage();
    		break;
    	}
    	case MEN_ZOOM_IN: {
    		zoomIn();
    		break;
    	}
    	case MEN_ZOOM_OUT: {
    		zoomOut();
    		break;
    	}
    	case MEN_BACK: {
            finish();
            break;
    	}
    	}
    	return true;
    }
    
    
    private void zoomIn() {
    	if (mDocument != null) {
    		if (mZoom < 4) {
    			mZoom *= 1.5;
    			if (mZoom > 4)
    				mZoom = 4;
    			showPage(mPage, mZoom);
    		}
    	}
	}

    private void zoomOut() {
    	if (mDocument != null) {
    		if (mZoom > 0.25) {
    			mZoom /= 1.5;
    			if (mZoom < 0.25)
    				mZoom = 0.25f;
    			showPage(mPage, mZoom);
    		}
    	}
	}

	private void nextPage() {
    	if (mDocument != null) {
    		if (mPage < mDocument.getNumberOfPages()) {
    			mPage += 1;
    			showPage(mPage, mZoom);
    		}
    	}
	}

    private void prevPage() {
    	if (mDocument != null) {
    		if (mPage > 1) {
    			mPage -= 1;
    			showPage(mPage, mZoom);
    		}
    	}
	}


	private class GraphView extends View {
    	private String mText;
		private float mLastX;
    	private float mLastY;
    	private float mOffX;
    	private float mOffY;
    	private long fileMillis;
    	private long pageMillis;
    	Canvas mCan;
    	Bitmap mBi;
        
        public GraphView(Context context) {
            super(context);
            mOffX = 100;
            mOffY = 100;
            
            setPageBitmap();
        }

        private void showText(String text) {
        	Log.i(TAG, "ST='"+text+"'");
        	mText = text;
        	invalidate();
		}
        
        private void setPageBitmap() {
			mBi = Bitmap.createBitmap(100, 100, Config.ARGB_8888);
            mCan = new Canvas(mBi);
            mCan.drawColor(Color.RED);
            
			Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            mCan.drawCircle(50, 50, 50, paint);
            
            paint.setStrokeWidth(0);
            paint.setColor(Color.BLACK);
            mCan.drawText("Bitmap", 10, 50, paint);

		}
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	super.onTouchEvent(event);
        	if (event.getAction() == MotionEvent.ACTION_DOWN) {
        		mLastX = event.getRawX();
        		mLastY = event.getRawY();
        		Log.i(TAG, "DOWN["+mLastX+","+mLastY+"]");
        	}
        	else if (event.getAction() == MotionEvent.ACTION_MOVE) {

        		float x = event.getRawX();
        		float y = event.getRawY();
        		float dx = x-mLastX;
        		float dy = y-mLastY;
        		mLastX = x;
        		mLastY = y;
        		mOffX += dx;
        		mOffY += dy;
    			mGraphView.invalidate();
        	}
        	return true;
        }
        
		@Override protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();

			canvas.drawColor(Color.LTGRAY);

            paint.setStrokeWidth(0);
            paint.setColor(Color.BLACK);
            canvas.drawText("PdfViewer: "+mText, 10, 20, paint);
            
            float fileTime = fileMillis*0.001f;
            float pageTime = pageMillis*0.001f;
            canvas.drawText("seconds: parse="+fileTime+" show="+pageTime, 10, 40, paint);

            // draw the normal strings
            paint.setColor(Color.BLUE);
            canvas.drawCircle(mOffX, mOffY, 5, paint);
            canvas.drawCircle(mOffX+mBi.getWidth(), mOffY, 5, paint);
            canvas.drawCircle(mOffX+mBi.getWidth(), mOffY+mBi.getHeight(), 5, paint);
            canvas.drawCircle(mOffX, mOffY+mBi.getHeight(), 5, paint);
 
//            canvas.drawBitmap(mBi, mOffX, mOffY, paint);
            canvas.drawBitmap(mBi, Util.createMatrix(1,0,0,-1,mOffX,mOffY+mBi.getHeight()), paint);
            
        }

   }
    
    private void showPage(int page, float scale) {
        long startTime = System.currentTimeMillis();
    	try {
            float rotation = 0f;

            Bitmap image = mDocument.getPageImage(page-1,
                                              GraphicsRenderingHints.SCREEN,
                                              Page.BOUNDARY_CROPBOX, rotation, scale);
            int maxNum = mDocument.getNumberOfPages();
	        String pageInfo= new File(pdffilename).getName() + " - " + page +"/"+maxNum+ ": " + image.getWidth() + "x" + image.getHeight();
	        mGraphView.showText(pageInfo);
	        Log.i(TAG, pageInfo);
	        mGraphView.mBi = image;
		} catch (Throwable e) {
			e.printStackTrace();
			mGraphView.showText("Exception: "+e.getMessage());
		}
        long stopTime = System.currentTimeMillis();
        mGraphView.pageMillis = stopTime-startTime;
    }
    
    private void parsePDF(String filename) {
        long startTime = System.currentTimeMillis();
    	try {
        	File f = new File(filename);
        	long len = f.length();
        	if (len == 0) {
        		mGraphView.showText("file '" + filename + "' not found");
        	}
        	else {
        		mGraphView.showText("file '" + filename + "' has " + len + " bytes");

		    	openFile(f);

        	}	    	
		} catch (Throwable e) {
			e.printStackTrace();
			mGraphView.showText("Exception: "+e.getMessage());
		}
        long stopTime = System.currentTimeMillis();
        mGraphView.fileMillis = stopTime-startTime;
	}

    
    /**
     * <p>Open a specific pdf file.  Creates a DocumentInfo from the file,
     * and opens that.</p>
     *
     * <p><b>Note:</b> Mapping the file locks the file until the PDFFile
     * is closed.</p>
     *
     * @param file the file to open
     * @throws IOException
     */
    public void openFile(File file) throws IOException {
        // open the url
        String msg;
        try {
        	mDocument = new Document();
            mDocument.setFile(file.getCanonicalPath());
            msg = "OK: num pages="+mDocument.getNumberOfPages();
        } catch (PDFException ex) {
            msg = "Error parsing PDF document " + ex;
        	Log.e(TAG, msg, ex);
            mDocument = null;
        } catch (PDFSecurityException ex) {
        	msg = "Error encryption not supported " + ex;
        	Log.e(TAG, msg, ex);
            mDocument = null;
        } catch (FileNotFoundException ex) {
        	msg = "Error file not found " + ex;
        	Log.e(TAG, msg, ex);
            mDocument = null;
        } catch (IOException ex) {
        	msg = "Error handling PDF document " + ex;
        	Log.e(TAG, msg, ex);
            mDocument = null;
        }
        catch (Exception ex) {
        	msg = "unknown Error: " + ex;
        	Log.e(TAG, msg, ex);
            mDocument = null;
		}
        mGraphView.showText(file.getName()+": "+msg);
    }
    
}