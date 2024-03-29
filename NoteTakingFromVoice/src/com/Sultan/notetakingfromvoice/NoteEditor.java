/*
 * Copyright (C) 2007 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.Sultan.notetakingfromvoice;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

/**
 * This Activity handles "editing" a note, where editing is responding to
 * {@link Intent#ACTION_VIEW} (request to view data), edit a note
 * {@link Intent#ACTION_EDIT}, create a note {@link Intent#ACTION_INSERT}, or
 * create a new note from the current contents of the clipboard
 * {@link Intent#ACTION_PASTE}.
 * 
 * NOTE: Notice that the provider operations in this Activity are taking place
 * on the UI thread. This is not a good practice. It is only done here to make
 * the code more readable. A real application should use the
 * {@link android.content.AsyncQueryHandler} or {@link android.os.AsyncTask}
 * object to perform operations asynchronously on a separate thread.
 */

public class NoteEditor extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	/** For logging and debugging purposes */
	private static final String TAG = "NoteEditor";

	/** Projection that returns the note ID and the note contents. */
	private static final String[] PROJECTION = new String[] {
			NotePad.Notes.COLUMN_NAME_TITLE, NotePad.Notes.COLUMN_NAME_NOTE,
			NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE };

	/** Label for the saved state of the activity. */
	private static final String ORIGINAL_CONTENT = "origContent";

	/**
	 * This Activity can be started by more than one action. Each action is
	 * represented as a "state" constant
	 */
	private static final int STATE_EDIT = 0;
	private static final int STATE_INSERT = 1;

	private static final int CHOOSE_ACCOUNT = 0;

	// Global mutable variables
	private int mState;
	private Uri mUri;
	private Cursor mCursor;
	private EditText mText;
	private String mOriginalContent;

	private String mFileId;
	private String mAccountName = null;
	private ProgressDialog mProgressBar = null;
	private boolean mInserted = false;

	private Dialog save_note_Dialog;

	private EditText title_note_text_view;

	private String title_note;

	/**
	 * Defines a custom EditText View that draws lines between each line of text
	 * that is displayed.
	 */
	public static class LinedEditText extends EditText {
		private final Rect mRect;
		private final Paint mPaint;

		// This constructor is used by LayoutInflater
		public LinedEditText(Context context, AttributeSet attrs) {
			super(context, attrs);

			// Creates a Rect and a Paint object, and sets the style and color
			// of the Paint object.
			mRect = new Rect();
			mPaint = new Paint();
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setColor(0x800000FF);
		}

		/**
		 * This is called to draw the LinedEditText object
		 * 
		 * @param canvas
		 *            The canvas on which the background is drawn.
		 */
		@Override
		protected void onDraw(Canvas canvas) {

			// Gets the number of lines of text in the View.
			int count = getLineCount();

			// Gets the global Rect and Paint objects
			Rect r = mRect;
			Paint paint = mPaint;

			// Draws one line in the rectangle for every line of text in the
			// EditText
			for (int i = 0; i < count; i++) {

				// Gets the baseline coordinates for the current line of text
				int baseline = getLineBounds(i, r);

				/*
				 * Draws a line in the background from the left of the rectangle
				 * to the right, at a vertical position one dip below the
				 * baseline, using the "paint" object for details.
				 */
				canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
			}

			// Finishes up by calling the parent method
			super.onDraw(canvas);
		}
	}

	/* public static class LinedEditText Class ends */

	/**
	 * This method is called by Android when the Activity is first started. From
	 * the incoming Intent, it determines what kind of editing is desired, and
	 * then does it.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * Creates an Intent to use when the Activity object's result is sent
		 * back to the caller.
		 */
		final Intent intent = getIntent();

		/*
		 * Sets up for the edit, based on the action specified for the incoming
		 * Intent.
		 */

		// Gets the action that triggered the intent filter for this Activity
		final String action = intent.getAction();

		// For an edit action:
		if (Intent.ACTION_EDIT.equals(action)) {

			// Sets the Activity state to EDIT, and gets the URI for the data to
			// be edited.
			mState = STATE_EDIT;
			mUri = intent.getData();

			mAccountName = mUri.getPathSegments().get(
					NotePad.Notes.NOTE_ACCOUNT_PATH_POSITION);
			requestSync();
			// For an insert or paste action:
		} else if (Intent.ACTION_INSERT.equals(action)
				|| Intent.ACTION_PASTE.equals(action)) {
			// Sets the Activity state to INSERT, gets the general note URI, and
			// inserts an empty record in the provider
			mState = STATE_INSERT;
			mUri = getContentResolver().insert(intent.getData(), null);

			/*
			 * If the attempt to insert the new note fails, shuts down this
			 * Activity. The originating Activity receives back RESULT_CANCELED
			 * if it requested a result. Logs that the insert failed.
			 */
			if (mUri == null) {

				// Writes the log identifier, a message, and the URI that
				// failed.
				Log.e(TAG, "Failed to insert new note into " + getIntent().getData());

				// Closes the activity.
				finish();
				return;
			}

			// Since the new entry was created, this sets the result to be
			// returned
			// set the result to be returned.
			setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));
			getLoaderManager().initLoader(0, null, NoteEditor.this);
			// If the action was other than EDIT or INSERT:
		} else if ("com.google.android.apps.drive.DRIVE_OPEN".equals(action)) {
			mFileId = intent.getStringExtra("resourceId");
			if (mFileId == null || mFileId.length() == 0) {
				Log.e(TAG, "Failed to retrieve the Drive file ID.");
				finish();
				return;
			} else {
				Intent accountPickerIntent = AccountPicker.newChooseAccountIntent(null,
						null, Preferences.PreferencesFragment.ACCOUNT_TYPE, false, null,
						null, null, null);
				startActivityForResult(accountPickerIntent, CHOOSE_ACCOUNT);
			}

		} else {

			/*
			 * Logs an error that the action was not understood, finishes the
			 * Activity, and returns RESULT_CANCELED to an originating Activity.
			 */
			Log.e(TAG,
					"Unknown action, exiting: " + action + " (uri: " + intent.getData()
							+ ")");
			finish();
			return;
		}

		/*
		 * For a paste, initializes the data from clipboard. (Must be done after
		 * mCursor is initialized.)
		 */
		if (Intent.ACTION_PASTE.equals(action)) {
			// Does the paste
			performPaste();
			// Switches the state to EDIT so the title can be modified.
			mState = STATE_EDIT;
		}

		// Sets the layout for this Activity. See res/layout/note_editor.xml
		setContentView(R.layout.note_editor);

		// Gets a handle to the EditText in the the layout.
		mText = (EditText) findViewById(R.id.note);

		/*
		 * If this Activity had stopped previously, its state was written the
		 * ORIGINAL_CONTENT location in the saved Instance state. This gets the
		 * state.
		 */
		if (savedInstanceState != null) {
			mOriginalContent = savedInstanceState.getString(ORIGINAL_CONTENT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CHOOSE_ACCOUNT:
			if (resultCode == RESULT_OK) {
				mAccountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			}
			if (mAccountName != null && mAccountName.length() > 0) {
				// Try retrieving existing file.
				mUri = Uri.parse("content://com.google.provider.NotePad/" + mAccountName
						+ "/files/" + mFileId);
				mState = STATE_EDIT;
			} else {
				setResult(RESULT_CANCELED);
				finish();
			}
			break;
		}

	}

	/**
	 * This method is called when the Activity is about to come to the
	 * foreground. This happens when the Activity comes to the top of the task
	 * stack, OR when it is first starting.
	 * 
	 * Moves to the first note in the list, sets an appropriate title for the
	 * action chosen by the user, puts the note contents into the TextView, and
	 * saves the original text as a backup.
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (mState == STATE_EDIT) {
			/*
			 * mCursor is initialized, since onCreate() always precedes onResume
			 * for any running process. This tests that it's not null, since it
			 * should always contain data.
			 */
			if (mCursor != null) {
				Log.d(TAG, "Destroying loader");
				getLoaderManager().destroyLoader(0);
			}
			if (mUri != null) {
				Log.d(TAG, "Initializing loader");
				getLoaderManager().initLoader(0, null, NoteEditor.this);
			}
			if (mProgressBar == null) {
				mProgressBar = ProgressDialog.show(this, null, "Loading note...", true);
			}
		}
	}

	/**
	 * This method is called when an Activity loses focus during its normal
	 * operation, and is then later on killed. The Activity has a chance to save
	 * its state so that the system can restore it.
	 * 
	 * Notice that this method isn't a normal part of the Activity lifecycle. It
	 * won't be called if the user simply navigates away from the Activity.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Save away the original text, so we still have it if the activity
		// needs to be killed while paused.
		outState.putString(ORIGINAL_CONTENT, mOriginalContent);
	}

	/**
	 * This method is called when the Activity loses focus.
	 * 
	 * For Activity objects that edit information, onPause() may be the one
	 * place where changes are saved. The Android application model is
	 * predicated on the idea that "save" and "exit" aren't required actions.
	 * When users navigate away from an Activity, they shouldn't have to go back
	 * to it to complete their work. The act of going away should save
	 * everything and leave the Activity in a state where Android can destroy it
	 * if necessary.
	 * 
	 * If the user hasn't done anything, then this deletes or clears out the
	 * note, otherwise it writes the user's work to the provider.
	 */
	@Override
	protected void onPause() {
		super.onPause();

		/*
		 * Tests to see that the query operation didn't fail (see onCreate()).
		 * The Cursor object will exist, even if no records were returned,
		 * unless the query failed because of some exception or error.
		 */
		if (mCursor != null) {

			// Get the current note text.
			String text = mText.getText().toString();
			int length = text.length();

			/*
			 * If the Activity is in the midst of finishing and there is no text
			 * in the current note, returns a result of CANCELED to the caller,
			 * and deletes the note. This is done even if the note was being
			 * edited, the assumption being that the user wanted to "clear out"
			 * (delete) the note.
			 */
			if (isFinishing() && (length == 0)) {
				setResult(RESULT_CANCELED);
				deleteNote();

				/*
				 * Writes the edits to the provider. The note has been edited if
				 * an existing note was retrieved into the editor *or* if a new
				 * note was inserted. In the latter case, onCreate() inserted a
				 * new empty note into the provider, and it is this new note
				 * that is being edited.
				 */
			} else if (mState == STATE_EDIT) {
				// Creates a map to contain the new values for the columns
				updateNote(text, null);
			} else if (mState == STATE_INSERT) {
				updateNote(text, text);
				mState = STATE_EDIT;
			}
		}
	}

	/**
	 * This method is called when the user clicks the device's Menu button the
	 * first time for this Activity. Android passes in a Menu object that is
	 * populated with items.
	 * 
	 * Builds the menus for editing and inserting, and adds in alternative
	 * actions that registered themselves to handle the MIME types for this
	 * application.
	 * 
	 * @param menu
	 *            A Menu object to which items should be added.
	 * @return True to display the menu.
	 **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu from XML resource
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.editor_options_menu, menu);

		// Only add extra menu items for a saved note
		if (mState == STATE_EDIT) {
			// Append to the
			// menu items for any other activities that can do stuff with it
			// as well. This does a query on the system for any activities that
			// implement the ALTERNATIVE_ACTION for our data, adding a menu item
			// for each one that is found.
			Intent intent = new Intent(null, mUri);
			intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
			menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, new ComponentName(
					this, NoteEditor.class), null, intent, 0, null);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mCursor != null && mCursor.getCount() > 0) {
			// Check if note has changed and enable/disable the revert option
			int colNoteIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
			String savedNote = mCursor.getString(colNoteIndex);
			String currentNote = mText.getText().toString();
			if (savedNote != null && savedNote.equals(currentNote)) {
				menu.findItem(R.id.menu_revert).setVisible(false);
			} else {
				menu.findItem(R.id.menu_revert).setVisible(true);
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * This method is called when a menu item is selected. Android passes in the
	 * selected item. The switch statement in this method calls the appropriate
	 * method to perform the action the user chose.
	 * 
	 * @param item
	 *            The selected MenuItem
	 * @return True to indicate that the item was processed, and no further work
	 *         is necessary. False to proceed to further processing as indicated
	 *         in the MenuItem object.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle all of the possible menu actions.
		switch (item.getItemId()) {
		case R.id.menu_save:
			String text = mText.getText().toString();
			updateNote(text, null);
			finish();
			break;
		case R.id.menu_delete:
			deleteNote();
			finish();
			break;
		case R.id.menu_revert:
			cancelNote();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A helper method that replaces the note's data with the contents of the
	 * clipboard.
	 */
	private final void performPaste() {

		// Gets a handle to the Clipboard Manager
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

		// Gets a content resolver instance
		ContentResolver cr = getContentResolver();

		// Gets the clipboard data from the clipboard
		ClipData clip = clipboard.getPrimaryClip();
		if (clip != null) {

			String text = null;
			String title = null;

			// Gets the first item from the clipboard data
			ClipData.Item item = clip.getItemAt(0);

			// Tries to get the item's contents as a URI pointing to a note
			Uri uri = item.getUri();

			// Tests to see that the item actually is an URI, and that the URI
			// is a content URI pointing to a provider whose MIME type is the
			// same
			// as the MIME type supported by the Note pad provider.
			if (uri != null && NotePad.Notes.CONTENT_ITEM_TYPE.equals(cr.getType(uri))) {

				// The clipboard holds a reference to data with a note MIME
				// type. This
				// copies it.
				Cursor orig = cr.query(uri, // URI for the content provider
						PROJECTION, // Get the columns referred to in the
									// projection
						null, // No selection variables
						null, // No selection variables, so no criteria are
								// needed
						null // Use the default sort order
						);

				/*
				 * If the Cursor is not null, and it contains at least one
				 * record (moveToFirst() returns true), then this gets the note
				 * data from it.
				 */
				if (orig != null) {
					if (orig.moveToFirst()) {
						int colNoteIndex = mCursor
								.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
						int colTitleIndex = mCursor
								.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE);
						text = orig.getString(colNoteIndex);
						title = orig.getString(colTitleIndex);
					}

					// Closes the cursor.
					orig.close();
				}
			}

			// If the contents of the clipboard wasn't a reference to a note,
			// then
			// this converts whatever it is to text.
			if (text == null) {
				text = item.coerceToText(this).toString();
			}

			// Updates the current note with the retrieved title and text.
			updateNote(text, title);
		}
	}

	/**
	 * Replaces the current note contents with the text and title provided as
	 * arguments.
	 * 
	 * @param text
	 *            The new note contents to use.
	 * @param initialTitle
	 *            The new note title to use
	 */
	private final void updateNote(final String text, final String initialTitle) {

		ContentValues values = new ContentValues();
		values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
				System.currentTimeMillis());
		String title = initialTitle;

		/*
		 * If the action is to insert a new note, this creates an initial title
		 * for it.
		 */
		if (mState == STATE_INSERT) {

			// If no title was provided as an argument, create one from the note
			// text.
			if (title == null) {

				// Get the note's length
				int length = text.length();

				/*
				 * Sets the title by getting a substring of the text that is 31
				 * characters long or the number of characters in the note plus
				 * one, whichever is smaller.
				 */
				title = text.substring(0, Math.min(30, length));

				/*
				 * If the resulting length is more than 30 characters, chops off
				 * any trailing spaces
				 */
				if (length > 30) {
					int lastSpace = title.lastIndexOf(' ');
					if (lastSpace > 0) {
						title = title.substring(0, lastSpace);
					}
				}
				title_note = title;

				showDialog2();

				title = title_note;
				Log.d(TAG, "title_note : " + title_note + " & title : " + title_note);
			}
			// In the values map, sets the value of the title
			values.put(NotePad.Notes.COLUMN_NAME_TITLE, title);

		} else if (title != null) {
			title_note = title;

			showDialog2();

			title = title_note;
			Log.d(TAG, "title_note : " + title_note + " & title : " + title_note);
			// In the values map, sets the value of the title
			values.put(NotePad.Notes.COLUMN_NAME_TITLE, title);
		}

		// This puts the desired notes text into the map.
		values.put(NotePad.Notes.COLUMN_NAME_NOTE, text);

		/*
		 * Updates the provider with the new values in the map. The ListView is
		 * updated automatically. The provider sets this up by setting the
		 * notification URI for query Cursor objects to the incoming URI. The
		 * content resolver is thus automatically notified when the Cursor for
		 * the URI changes, and the UI is updated. Note: This is being done on
		 * the UI thread. It will block the thread until the update completes.
		 * In a sample app, going against a simple provider based on a local
		 * database, the block will be momentary, but in a real app you should
		 * use android.content.AsyncQueryHandler or android.os.AsyncTask.
		 */
		getContentResolver().update(mUri, // The URI for the record to update.
				values, // The map of column names and new values to apply to
						// them.
				null, // No selection criteria are used, so no where columns are
						// necessary.
				null // No where columns are used, so no where arguments are
						// necessary.
				);
		requestSync();
	}

	/**
	 * This helper method cancels the work done on a note. It deletes the note
	 * if it was newly created, or reverts to the original text of the note i
	 */
	private final void cancelNote() {
		if (mCursor != null) {
			if (mState == STATE_EDIT) {
				// Put the original note text back into the database
				mCursor.close();
				mCursor = null;
				ContentValues values = new ContentValues();
				values.put(NotePad.Notes.COLUMN_NAME_NOTE, mOriginalContent);
				getContentResolver().update(mUri, values, null, null);
			} else if (mState == STATE_INSERT) {
				// We inserted an empty note, make sure to delete it
				deleteNote();
			}
		}
		setResult(RESULT_CANCELED);
		mText.setText("");
		finish();
	}

	/**
	 * Take care of deleting a note. Simply deletes the entry.
	 */
	private final void deleteNote() {
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
			// Set the note deleted status to 1.
			ContentValues values = new ContentValues();
			values.put(NotePad.Notes.COLUMN_NAME_DELETED, 1);
			values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
					System.currentTimeMillis());
			getContentResolver().update(mUri, values, null, null);
		}
		mText.setText("");
	}

	public void showDialog2() {
		// Toast.makeText(this,"A toast has been displayed .",Toast.LENGTH_LONG).show();

		// save_note_Dialog
		save_note_Dialog = new Dialog(NoteEditor.this);
		save_note_Dialog.setContentView(R.layout.save_nte_dialog_layout);

		save_note_Dialog.setTitle("Todays Note ");

		title_note_text_view = (EditText) save_note_Dialog
				.findViewById(R.id.title_note_editText);

		title_note_text_view.setText(title_note);

		Button save_to_google = (Button) save_note_Dialog
				.findViewById(R.id.save_to_google);
		save_to_google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				title_note = title_note_text_view.getText().toString();
				if (title_note.length() > 0)
					save_note_Dialog.cancel();
			}
		});
		save_note_Dialog.show();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		if (mUri != null) {
			return new CursorLoader(this, mUri, PROJECTION, null, null, null);
		} else {
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		boolean closeProgressBar = true;
		mCursor = cursor;

		if (mCursor != null && mCursor.getCount() > 0) {
			/*
			 * Moves to the first record. Always call moveToFirst() before
			 * accessing data in a Cursor for the first time. The semantics of
			 * using a Cursor are that when it is created, its internal index is
			 * pointing to a "place" immediately before the first record.
			 */
			mCursor.moveToFirst();

			int colModifiedDateIndex = mCursor
					.getColumnIndex(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE);

			if (mCursor.getLong(colModifiedDateIndex) != -1) {
				// Modifies the window title for the Activity according to the
				// current
				// Activity state.
				if (mState == STATE_EDIT) {
					// Set the title of the Activity to include the note title
					int colTitleIndex = mCursor
							.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE);
					String title = mCursor.getString(colTitleIndex);
					Resources res = getResources();
					String text = String
							.format(res.getString(R.string.title_edit), title);
					setTitle(text);
					// Sets the title to "create" for inserts
				} else if (mState == STATE_INSERT) {
					setTitle(getText(R.string.title_create));
				}

				/*
				 * onResume() may have been called after the Activity lost focus
				 * (was paused). The user was either editing or creating a note
				 * when the Activity paused. The Activity should re-display the
				 * text that had been retrieved previously, but it should not
				 * move the cursor. This helps the user to continue editing or
				 * entering.
				 */

				/*
				 * Gets the note text from the Cursor and puts it in the
				 * TextView, but doesn't change the text cursor's position.
				 */
				int colNoteIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
				String note = mCursor.getString(colNoteIndex);
				if (note == null) {
					mText.setTextKeepState("");
				} else if (!note.equals(mOriginalContent)) {
					mText.setTextKeepState(note);
				}

				// Stores the original note text, to allow the user to revert
				// changes.
				if (mOriginalContent == null) {
					mOriginalContent = note;
				}
			} else {
				closeProgressBar = false;
			}
		} else if (mFileId != null && mFileId.length() > 0) {
			if (!mInserted) {
				// File does not exist in database yet, add it.
				Uri insertUri = Uri.parse("content://com.google.provider.NotePad/"
						+ mAccountName + "/notes/");
				ContentValues values = new ContentValues();

				values.put(NotePad.Notes.COLUMN_NAME_ACCOUNT, mAccountName);
				values.put(NotePad.Notes.COLUMN_NAME_FILE_ID, mFileId);
				values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, -1);

				mUri = getContentResolver().insert(insertUri, values);
				// Request a sync from the SyncAdapter to retrieve the data from
				// Google
				// Drive.
				requestSync();
				setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));
				mInserted = true;
				getLoaderManager().initLoader(0, null, this);
			} else {
				Log.d(TAG, "File already inserted");
			}
			closeProgressBar = false;
		} else {
			// Something is wrong. The Cursor should always contain data. Report
			// an
			// error in the note.
			setTitle(getText(R.string.error_title));
			mText.setText(getText(R.string.error_message));
		}
		if (closeProgressBar && mProgressBar != null) {
			mProgressBar.dismiss();
			mProgressBar = null;
			// Redraw the option menu.
			invalidateOptionsMenu();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mCursor = null;
	}

	private void requestSync() {
		if (mAccountName != null && mAccountName.length() > 0) {
			final GoogleAccountManager accountManager = new GoogleAccountManager(this);
			Account account = accountManager.getAccountByName(mAccountName);

			if (account != null) {
				Bundle options = new Bundle();
				options.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
				ContentResolver.requestSync(account, "com.google.provider.NotePad",
						options);
			}
		}
	}
}
