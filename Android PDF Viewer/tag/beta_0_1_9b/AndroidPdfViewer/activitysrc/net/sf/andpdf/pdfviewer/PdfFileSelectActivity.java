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
	public static final String PREFS_ANTIALIAS = "antialias";
	public final static String DEFAULTPDFFILENAME = "/sdcard/download/example.pdf";
	public static final boolean DEFAULTSHOWIMAGES = true;
	public static final boolean DEFAULTANTIALIAS = true;
	
    public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
    public static final String EXTRA_SHOWIMAGES = "net.sf.andpdf.extra.SHOWIMAGES";
    public static final String EXTRA_ANTIALIAS = "net.sf.andpdf.extra.ANTIALIAS";
	
	private EditText mFilename;
	private EditText mOutput;
	private CheckBox mAntiAlias;
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
        mAntiAlias = (CheckBox) findViewById(R.id.cbAntiAlias);
    	mShow = (Button) findViewById(R.id.btShow);
    	mExit = (Button) findViewById(R.id.btExit);

        mShow.setOnClickListener(ShowPdfListener);
        mExit.setOnClickListener(ExitListener);

        // load persisted values
    	persist = new SimplePersistence(this, PREFS_NAME);
    	String pdffilename = persist.getString(PREFS_PDFFILENAME, DEFAULTPDFFILENAME);
    	boolean antiAlias = persist.getBoolean(PREFS_ANTIALIAS, DEFAULTANTIALIAS);
        mFilename.setText(pdffilename);
        mAntiAlias.setChecked(antiAlias);
        

    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	persistValues();
    }

	private void persistValues() {
    	String pdffilename = mFilename.getText().toString();
    	boolean antiAlias = mAntiAlias.isChecked();
    	persist.putString(PREFS_PDFFILENAME, pdffilename);
    	persist.putBoolean(PREFS_ANTIALIAS, antiAlias);
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
        	boolean antiAlias = mAntiAlias.isChecked();
	    	Intent intent = new Intent(PdfFileSelectActivity.this, PdfViewerActivity.class)
			.putExtra(EXTRA_PDFFILENAME, pdffilename)
			.putExtra(EXTRA_ANTIALIAS, antiAlias);
	    	startActivity(intent);
//	    	startActivityForResult(intent, 1);
        }
    };
    


        
}