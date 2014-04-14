package com.example.myp;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	public static final String MyPREFERENCES = "mypreferences";
	private MyReceiver r;
	private ImageView image;
	private TextView text;
	private static int SPLASH_TIME_OUT = 1100;
	public static final String uri = "content://media/external/images/media";

	// private static final Uri MEDIA_URI =
	// Uri.parse("content://media/external/images/media/1");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		r = new MyReceiver(this);
		image = (ImageView) findViewById(R.id.image);
		text = (TextView) findViewById(R.id.textview);
		Uri path = Uri.parse(uri);
		Random random = new Random();
		//Cursor c=this.getContentResolver().query(path, projection, selection, selectionArgs, sortOrder)
		String[] filepath = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(path, filepath, null, null,
				null);
		int c=cursor.getCount();
		cursor.moveToFirst();
		cursor.move(random.nextInt(c));

		String imagepath = cursor.getString(cursor.getColumnIndex(filepath[0]));
		Log.i("siva", "siva" + imagepath);
		Bitmap map = BitmapFactory.decodeFile(imagepath);
		// Bitmap d = new BitmapDrawable(ctx.getResources() ,
		// w.photo.getAbsolutePath()).getBitmap();
		int nh = (int) (map.getHeight() * (1024.0 / map.getWidth()));
		Bitmap scaled = Bitmap.createScaledBitmap(map, 1024, nh, true);
		// iv.setImageBitmap(scaled);
		image.setImageBitmap(scaled);
		// map.recycle();
		cursor.close();
		String mydate = java.text.DateFormat.getDateTimeInstance().format(
				Calendar.getInstance().getTime());
		SharedPreferences preference = getApplicationContext()
				.getSharedPreferences("my_pref", 0);
		Editor edit = preference.edit();
//		if (!preference.contains("is_AM")) {
//			// Editor edit = preference.edit();
//			if (mydate.substring(mydate.length() - 2, mydate.length()).equals("AM")) {
//				edit.putBoolean("is_AM", true);
//				Log.i("siva", "first time AM");
//			} else {
//				edit.putBoolean("is_AM", false);
//				Log.i("siva", "first time M");
//			}
//			edit.commit();
//		}
		int hour = new TimePicker(this).getCurrentHour();
		boolean isAM = hour < 12 ? true : false;
		if (isAM) {
			/*
			 * edit.putBoolean("is_AM",true); }else{ edit.putBoolean("is_AM",
			 * false); } edit.commit();
			 */
			// if(preference.getBoolean("is_AM", true)){
			// if(preference.getBoolean("is_AM", (Boolean) null)==true){
			// if(preference.contains("is_AM")){
			text.setText("GOOD MORNING");
			edit.putBoolean("is_AM", false);
		} else {
			text.setText("GOOD Afternoon");
			edit.putBoolean("is_AM", true);
			Log.i("siva", "gud afternoon");
		}
		//edit.commit();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				finish();

			}
		}, SPLASH_TIME_OUT);

	}

}
