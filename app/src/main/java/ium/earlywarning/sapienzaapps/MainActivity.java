package ium.earlywarning.sapienzaapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	@SuppressWarnings("unused")
	private static String LOG_TAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button alChiusoBtn = (Button) findViewById(R.id.button_chiuso);
		alChiusoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,
						SecondActivity.class));
			}
		});
		Button alApertoBtn = (Button) findViewById(R.id.button_aperto);
		alApertoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SecondActivity.class);
				intent.putExtra(SecondActivity.APERTO, true);
				startActivity(intent);
			}
		});
		Button sosBtn = (Button) findViewById(R.id.sos_btn);
		sosBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SOSActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

}
