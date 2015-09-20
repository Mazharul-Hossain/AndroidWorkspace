package com.carouseldemo.main;

import java.io.IOException;
import java.io.InputStream;

import com.pinterest.pinit.PinItButton; 
import com.pinterest.pinit.PinItListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Pin_Url_In_Pinterest extends Activity  implements View.OnClickListener 
{
	 private static final String TAG = "Demo Activity";
	 private static final String WEB_URL = "http://placekitten.com";
	 private static final String DEFAULT_DESCRIPTION = "This image is from gallery";
	 private static final int IMAGE_SELECT = 801;
	 public static final String CLIENT_ID = "1435356";

	 private ImageView mImage;
	 private Button mButton;
	 private TextView mDescriptEt;
	 private PinItButton mPinIt;
	 private TextView mUriTv;
	 Dialog dialog;
	 private Uri mImageUri;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState );
	
	    dialog = new Dialog(Pin_Url_In_Pinterest.this);
	    dialog.setContentView(R.layout.dialog_pinterest_take_pic_from_gallery);
	    dialog.setTitle("Android Custom Dialog Box");
	    dialog.show();
	    
	    mButton = (Button) dialog.findViewById(R.id.gallery_bt);
        mButton.setOnClickListener(this);
        mImage = (ImageView) dialog.findViewById(R.id.source_iv);
        mUriTv = (TextView) dialog.findViewById(R.id.uri_tv);
        mDescriptEt = (TextView) dialog.findViewById(R.id.desc_tv);
        mDescriptEt.setText(DEFAULT_DESCRIPTION);

        mPinIt = (PinItButton) dialog.findViewById(R.id.pin_bt);

        PinItButton.setDebugMode(true);
        PinItButton.setPartnerId("myApp");
        mPinIt.setUrl(WEB_URL);
        mPinIt.setDescription(DEFAULT_DESCRIPTION);
        PinItButton.setPartnerId(CLIENT_ID);
        mPinIt.setListener(_listener);
	}
	
	PinItListener _listener = new PinItListener() {

        @Override
        public void onStart() 
        {
            super.onStart();
            Log.i(TAG, "PinItListener.onStart");
        }

        @Override
        public void onComplete(boolean completed) {
            super.onComplete(completed);
            dialog.cancel();
            Log.i(TAG, "PinItListener.onComplete");
        }

        @Override
        public void onException(Exception e) {
            super.onException(e);
            Log.i(TAG, "PinItListener.onException");
        }

    };

	@Override
	public void onClick(View v) 
	{
		if( v == mButton )
		{
			 Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		     startActivityForResult(intent, IMAGE_SELECT);
		}
		
	}
	
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data)
	 {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_SELECT) {
	            // Check for returned image from gallery
	            if (data == null)
	                return;

	            Uri imageUri = data.getData();
	            setImageUri(imageUri);

	        }
	    }

	    public void setImageUri(Uri imageUri) {
	        if (imageUri == null)
	            return;

	        mImageUri = imageUri;
	        mUriTv.setText(imageUri.toString());
	        mPinIt.setImageUri(mImageUri);
	        try {
	            Bitmap pinthumb = imageFromUri(this, mImageUri, 400, 300);
	            mImage.setImageBitmap(pinthumb);
	        } catch (IOException ignored) {
	        }
	    }

	    /**
	     * Get the image from Uri. If the image is too large, sample it.
	     * @param context
	     * @param uri
	     * @param width
	     * @param height
	     * @return
	     * @throws IOException
	     */
	    public static Bitmap imageFromUri(Context context, Uri uri, int width,
	        int height) throws IOException {
	        ContentResolver resolver = context.getContentResolver();
	        InputStream input = resolver.openInputStream(uri);

	        // Just get some info
	        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
	        onlyBoundsOptions.inJustDecodeBounds = true;
	        onlyBoundsOptions.inDensity = Bitmap.DENSITY_NONE;
	        onlyBoundsOptions.inPurgeable = true;
	        onlyBoundsOptions.inInputShareable = true;
	        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
	        input.close();
	        int xSample = 0, ySample = 0;
	        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
	            return null;
	        else {
	            xSample = (int) Math.floor(onlyBoundsOptions.outWidth / width);
	            ySample = (int) Math.floor(onlyBoundsOptions.outHeight / height);
	        }

	        // Decode
	        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
	        bitmapOptions.inDensity = Bitmap.DENSITY_NONE;
	        bitmapOptions.inPurgeable = true;
	        bitmapOptions.inInputShareable = true;
	        bitmapOptions.inSampleSize = Math.min(xSample, ySample);

	        input = context.getContentResolver().openInputStream(uri);
	        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
	        input.close();

	        return bitmap;
	    }

}