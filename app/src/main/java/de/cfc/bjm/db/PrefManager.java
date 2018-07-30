package de.cfc.bjm.db;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import de.cfc.bjm.R;
import de.cfc.bjm.adapter.ImageAdapter;

public class PrefManager {
	
	private SharedPreferences pref;
	private Context context;

	private static final String IMAGES_FOLDER="avatar/";
	private static final String TEMP_FOLDER = "temp_data/";
	public static final String BASE_FOLDER=Environment.getExternalStorageDirectory().getPath()+"/bjm/";
	private static PrefManager instance;

	public static PrefManager getInstance(Context ctx){
		if(instance == null)
			instance = new PrefManager(ctx);
		
		return instance;
	}
	
	private PrefManager(Context context){
		pref = context.getSharedPreferences("bjm",Context.MODE_PRIVATE);
		this.context=context;
		initDefaultUserPrefs();
	}
	

	public void clearPrefs(){
		pref.edit().clear().apply();
	}
	
	public String getAvatarFolder(){
		File file = new File(BASE_FOLDER+IMAGES_FOLDER);
		file.mkdirs();
		return BASE_FOLDER+IMAGES_FOLDER;
	}
	
	private void initDefaultUserPrefs(){
//		if(!isUserPrefSet(Prefs.BJM_DB_NAMESPACE))
			pref.edit().putString(Prefs.BJM_DB_NAMESPACE, "bjm").apply();
//		if(!isUserPrefSet(Prefs.BJM_DB_ADDRESS))	
			pref.edit().putString(Prefs.BJM_DB_ADDRESS, "jdbc:mysql://tri-kon.de:3306").apply();
//		if(!isUserPrefSet(Prefs.BJM_DB_USER))
			pref.edit().putString(Prefs.BJM_DB_USER, "bjm_user").apply();
//		if(!isUserPrefSet(Prefs.BJM_DB_PASSWORD))
			pref.edit().putString(Prefs.BJM_DB_PASSWORD, "df3iub1").apply();
	}
	
	private boolean isUserPrefSet(String pref){
		return pref.contains(pref);
	}

	public String getDBNamespace(){
		return pref.getString(Prefs.BJM_DB_NAMESPACE, "bjm");
	}
	
	public String getDBURL(){
		return pref.getString(Prefs.BJM_DB_ADDRESS,  "jdbc:mysql://tri-kon.de:3306");
	}
	
	public String getDBUser(){
		return pref.getString(Prefs.BJM_DB_USER, "bjm_user");
	}
	
	public String getDBPassword(){
		return pref.getString(Prefs.BJM_DB_PASSWORD, "df3iub");
	}
	
	public String getTempFolder(){
		File file = new File(BASE_FOLDER+TEMP_FOLDER);
		file.mkdirs();
		return BASE_FOLDER+TEMP_FOLDER;
	}

}
