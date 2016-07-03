package de.cfc.bjm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import de.cfc.bjm.adapter.VerbindungenSpinnerAdapter;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.db.PrefManager;
import de.cfc.bjm.model.Verbindung;

public class NewUserPanel extends Activity{
	Button saveNewUser;
	Button avatarSelect;
	ImageView ava;
	String avatar="";
	EditText nameEdit;
	Spinner verbindungSpinner;
	BJMMySQLDb db;
	PrefManager prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuser);
		db = BJMMySQLDb.getInstance(this);
		prefs = PrefManager.getInstance(this);
		initGUI();
	}
	
	public void initGUI(){
		nameEdit = (EditText) findViewById(R.id.name);  
		verbindungSpinner = (Spinner) findViewById(R.id.verbindung_spinner);
    	ava = (ImageView) findViewById(R.id.nu_avatar);
    	
    	
        List<Verbindung> verbindungen = db.getVerbindungList();

        
        File check = new File(avatar);
        if(check.exists()){
        	Bitmap pic = BitmapFactory.decodeFile(avatar);
        	ava.setImageBitmap(pic);
        	
        } else {
        	Drawable pic = getResources().getDrawable(R.drawable.bier);
            File file = new File(prefs.getAvatarFolder()+"bier.png");

        	if(!file.exists()){
	            Bitmap bmp = ((BitmapDrawable) pic).getBitmap();
	            
	            FileOutputStream out = null;
				try {
					out = new FileOutputStream(file.getAbsolutePath());
					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			        out.flush();
			        out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	          
            }
        	ava.setImageDrawable(pic);
        	avatar = file.getAbsolutePath();
        }
        VerbindungenSpinnerAdapter adapter = new VerbindungenSpinnerAdapter(this, verbindungen);
        verbindungSpinner.setAdapter(adapter);
        
        saveNewUser = (Button) findViewById(R.id.saveNewUser);
        saveNewUser.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(nameEdit.getText().length()>16){
					showAlertDialogLongName();
				}else{ if(nameEdit.getText().length()==0){
					showAlertDialogNoName();
				} else{
					saveUser();
					finish();
				}
				}
			}
		});
        
        avatarSelect = (Button) findViewById(R.id.avatarselectbtn);
        avatarSelect.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				final Intent i = new Intent(v.getContext(), ImageSelector.class);
				startActivityForResult(i, 12);
			}
		});
	}
	
	public void saveUser(){
		db.insertUser(nameEdit.getText().toString(), BitmapFactory.decodeFile(avatar),  verbindungSpinner.getSelectedItemId());
	}
	
	public void showAlertDialogNoName(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Zu peinlich 'nen Namen anzugeben?")
		       .setCancelable(true)
		       .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           	dialog.cancel();		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		}
	
	public void showAlertDialogLongName(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Laberkaraffe als Namen? W?hl was ordentliches!")
		       .setCancelable(true)
		       .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           	dialog.cancel();}
		       });
		AlertDialog alert = builder.create();
		alert.show();
		}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
		Bundle bundle = data.getExtras();
		avatar = bundle.getString("avatar");
		initGUI();
		}
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.nu_save:  
	  		saveUser();
	  		finish();
	      default:
	        return super.onOptionsItemSelected(item);
	      }
	    }
	    
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.layout.nu_menu, menu);
	        return true;
	    }
}
