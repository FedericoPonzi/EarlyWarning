package ium.earlywarning;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class SOSActivity extends ActionBarActivity {

	private float mProgressStatus = 0; // The progress bar.
	private Handler mHandler;
	private ProgressBar mProgress;
	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos);
		// Show the Up button in the action bar.
		setupActionBar();
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);

		mHandler = new Handler();

		startProgressBar();
		Button annullaBtn = (Button) findViewById(R.id.annulla_btn);
		annullaBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent s = new Intent(SOSActivity.this, MainActivity.class);
				startActivity(s);
				finish();
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.fine_action_bar:
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void startProgressBar() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (mProgressStatus < 100) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mProgressStatus += 10;
					mHandler.post(new Runnable() {

						@Override
						public void run() {

							mProgress.setProgress((int) mProgressStatus);
							if (mProgress.getProgress() == 100) {
								startActivity(new Intent(getApplicationContext(), AlertFriendsActivity.class));
								return;
							}
						}
					});
				}
			}
		});
		mThread.start();
	}
	// private class ProgressBarUpdaterThread implements Runnable
	// {
	// @Override
	// public void run()
	// {
	// // TODO Auto-generated method stub
	//
	// }
	// }
}
