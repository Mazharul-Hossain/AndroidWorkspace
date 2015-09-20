package net.sf.andpdf.pdfviewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFImage;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPaint;


/**
 * U:\Android\android-sdk-windows-1.5_r1\tools\adb push u:\Android\simple_T.pdf /data/test.pdf
 * @author ferenc.hechler
 */
public class PdfViewerActivity extends Activity {

	private static final String TAG = "PDFVIEWER";
	
	private final static int MEN_NEXT_PAGE = 1;
	private final static int MEN_PREV_PAGE = 2;
	private final static int MEN_ZOOM_IN   = 3;
	private final static int MEN_ZOOM_OUT  = 4;
	private final static int MEN_BACK      = 5;
	
	private GraphView mGraphView;
	private String pdffilename;
	private PDFFile mPdfFile;
	private int mPage;
	private float mZoom;
    private File mTmpFile;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGraphView = new GraphView(this);
        
        Intent intent = getIntent();
        Log.i(TAG, ""+intent);
        if (intent != null) {
        	if ("android.intent.action.VIEW".equals(intent.getAction())) {
				pdffilename = storeUriContentToFile(intent.getData());
        	}
        	else {
                pdffilename = getIntent().getStringExtra(PdfFileSelectActivity.EXTRA_PDFFILENAME);
        	}
        }
        
        if (pdffilename == null)
        	pdffilename = "no file selected";
        boolean showImages = getIntent().getBooleanExtra(PdfFileSelectActivity.EXTRA_SHOWIMAGES, PdfFileSelectActivity.DEFAULTSHOWIMAGES);
        PDFImage.sShowImages = showImages;
        boolean antiAlias = getIntent().getBooleanExtra(PdfFileSelectActivity.EXTRA_ANTIALIAS, PdfFileSelectActivity.DEFAULTANTIALIAS);
        PDFPaint.s_doAntiAlias = antiAlias;
        
        parsePDF(pdffilename);
        mPage = 1;
        mZoom = 1;
        if (mPdfFile != null) {
        	showPage(mPage, mZoom);
        }
        setContentView(mGraphView);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MEN_PREV_PAGE, Menu.NONE, "Previous Page");
        menu.add(Menu.NONE, MEN_NEXT_PAGE, Menu.NONE, "Next Page");
        menu.add(Menu.NONE, MEN_ZOOM_OUT, Menu.NONE, "Zoom Out");
        menu.add(Menu.NONE, MEN_ZOOM_IN, Menu.NONE, "Zoom In");
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
    	if (mPdfFile != null) {
    		if (mZoom < 4) {
    			mZoom *= 1.5;
    			if (mZoom > 4)
    				mZoom = 4;
    			showPage(mPage, mZoom);
    		}
    	}
	}

    private void zoomOut() {
    	if (mPdfFile != null) {
    		if (mZoom > 0.25) {
    			mZoom /= 1.5;
    			if (mZoom < 0.25)
    				mZoom = 0.25f;
    			showPage(mPage, mZoom);
    		}
    	}
	}

	private void nextPage() {
    	if (mPdfFile != null) {
    		if (mPage < mPdfFile.getNumPages()) {
    			mPage += 1;
    			showPage(mPage, mZoom);
    		}
    	}
	}

    private void prevPage() {
    	if (mPdfFile != null) {
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
 
            canvas.drawBitmap(mBi, mOffX, mOffY, paint);
//            canvas.drawBitmap(mBi, Utils.createMatrix(1,0,0,-1,mOffX,mOffY+mBi.getHeight()), paint);
            
        }

   }
    
    private void showPage(int page, float zoom) {
        long startTime = System.currentTimeMillis();
    	try {
	        PDFPage pdfPage = mPdfFile.getPage(page, true);
	        int num = pdfPage.getPageNumber();
	        int maxNum = mPdfFile.getNumPages();
	        float wi = pdfPage.getWidth();
	        float hei = pdfPage.getHeight();
	        String pageInfo= new File(pdffilename).getName() + " - " + num +"/"+maxNum+ ": " + wi + "x" + hei;
	        mGraphView.showText(pageInfo);
	        Log.i(TAG, pageInfo);
	        RectF clip = null;
	        mGraphView.mBi = pdfPage.getImage((int)(wi*zoom), (int)(hei*zoom), clip, true, true);
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
        // first open the file for random access
        RandomAccessFile raf = new RandomAccessFile(file, "r");

        // extract a file channel
        FileChannel channel = raf.getChannel();

        // now memory-map a byte-buffer
        ByteBuffer buf =
                channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        // create a PDFFile from the data
        mPdfFile = new PDFFile(buf);
        mGraphView.showText("Anzahl Seiten:" + mPdfFile.getNumPages());
    }
    
     
    private String storeUriContentToFile(Uri uri) {
    	String result = null;
    	try {
	    	if (mTmpFile == null) {
				File root = Environment.getExternalStorageDirectory();
				if (root == null)
					throw new Exception("external storage dir not found");
				mTmpFile = new File(root,"AndroidPdfViewer/AndroidPdfViewer_temp.pdf");
				mTmpFile.getParentFile().mkdirs();
	    		mTmpFile.delete();
	    	}
	    	else {
	    		mTmpFile.delete();
	    	}
	    	InputStream is = getContentResolver().openInputStream(uri);
	    	OutputStream os = new FileOutputStream(mTmpFile);
	    	byte[] buf = new byte[1024];
	    	int cnt = is.read(buf);
	    	while (cnt > 0) {
	    		os.write(buf, 0, cnt);
		    	cnt = is.read(buf);
	    	}
	    	os.close();
	    	is.close();
	    	result = mTmpFile.getCanonicalPath();
	    	mTmpFile.deleteOnExit();
    	}
    	catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
		}
		return result;
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if (mTmpFile != null) {
    		mTmpFile.delete();
    		mTmpFile = null;
    	}
    }
    
}