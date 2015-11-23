package com.greedygame.example.andorid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.greedygame.android.GreedyGameAgent;
import com.greedygame.android.GreedyGameAgent.FETCH_TYPE;
import com.greedygame.android.GreedyGameAgent.OnINIT_EVENT;

public class SplashScreenActivity extends Activity {

	protected static GreedyGameAgent ggAgent;
	private final String[] units = {"sun.png"};
	private TextView loadingView = null;
	private float downloadProgress = 0;
	private Runnable updateProgress = null;
	private ProgressBar progressBar;
	
	 
	class GG_Listner implements com.greedygame.android.IAgentListner{

		@Override
		public void onProgress(float progress) {
			Log.i("GreedyGame Sample", "Downloaded = "+progress+"%");
			downloadProgress = progress;
			runOnUiThread(updateProgress);
		}

		@Override
		public void onDownload(boolean success) {
			loadingView.setText("Loaded");
		}



		@Override
		public void onInit(OnINIT_EVENT response) {
			Log.i("GreedyGame Sample", "response = "+response);
			if(response == OnINIT_EVENT.CAMPAIGN_NOT_AVAILABLE || response == OnINIT_EVENT.CAMPAIGN_CACHED){
				loadingView.setText("Loaded");
			}
		}


		
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.splash_layout);
		progressBar = (ProgressBar) findViewById(R.id.progressbar2);
		  
		 
		loadingView = (TextView) findViewById(R.id.loadingView);
		updateProgress = new Runnable() 
		{
			@Override
			public void run() 
			{
				progressBar.setProgress((int)downloadProgress);
				loadingView.setText("Loading... ["+(int)downloadProgress+"% ]");
			}
		};
		
		ggAgent = new GreedyGameAgent(this, new GG_Listner());
		ggAgent.setDebug(true);
		ggAgent.init("68712536", units, FETCH_TYPE.DOWNLOAD_BY_PATH);
		
		final Activity thisActivity = this;
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(thisActivity, AnimatedActivity.class);
				startActivity(intent);
	        }
	    });

		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(thisActivity, NativeActivity.class);
				startActivity(intent);
	        }
	    });
	}
	

}
