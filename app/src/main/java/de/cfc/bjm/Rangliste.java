package de.cfc.bjm;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.List;

import de.cfc.bjm.adapter.RankCursorAdapter;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.User;

public class Rangliste extends ListActivity{
	SQLiteDatabase connDB;
	public int position=1;
	Bundle savedState;
	int tempid;
	BJMMySQLDb db;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.savedState = savedInstanceState;
	db = BJMMySQLDb.getInstance(this);
	getList();
	
	getListView().setOnItemClickListener(oc);
}



public void getList(){
	List<User> users = db.getXPRecord();

	RankCursorAdapter mAdapter = new RankCursorAdapter(this, users);
	setListAdapter(mAdapter);
	}

private OnItemClickListener oc = new OnItemClickListener(){

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent details = new Intent(arg1.getContext(),UserDetail.class);
		details.putExtra("id", arg3);
		startActivityForResult(details,1);
	}
};

protected void onActivityResult(int requestCode, int resultCode, Intent data){
	super.onActivityResult(requestCode, resultCode, data);
	onCreate(savedState);
}

public OnCreateContextMenuListener contextmenu= new OnCreateContextMenuListener(){

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		TextView rank=(TextView)v.findViewById(R.id.rangzahl);
		tempid = Integer.parseInt(rank.getText().toString());
		
		menu.setHeaderTitle("Bierjunge Nr. "+tempid);
		menu.add(0, R.id.uc_delete, 0, "L?schen");
	}
};

public boolean onContextItemSelected(MenuItem item) {
	 
	   switch (item.getItemId()) {
	   case R.id.uc_delete:
//		   DataHolder.db.deleteListEntry(tempid);
		   onCreate(savedState);
		   break;
	   default:
		   return super.onContextItemSelected(item);

	   }

return super.onContextItemSelected(item);
}



}
