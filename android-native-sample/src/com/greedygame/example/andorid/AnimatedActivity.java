package com.greedygame.example.andorid;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.greedygame.android.FloatAdLayout;
import com.greedygame.android.GreedyUtilities;
import com.greedygame.android.exceptions.AgentInitNotCalledException;

public class AnimatedActivity extends Activity {
   
    private String themePath = null;
    private FloatAdLayout floatAdlayout = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_animated);
    	
    	themePath = SplashScreenActivity.ggAgent.getActivePath();
    	
    	/*** Fetching Float Ad unit ***/
    	floatAdlayout = new FloatAdLayout(this);
    	Log.i("GreedyGame Sample", "activePath "+themePath);
		
    	try {
    		floatAdlayout.fetchHeadAd("unit-701", true);
    		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			((Activity) this).addContentView(floatAdlayout, params);
		} catch (AgentInitNotCalledException e) {
			e.printStackTrace();
		}
    	
    	/*** Changing Native Ad units ***/
    	ImageView sun =  (ImageView) findViewById(R.id.sun);
    	
    	Bitmap bmp = GreedyUtilities.getBitmapByResid(SplashScreenActivity.ggAgent, R.id.sun);
    	if(bmp != null){
    		sun.setImageBitmap(bmp);
    	}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		SplashScreenActivity.ggAgent.onResume();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		floatAdlayout.removeAllHeadAd();
	}
	
	
	@Override
	public void onPause(){
		super.onPause();
		SplashScreenActivity.ggAgent.onPause();
	}


}
