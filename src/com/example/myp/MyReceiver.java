package com.example.myp;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.TimePicker;

public class MyReceiver extends BroadcastReceiver {
	private static final String IS_AM_ALREADY_LAUNCHED_KEY = "is_am_already_launched";
	private static final String IS_PM_ALREADY_LAUNCHED_KEY = "is_pm_already_launched";

	public MyReceiver() {

	}

	public MyReceiver(Context context) {
		super();

	}

	@Override
	public void onReceive(Context mContext, Intent intent) {

		Log.d("receiver", "main");
		String mydate = java.text.DateFormat.getDateTimeInstance().format(
				Calendar.getInstance().getTime());

		SharedPreferences preference = mContext.getSharedPreferences("my_pref",
				0);
		Editor edit = preference.edit();
		/*
		 * boolean isFirstTime = preference.getBoolean("is_AM", true);
		 * if(isFirstTime){
		 * 
		 * isFirstTime = false; Editor edit = preference.edit();
		 * edit.putBoolean("is_AM", isFirstTime); edit.commit();
		 * 
		 * context.startActivity(new Intent(context, MainActivity.class)
		 * .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		 * 
		 * }
		 */
		int hour = new TimePicker(mContext).getCurrentHour();
		boolean isAM = hour < 12 ? true : false;
		if (!preference.contains("is_AM")) {
			launchActivity(mContext, intent);
			edit.putBoolean(IS_PM_ALREADY_LAUNCHED_KEY, false);
			edit.putBoolean(IS_AM_ALREADY_LAUNCHED_KEY, false);
			// Editor edit = preference.edit();
			if (isAM) {
				edit.putBoolean("is_AM", true);
				edit.putBoolean(IS_AM_ALREADY_LAUNCHED_KEY, true);
				Log.i("siva", "first time AM");
			} else {
				edit.putBoolean("is_AM", false);
				edit.putBoolean(IS_PM_ALREADY_LAUNCHED_KEY, true);
				Log.i("siva", "first time M");
			}
			edit.commit();
		} else {
			boolean isLaunched = true;
			if (isAM) {
				isLaunched = preference.getBoolean(IS_AM_ALREADY_LAUNCHED_KEY, true);
				edit.putBoolean(IS_PM_ALREADY_LAUNCHED_KEY, false);
				Log.i("siva", "first time AM");
				if(!isLaunched){
					edit.putBoolean(IS_AM_ALREADY_LAUNCHED_KEY, true);
					launchActivity(mContext, intent);
				}
			} else {
				/*edit.putBoolean("is_AM", false);
				edit.putBoolean(IS_PM_ALREADY_LAUNCHED_KEY, true);
				Log.i("siva", "first time M");*/
				isLaunched = preference.getBoolean(IS_PM_ALREADY_LAUNCHED_KEY, true);
				edit.putBoolean(IS_AM_ALREADY_LAUNCHED_KEY, false);
				Log.i("siva", "first time AM");
				if(!isLaunched){
					edit.putBoolean(IS_PM_ALREADY_LAUNCHED_KEY, true);
					launchActivity(mContext, intent);
				}
			}
			edit.commit();
		}

		/*
		 * if(preference.getBoolean("is_AM",true) &&
		 * mydate.substring(mydate.length()-2, mydate.length()).equals("AM")){
		 * edit.putBoolean("is_AM",false); } if(preference.getBoolean("is_AM",
		 * false) && mydate.substring(mydate.length()-2,
		 * mydate.length()).equals("PM")){ edit.putBoolean("is_AM",true);
		 * 
		 * }
		 */
		edit.commit();

	}
	
	private void launchActivity(Context mContext, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

			Log.d("receiver", "screen off");
		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

			Log.d("receiver", "screen on");

			mContext.startActivity(new Intent(mContext, MainActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
					.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			Log.d("receiver", "aft activity");
		} else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			Log.d("receiver", "unlock");

			mContext.startActivity(new Intent(mContext, MainActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
}
