package de.cfc.bjm;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import de.cfc.bjm.data.DataHolder;

public class EditUserPanel extends Activity{
	Spinner spinner;
	Button saveNewUser;
	Button avatarSelect;
	EditText nameEdit; 
	Bundle bdl;
	ImageView ava;
	String avatar;
	Bundle instanceState;
	Spinner verbindungSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.newuser);
//		instanceState = savedInstanceState;
//        bdl = getIntent().getExtras();
//        Log.d("onCreate EditUser", "User ID: "+bdl.getInt("id"));
//        nameEdit = (EditText) findViewById(R.id.name); 
//        verbindungSpinner = (Spinner) findViewById(R.id.verbindung_spinner);
//        ava = (ImageView) findViewById(R.id.nu_avatar);
//        
//		DataHolder.db.open();
//		TextView title = (TextView) findViewById(R.id.nu_title);
//		title.setText("Bearbeite Nutzer");
//        
//        Cursor userCursor = DataHolder.db.getUser(bdl.getInt("id"));
//    	spinner = (Spinner) findViewById(R.id.verbindung_spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//
//        Cursor verbindungen = DataHolder.db.getVerbindungen();
//        startManagingCursor(verbindungen);
//        verbindungen.moveToFirst();
//        while (verbindungen.isAfterLast() == false) {
//            adapter.add(verbindungen.getString(1));
//       	    verbindungen.moveToNext();
//        }
//        
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        
//        if(avatar==null){
//        	avatar = userCursor.getString(2);
//        }
//        
//        File check = new File("/sdcard/.bjm/avatar/"+avatar); 
//    	if(check.exists()){
//        	Bitmap pic = BitmapFactory.decodeFile("/sdcard/.bjm/avatar/"+avatar);
//        	ava.setImageBitmap(pic);
//        	
//        } else {
//        	Bitmap pic = BitmapFactory.decodeFile("/sdcard/.bjm/avatar/bier.jpg");
//        	ava.setImageBitmap(pic);
//        	avatar = "bier.jpg";
//        	
//        }
//       
//        spinner.setSelection(userCursor.getInt(3));
//        nameEdit.setText(userCursor.getString(1));
//        
//        saveNewUser = (Button) findViewById(R.id.saveNewUser);
//        saveNewUser.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				if(nameEdit.getText().length()>16){
//					showAlertDialogLongName();
//				}else{ if(nameEdit.getText().length()==0){
//					showAlertDialogNoName();
//				} else{
//					saveUser();
//					finish();
//				}
//				}
//			}
//		});
//        
//        avatarSelect = (Button) findViewById(R.id.avatarselectbtn);
//        avatarSelect.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//
//				final Intent i = new Intent(v.getContext(), ImageSelector.class);
//				startActivityForResult(i,12);
//			}
//		});
//        
//        
//        DataHolder.db.close();
//        verbindungen.close();
	}
//	public void saveUser(){
//
//		DataHolder.db.open();
//		DataHolder.db.updateUser(bdl.getInt("id"), nameEdit.getText().toString(), avatar, verbindungSpinner.getSelectedItemPosition());
//		DataHolder.db.close();
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode == RESULT_OK){
//			Bundle bundle = data.getExtras();
//			avatar = bundle.getString("avatar");
//			onCreate(instanceState);
//		}
//	}
//	
//	public void showAlertDialogNoName(){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Zu peinlich 'nen Namen anzugeben?")
//		       .setCancelable(true)
//		       .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		           	dialog.cancel();		           }
//		       });
//		AlertDialog alert = builder.create();
//		alert.show();
//		}
//	
//	public void showAlertDialogLongName(){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Laberkaraffe als Namen? W�hl was ordentliches!")
//		       .setCancelable(true)
//		       .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		           	dialog.cancel();		           }
//		       });
//		AlertDialog alert = builder.create();
//		alert.show();
//		}
//	
}
