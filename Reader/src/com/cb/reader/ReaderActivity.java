package com.cb.reader;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.widget.TextView;

import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfReader;

public class ReaderActivity extends Activity implements OnInitListener {
	TextView content;
	private TextToSpeech mTts;
	final int MY_DATA_CHECK_CODE = 194;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		File extStore = Environment.getExternalStorageDirectory();
		String path = extStore.getPath() + "/FirstPdf.pdf";
		content = (TextView) this.findViewById(R.id.textView);
		try {
			this.parsePdf(path, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.checkTTS();

	}

	private void readAloud(String text) {
		mTts.setLanguage(Locale.US);
		String myText1 = "Did you sleep well?";
		String myText2 = "I hope so, because it's time to wake up.";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
		mTts.speak(myText2, TextToSpeech.QUEUE_ADD, null);
	}

	/**
	 * Checks the presense of TTS service
	 */
	private void checkTTS() {
		Log.d("TTS Check:", "Started");
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
	}

	/**
	 * Starts actual TTS if the service is available. [called automatically when
	 * the intent returns]
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				Log.d("TTS Found:", "Success");
				//
				// success, create the TTS instance
				mTts = new TextToSpeech(this, this);
			} else {
				// missing data, install it
				Log.d("TTS Not Found:", "Failure");
				//
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	/**
	 * Parses the PDF using PRTokeniser
	 * 
	 * @param src
	 *            the path to the original PDF file
	 * @param pageNumber
	 *            page number
	 * @throws IOException
	 */
	public void parsePdf(String src, int pageNumber) throws IOException {
		PdfReader reader = new PdfReader(src);
		// we can inspect the syntax of the imported page
		byte[] streamBytes = reader.getPageContent(pageNumber);
		PRTokeniser tokenizer = new PRTokeniser(streamBytes);
		while (tokenizer.nextToken()) {
			if (tokenizer.getTokenType() == PRTokeniser.TokenType.STRING) {
				content.append(tokenizer.getStringValue());
			}
		}
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		this.readAloud("");
	}

	@Override
	public void onDestroy() {
		this.mTts.shutdown();
	}
}