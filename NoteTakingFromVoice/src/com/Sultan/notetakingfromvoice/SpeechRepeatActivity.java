package com.Sultan.notetakingfromvoice;

/*
 * SpeechRepeatActivity
 * - demonstrate speech recognition and TTS repeat
 * - as outlined in Mobiletuts tutorial
 * - "Android SDK: Implementing Speech Recognition with a Speak and Repeat App"
 * 
 *  Sue Smith
 *  29.05.12
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

/**
 * SpeechRepeatActivity - processes speech input - presents user with list of
 * suggested words - when user selects a word from the list, the app speaks the
 * word back using the TTS engine
 */
public class SpeechRepeatActivity extends Activity implements OnClickListener,
		OnInitListener {

	// variable for checking Voice Recognition support on user device
	private static final int VR_REQUEST = 999;
	public String path_name_of_our_folder, path_name_of_our_file;
	Dialog myDialog, save_note_Dialog;
	EditText today_note;
	Button take_note_btn, finishbtn, save_btn;
	// variable for checking TTS engine data on user device
	private final int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech repeatTTS;

	EditText title_note_text_view;
	Button save_to_google, cancel_button;

	File temp;
	private ListView wordList;
	ArrayList<String> suggestedWords;

	// Log tag for output information

	private Account mAccount;
	private EditText voice_note_editText;

	private String wordChosen;

	/** Create the Activity, prepare to process speech and repeat */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// make_dir();

		// gain reference to speak button
		Button speechBtn = (Button) findViewById(R.id.speech_btn);
		save_btn = (Button) findViewById(R.id.save_btn_speech);
		save_btn.setOnClickListener(this);

		voice_note_editText = (EditText) findViewById(R.id.voice_note_editText);
		voice_note_editText.setText("");
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// gain reference to word list
		wordList = (ListView) findViewById(R.id.word_list);

		// find out whether speech recognition is supported
		PackageManager packManager = getPackageManager();

		List<ResolveInfo> intActivities = packManager.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

		if (intActivities.size() != 0) {

			// speech recognition is supported - detect user button clicks
			speechBtn.setOnClickListener(this);

			// prepare the TTS to repeat chosen words
			Intent checkTTSIntent = new Intent();
			// check TTS data
			checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);

			/*
			 * start the checking Intent - will retrieve result in
			 * onActivityResult
			 */
			startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

		} else {

			// speech recognition not supported, disable button and output
			// message
			speechBtn.setEnabled(false);
			Toast.makeText(this, "Oops - Speech recognition not supported!",
					Toast.LENGTH_LONG).show();
		}

		// detect user clicks of suggested words
		wordList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TextView wordView = (TextView) view;

				// retrieve the chosen word
				wordChosen = (String) wordView.getText();
				String wordChosen1 = voice_note_editText.getText().toString();
				wordChosen = wordChosen1 + wordChosen;
				voice_note_editText.setText(wordChosen);

				SHOW_DIALOG();

				@SuppressWarnings("unchecked")
				ArrayAdapter<String> adapter = (ArrayAdapter<String>) wordList
						.getAdapter();

				if (adapter != null) {
					adapter.clear();
					adapter.notifyDataSetChanged();
				}
				wordList.setAdapter(adapter);
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();

		mAccount = new GoogleAccountManager(this).getAccountByName(PreferenceManager
				.getDefaultSharedPreferences(this).getString(
						"selected_account_preference", ""));
		if (mAccount == null) {

			// Show the Preferences screen.
			startActivity(new Intent(getApplicationContext(), Preferences.class));
		}

	}

	public void SHOW_DIALOG() {

		myDialog = new Dialog(SpeechRepeatActivity.this);
		myDialog.setContentView(R.layout.my_custom_dialog);
		myDialog.setTitle("Todays Note ");

		today_note = (EditText) myDialog.findViewById(R.id.today_note_edit_text);
		today_note.setText(wordChosen);

		take_note_btn = (Button) myDialog.findViewById(R.id.import_to_reader);
		take_note_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				myDialog.cancel();
				take_note();
			}
		});

		finishbtn = (Button) myDialog.findViewById(R.id.cancel_btn);
		finishbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myDialog.cancel();
			}

		});
		myDialog.show();

	}

	/**
	 * Called when the user presses the speak button
	 */
	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.speech_btn) {
			// listen for results
			listenToSpeech();
		} else if (v.getId() == R.id.save_btn_speech) {

			wordChosen = voice_note_editText.getText().toString();
			take_note();
		}

	}

	public void take_note() {

		// Gets the intent that started this Activity.
		Intent intent = getIntent();
		intent.setData(Uri.parse("content://com.google.provider.NotePad/" + mAccount.name
				+ "/notes/"));

		// Gets a handle to the clipboard service.
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		// Creates a new text clip to put on the clipboard
		ClipData clip = ClipData.newPlainText("simple text", wordChosen);
		// Set the clipboard's primary clip.
		clipboard.setPrimaryClip(clip);

		startActivity(new Intent(Intent.ACTION_PASTE, getIntent().getData()));
		finish();
	}

	/**
	 * Instruct the app to listen for user speech input
	 */
	private void listenToSpeech() {

		// start the speech recognition intent passing required data
		Intent listenIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName()); // indicate package

		// message to display while listening
		listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word!");
		listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // set speech model

		// specify number of results to retrieve
		listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
		startActivityForResult(listenIntent, VR_REQUEST); // start listening
	}

	/**
	 * onActivityResults handles: - retrieving results of speech recognition
	 * listening - retrieving result of TTS data check
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// check speech recognition result
		if (requestCode == VR_REQUEST && resultCode == RESULT_OK) {
			// store the returned word list as an ArrayList
			suggestedWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			// set the retrieved list to display in the ListView using an
			// ArrayAdapter
			wordList.setAdapter(new ArrayAdapter<String>(this, R.layout.word,
					suggestedWords));
		}

		if (requestCode == MY_DATA_CHECK_CODE) // returned from TTS data check
		{

			// we have the data - create a TTS instance
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
				repeatTTS = new TextToSpeech(this, this);

			else // data not installed, prompt the user to install it
			{
				// intent will take user to TTS download page in Google Play
				Intent installTTSIntent = new Intent();
				installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}

		super.onActivityResult(requestCode, resultCode, data); // call
																// superclass
																// method
	}

	/**
	 * onInit fires when TTS initializes
	 */
	@Override
	public void onInit(int initStatus) {

		if (initStatus == TextToSpeech.SUCCESS) // if successful, set locale
			repeatTTS.setLanguage(Locale.UK); // ***choose your own locale
												// here***

	}

	@Override
	public void onStop() {
		super.onStop();
	}
}
