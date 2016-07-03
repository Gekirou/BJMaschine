package de.cfc.bjm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;

public class NewVerbPanel extends Activity{
	Spinner spinner;
	Button saveNewVerbindung;
	EditText nameEdit;
	BJMMySQLDb db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = BJMMySQLDb.getInstance(this);
		setContentView(R.layout.newverbindung);
		nameEdit = (EditText) findViewById(R.id.nvname);  
        saveNewVerbindung = (Button) findViewById(R.id.nvsave);
        
        saveNewVerbindung.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(nameEdit.getText().length()>18){
					showAlertDialogLongName();
				}else{ if(nameEdit.getText().length()==0){
					showAlertDialogNoName();
				} else{
					saveVerbindung();
					finish();
				}
				}
			}
		});

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
		builder.setMessage("Laberkaraffe als Namen? Wähl was ordentliches!")
		       .setCancelable(true)
		       .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           	dialog.cancel();		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		}
	
	public void saveVerbindung(){
		db.insertVerbindung(nameEdit.getText().toString());
	}
}
