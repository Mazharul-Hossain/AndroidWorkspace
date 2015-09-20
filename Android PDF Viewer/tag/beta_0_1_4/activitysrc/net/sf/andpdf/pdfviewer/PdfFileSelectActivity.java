package net.sf.andpdf.pdfviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * U:\Android\android-sdk-windows-1.5_r1\tools\adb push u:\Android\simple_T.pdf /data/test.pdf
 * @author ferenc.hechler
 */
public class PdfFileSelectActivity extends Activity {

	private static final String TAG = "PDFVIEWER";
	
	public final static String PREFS_NAME = "PDFViewerPrefs";
	public static final String PREFS_PDFFILENAME = "pdffilename";
	public static final String PREFS_SHOWIMAGES = "showimages";
	public final static String DEFAULTPDFFILENAME = "/sdcard/download/example.pdf";
	public static final boolean DEFAULTSHOWIMAGES = true;
	
    public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
    public static final String EXTRA_SHOWIMAGES = "net.sf.andpdf.extra.SHOWIMAGES";
	
	private EditText mFilename;
	private EditText mOutput;
	private CheckBox mShowImages;
	private Button mShow;
	private Button mExit;

	private SimplePersistence persist; 

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_file_select);
        
        mFilename = (EditText) findViewById(R.id.filename);
        mOutput = (EditText) findViewById(R.id.output);
        mShowImages = (CheckBox) findViewById(R.id.cbShowImages);
    	mShow = (Button) findViewById(R.id.btShow);
    	mExit = (Button) findViewById(R.id.btExit);

        mShow.setOnClickListener(ShowPdfListener);
        mExit.setOnClickListener(ExitListener);

        // load persisted values
    	persist = new SimplePersistence(this, PREFS_NAME);
    	String pdffilename = persist.getString(PREFS_PDFFILENAME, DEFAULTPDFFILENAME);
    	boolean showImages = persist.getBoolean(PREFS_SHOWIMAGES, DEFAULTSHOWIMAGES);
        mFilename.setText(pdffilename);
        mShowImages.setChecked(showImages);
        

    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	persistValues();
    }

	private void persistValues() {
    	String pdffilename = mFilename.getText().toString();
    	boolean showImages = mShowImages.isChecked();
    	persist.putString(PREFS_PDFFILENAME, pdffilename);
    	persist.putBoolean(PREFS_SHOWIMAGES, showImages);
    	persist.commit();
	}

    private void showText(String text) {
    	Log.i(TAG, text);
    	mOutput.setTag(text);
	}
    
    OnClickListener ExitListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };
    
    OnClickListener ShowPdfListener = new OnClickListener() {
        public void onClick(View v) {
        	persistValues();
        	String pdffilename = mFilename.getText().toString();
        	boolean showImages = mShowImages.isChecked();
	    	Intent intent = new Intent(PdfFileSelectActivity.this, PdfViewerActivity.class)
			.putExtra(EXTRA_PDFFILENAME, pdffilename)
			.putExtra(EXTRA_SHOWIMAGES, showImages);
	    	startActivity(intent);
//	    	startActivityForResult(intent, 1);
        }
    };
    


        
}