package com.example.gameactivity;

import java.util.Locale;
import android.os.Bundle;
import android.os.PowerManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StandardActivity extends Activity implements Runnable {
	private Locale locale = new Locale("no");
	private Locale locale2 = new Locale("en");
	private static boolean lang;
	protected PowerManager.WakeLock mWakeLock;
	private volatile boolean paused;
    private final Object signal = new Object();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard);
		ActionBar ab = getActionBar();
		ab.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		if (lang) {
			menu.getItem(0).setTitle("English");
		} else
			menu.getItem(0).setTitle("Norwegian"); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Resources res = getResources();
		Configuration newConfig = new Configuration(res.getConfiguration());
		if (!lang) {
			newConfig.locale = locale;
			res.updateConfiguration(newConfig, null);
			restartActivity();
			lang = true;
			return true;
		} else {
			newConfig.locale = locale2;
			res.updateConfiguration(newConfig, null);
			restartActivity();
			lang = false;
			return true;
		}
	}

	public void restartActivity() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

    public void run() {

        while(paused) {
           synchronized(signal) {
           try {
			signal.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
           }
        }

        while(paused) {
           synchronized(signal){
        	   try {
				signal.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	   }
        }
    }

    public void setPaused() {
        paused = true;      
    }

    public void setUnpaused() {
        paused = false;
        synchronized(signal){
        	signal.notify();
        }
    }
    
	public void onDestroy() {
	    super.onDestroy();
	}

}
