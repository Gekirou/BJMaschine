package de.cfc.bjm;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import de.cfc.bjm.adapter.BLCursorAdapter;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMDatabase;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.Entry;
import de.cfc.bjm.model.Record;

public class Bestenliste extends ListActivity{
	SQLiteDatabase connDB;
	public int position=1;
	Bundle savedState;
	int volume;
	int liquid;
	int anzahl;
	int tempid;
	BJMMySQLDb db;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.savedState = savedInstanceState;
	Bundle bdl = getIntent().getExtras();
	db = BJMMySQLDb.getInstance(this);
	String mode = bdl.getString("mode");
	if(mode.equals("saufmode")){
		volume = DataHolder.volume;
		liquid = DataHolder.liquid;
		anzahl = DataHolder.anzahl;
		getList();
		if(position>10){
			getListView().smoothScrollToPosition(13);
		}
	}else{
		volume = bdl.getInt("volume");
		liquid = bdl.getInt("liquid");
		anzahl = bdl.getInt("anzahl");
		getListWOR();
	}
	getListView().setOnItemClickListener(oc);
}



public void getList(){
	
	List<Record> entries = db.getRecord(volume, liquid, anzahl);

	calculateRank(entries);
	List<Record> bestEntries = db.getRecordWithLimit(volume, liquid, anzahl,position,15);

	BLCursorAdapter mAdapter = new BLCursorAdapter(this, bestEntries, position, true);
	setListAdapter(mAdapter);
	}

public void getListWOR(){
	List<Record> records = db.getRecord(volume, liquid, anzahl);
	BLCursorAdapter mAdapter = new BLCursorAdapter(this,records, 1, false);
	setListAdapter(mAdapter);
	}




public void calculateRank(List<Record> entries){
    for (int i = 0; i < entries.size(); i++) {
        if(DataHolder.lid==entries.get(i).id){
        	this.position = i;
        	break;
        }
    }
}

private OnItemClickListener oc = new OnItemClickListener(){

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
		Intent details = new Intent(arg1.getContext(),SaufDetail.class);
		details.putExtra("id", id);
		startActivityForResult(details,1);	
	}
	
};
protected void onActivityResult(int requestCode, int resultCode, Intent data){
	super.onActivityResult(requestCode, resultCode, data);
	onCreate(savedState);
}

//public OnCreateContextMenuListener contextmenu= new OnCreateContextMenuListener(){
//
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		TextView rank=(TextView)v.findViewById(R.id.rangzahl);
//		tempid = Integer.parseInt(rank.getText().toString());
//		
//		menu.setHeaderTitle("Bierjunge Nr. "+tempid);
//		menu.add(0, R.id.uc_delete, 0, "Löschen");
//	}
//};

//public boolean onContextItemSelected(MenuItem item) {
//	 
//	   switch (item.getItemId()) {
//	   case R.id.uc_delete:
//		   deleteListEntry(tempid);
//		   onCreate(savedState);
//		   break;
//	   default:
//		   return super.onContextItemSelected(item);
//
//	   }
//
//return super.onContextItemSelected(item);
//}


	@Override
	public void onBackPressed() {
		finish();
	}
}
