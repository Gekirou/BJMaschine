package de.cfc.bjm.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import de.cfc.bjm.R;
import de.cfc.bjm.db.PrefManager;

public class User {
	public String name;
	public long id;
	public long verbindungid;
	public long xp;
	public long timestamp;
	public int level;

	public User(long id, String name, long verbindungID, long xp, long timestamp, int level){
		this.id = id;
		this.name = name;
		this.verbindungid = verbindungID;
		this.xp = xp;
		this.timestamp = timestamp;
		this.level = level;
	}
	
	public User(long id, String name, long xp){
		this.id = id;
		this.name = name;
		this.xp = xp;
	}
	
	public String getAvatarPath(PrefManager prefs){
		return prefs.getAvatarFolder()+id+".png";
	}
	
	public void setAvatarToImageView(ImageView view, Context ctx){
		PrefManager prefs = PrefManager.getInstance(ctx);
		 File check = new File(getAvatarPath(prefs)); 
     	Log.d("BJM", "Avatar path: "+check.getAbsolutePath());

	    	if(check.exists()){
	        	Bitmap pic = BitmapFactory.decodeFile(check.getAbsolutePath());
	        	view.setImageBitmap(pic);
	        } else {
	        	Drawable pic = ctx.getResources().getDrawable(R.drawable.bier);
	        	view.setImageDrawable(pic);
	        }
	}
}
