package com.greedygame.example.andorid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.greedygame.android.FloatAdLayout;
import com.greedygame.android.exceptions.AgentInitNotCalledException;

public class NativeActivity extends Activity {
	
	private FloatAdLayout floatAdlayout = null;
	   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.native_layout);
    	
    	/*** Fetching Float Ad unit ***/
    	//floatAdlayout = (FloatAdLayout) findViewById(R.id.gg_holder_1);
    	floatAdlayout = new FloatAdLayout(this);
    	addContentView(floatAdlayout, new FrameLayout.LayoutParams(
    											FrameLayout.LayoutParams.WRAP_CONTENT, 
    											FrameLayout.LayoutParams.WRAP_CONTENT));
    	try {
			floatAdlayout.fetchHeadAd("unit-363");
		} catch (AgentInitNotCalledException e) {
			e.printStackTrace();
		}
    	
	}

}
