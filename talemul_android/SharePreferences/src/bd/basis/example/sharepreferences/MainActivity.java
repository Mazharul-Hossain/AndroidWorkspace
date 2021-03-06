package bd.basis.example.sharepreferences;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	EditText etVal1,etVal2;
	Button btnSet,btnGet;
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pref = getSharedPreferences("demo", MODE_PRIVATE);
		etVal1=(EditText)findViewById(R.id.etVal1);
		etVal2=(EditText)findViewById(R.id.etval2);
		btnSet=(Button)findViewById(R.id.btnSet);
		btnGet=(Button)findViewById(R.id.btnGet);
		
		
		btnSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			SharedPreferences.Editor editor=pref.edit();
			editor.putString("val1", etVal1.getText().toString());
			editor.putString("val2", etVal2.getText().toString());
			editor.commit();
				
			}
		});
		
	btnGet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String val1=pref.getString("val1", "");
				String val2=pref.getString("val2", "");
				etVal1.setText(val1);
				etVal2.setText(val2);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
