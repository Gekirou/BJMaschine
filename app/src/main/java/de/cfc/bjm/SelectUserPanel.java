package de.cfc.bjm;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.data.UserSelectAdapter;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.User;
import de.cfc.bjm.model.Verbindung;

public class SelectUserPanel extends ExpandableListActivity{

	int position=0;
	static int userid;
	static int verbid;
	static ExpandableListView lv;
	Bundle instance;
	BJMMySQLDb db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = savedInstanceState;
		db = BJMMySQLDb.getInstance(this);

		getUser();
		Bundle bdl = getIntent().getExtras();
		position = bdl.getInt("position");
		
		lv = getExpandableListView();
		lv.setOnChildClickListener(childListener);
//		lv.setOnCreateContextMenuListener(contextmenu);
		
	}
	
	public void getUser(){
		List<Verbindung> verbindungen = db.getVerbindungList();
		Map<String, String> curGroupMap;
		List<Map<String,String>> verbindungsListe = new ArrayList<Map<String,String>>();
		
		for(Verbindung verbindung : verbindungen){
			curGroupMap = (HashMap<String, String>) new HashMap<String, String>();
			curGroupMap.put("name", verbindung.name);
			curGroupMap.put("id", verbindung.id+"");

			verbindungsListe.add(curGroupMap);
//			Log.d("verbindung while 1", "Name: "+verbindung.getString(1));
		}
		
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        List<Map<String, String>> children;
        Map<String, String> curChildMap;
        
		for(Verbindung verbindung : verbindungen){
			children = new ArrayList<Map<String,String>>();

			List<User> users = db.getUsersFromVerbindung(verbindung.id);
			for(User user : users){

				curChildMap = new HashMap<String, String>();
				curChildMap.put("name", user.name);
				curChildMap.put("id", user.id+"");
				children.add(curChildMap);
			}
			childData.add(children);
		}
		

				UserSelectAdapter mAdapter = new UserSelectAdapter(this, verbindungsListe, R.layout.us_groupview, new String[] {"name"}, new int[] {R.id.usg_name}, 
						childData, R.layout.us_childview, new String[]{"name"}, new int[]{R.id.usc_name});
		setListAdapter(mAdapter);
		}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onCreate(instance);
	};

	OnChildClickListener childListener = new OnChildClickListener() {
		
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			if(position==0){
			DataHolder.usr1ID = (int) id;
		}else{
			DataHolder.usr2ID = (int) id;
		}
			Log.d("childListener", "Setting ID to :"+id);
			finish();
			
			return true;
			
		}
	};
//	public static OnCreateContextMenuListener contextmenu= new OnCreateContextMenuListener(){
//
//		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//			ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
//			
//			int type = ExpandableListView.getPackedPositionType(info.packedPosition);
//			int group = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//			int child = ExpandableListView.getPackedPositionChild(info.packedPosition);
//			
//			if(type == 1){
//				List<User> users = db.getUsersFromVerbindung(group);
//				cur.move(child+1);
//				menu.setHeaderTitle(cur.getString(1));
//				menu.add(0, R.id.uc_usrdetail, 0, "Nutzerdetails");
//				menu.add(0, R.id.uc_usredit,0, "Bearbeiten");
//				menu.add(0, R.id.uc_usrdelete, 0, "L?schen");
//				userid = cur.getInt(0);
//			}else{
//				if(type == 0){
//					Cursor cur = DataHolder.db.getVerbindungen();
//					cur.move(group);
//					menu.setHeaderTitle(cur.getString(1));
//					menu.add(0, R.id.uc_verbdelete, 0, "L?schen");
//					verbid = cur.getInt(0);
//				}
//			}
//		}
//	};
//	
//	 public boolean onContextItemSelected(MenuItem item) {
//		 
//		   switch (item.getItemId()) {
//		   case R.id.uc_usredit:
//			   	Intent i = new Intent(this,EditUserPanel.class);
//			   	i.putExtra("id", userid);
//			   	startActivityForResult(i,0);
//			   break;
//		   case R.id.uc_usrdelete:
//			   showAlertDialogUserSure();
//			   break;
//		   case R.id.uc_usrdetail:
//			   final Intent in = new Intent(this, UserDetail.class);
//			   in.putExtra("id", userid);
//			   startActivityForResult(in, 0);
//			   break;
//		   case R.id.uc_verbdelete:
//			   showAlertDialogVerbindungSure();
//			   break;
//		   default:
//			   return super.onContextItemSelected(item);
//		   }
//	   
//	   return super.onContextItemSelected(item);
//	 }
	 
//		public void showAlertDialogUserSure(){
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage("Bist du dir sicher, dass du diesen Nutzer l?schen m?chtest?")
//			       .setCancelable(false)
//			       .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//						  DataHolder.db.deleteUser(userid);	
//						  onCreate(instance);}
//			       });
//			builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//						  dialog.cancel();	           }
//			       });
//			AlertDialog alert = builder.create();
//			alert.show();
//			}
//		
//		public void showAlertDialogVerbindungSure(){
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage("Bist du dir sicher, dass du diese Verbindung l?schen m?chtest? S?mtliche Nutzer darin gehen damit verloren!")
//			       .setCancelable(false)
//			       .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//						   DataHolder.db.deleteVerbindung(verbid);
//						   onCreate(instance);}
//			       });
//			builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//					  dialog.cancel();	           }
//		       });
//			AlertDialog alert = builder.create();
//			alert.show();
//			}
	
}
