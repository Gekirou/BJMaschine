package de.cfc.bjm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import de.cfc.bjm.adapter.ImageAdapter;
import de.cfc.bjm.db.PrefManager;


public class ImageSelector extends Activity{

	Button foto;
	String avatar;
	File imageFolder;
	File photo;
	PrefManager prefs;
	String temp_avatar;
	
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gridview);
    prefs = PrefManager.getInstance(this);
    GridView gridview = (GridView) findViewById(R.id.gridview);
    gridview.setAdapter(new ImageAdapter(this));
    temp_avatar = prefs.getTempFolder()+"avatar.png";
    gridview.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            switch (position){
            case 0:
            	avatar="conan.png";
            	break;
            case 1:
            	avatar="bier.png";
            	break;
            case 2:
            	avatar="corpsstudent.png";
            	break;
            case 3:
            	avatar="anonym.png";
            	break;
            case 4:
            	avatar="hendrix.png";
            	break;
            case 5:
            	avatar="spongebob.png";
            	break;
            default:
            	avatar="anonym.png";
            	break;
            }
            File file = new File(prefs.getAvatarFolder()+avatar);
            if(!file.exists()){
	            Drawable draw = getResources().getDrawable(ImageAdapter.mThumbIds[position]);
	            Bitmap bmp = ((BitmapDrawable) draw).getBitmap();
	            
	            FileOutputStream out = null;
				try {
					out = new FileOutputStream(file.getAbsolutePath());
					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
					
			        out.flush();
			        out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          
            }
            final Intent i = new Intent();
            
            i.putExtra("avatar", file.getAbsolutePath());
            setResult(RESULT_OK, i);
            finish();
        }
    });
    
    foto = (Button) findViewById(R.id.take_photo);
    foto.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
//			 		imageFolder = new File(
//				   	   Environment.getExternalStorageDirectory(), ".bjm/avatar");
//				   	   if (!imageFolder.exists()) {
//				   	    imageFolder.mkdirs();
//				   	   }
//				   String[] size=imageFolder.list(null);
//				   	photo = new File(imageFolder.getPath(), size.length+".jpg");
//				   	avatar=size.length+".jpg";
//				   	   if (!photo.exists()) {
//				   	    try {
//							photo.createNewFile();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				   	   }
					File avatarFile = new File(temp_avatar);
			        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(avatarFile));
			        startActivityForResult(imageCaptureIntent, 1);
			 }
	});
    
    
}
protected void onActivityResult(int requestCode, int resultCode, Intent data){
	if(resultCode==RESULT_OK){
		Bitmap temp_pic = BitmapFactory.decodeFile(temp_avatar);
				
		Bitmap pic = Bitmap.createBitmap(temp_pic.getWidth(), temp_pic.getHeight(), Config.RGB_565);
        
		
		   ExifInterface exif = null;
		try {
			exif = new ExifInterface(temp_avatar);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		   int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		   int rotate = 0;
		   switch(orientation) {
		     case ExifInterface.ORIENTATION_ROTATE_270:
		         rotate = 270;
		         break;
		     case ExifInterface.ORIENTATION_ROTATE_180:
		         rotate = 180;
		         break;
		     case ExifInterface.ORIENTATION_ROTATE_90:
		         rotate = 90;
		         break;
		   }
		   Canvas c = new Canvas(pic);
		   Matrix mtrix = new Matrix();
		   mtrix.setRotate(rotate,pic.getWidth()/2,pic.getHeight()/2);
	       c.drawBitmap(temp_pic, mtrix, new Paint());
		   

		
	    int width = pic.getWidth();
	    int height = pic.getHeight();
	    Bitmap cropped=pic;
	    if(height>width){
//	    	cropped = Bitmap.createBitmap(pic,0,(height-width)/2,width,width);
	    	cropped = Bitmap.createBitmap(pic,(width-height)/2,0,height,height);
	    }else{
	    	cropped = Bitmap.createBitmap(pic,(width-height)/2,0,height,height);
	    }
	    width = cropped.getWidth();
	    height = cropped.getHeight();
	    int newWidth = 300;
	    int newHeight = 300;
	    
	    // calculate the scale - in this case = 0.4f
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    
	    // createa matrix for the manipulation
	    Matrix matrix = new Matrix();
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);

	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(cropped, 0, 0, width, height, matrix, true); 
	    try {
	        FileOutputStream out = new FileOutputStream(temp_avatar,false);
	        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 70, out);
	        out.flush();
            out.close();
//            File avatarFile
//            MediaStore.Images.Media.insertImage(getContentResolver(),avatar,,photo.getName());
	 } catch (Exception e) {
	        e.printStackTrace();
	 }
	
	
	final Intent i = new Intent();
    i.putExtra("avatar", temp_avatar);
    setResult(RESULT_OK, i);
    finish();
	}
}

}