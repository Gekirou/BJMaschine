package de.cfc.bjm.data;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.Entry;

public class TransferDBWorker extends AsyncTask<Void, Integer, Boolean>{

	Activity activity;
	BJMMySQLDb remoteInstance;
	BJMMySQLDb localInstance;
	
	int totalRecordCount;
	int currentRecordCount;
	
	
	public TransferDBWorker(Activity activity) {
		this.activity = activity;
		remoteInstance = BJMMySQLDb.getInstance(activity);
		localInstance = BJMMySQLDb.getLocalInstance(activity);
	}
	
	
	@Override
	protected Boolean doInBackground(Void... params) {
		if(remoteInstance.getMode() == BJMMySQLDb.MODE_LOCAL||
				remoteInstance.getMode() == BJMMySQLDb.MODE_NO_CONNECTION)
				return false;
		
		totalRecordCount = remoteInstance.getAllRecordCounts();
//		activity.runOnUiThread(new Runnable() {	
//			@Override
//			public void run() {
//				panel.getProgressBar().setMaximum(totalRecordCount);
//			}
//		});
		
		currentRecordCount=0;
		try {
			localInstance.clearAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void copyEntries(){
		List<Entry> entries = remoteInstance.getEntries();
		for(Entry entry: entries){
			localInstance.insertEntry(entry);
			currentRecordCount++;
			updateProgress();
		}
	}
	
	private void copyUsers(){
		
	}
	

	
	private void updateProgress(){
		activity.runOnUiThread(new Runnable() {
			public void run() {
//				panel.getProgressBar().setValue(currentRecordCount);
//				int percent = (int)(panel.getProgressBar().getPercentComplete()*100);
//				panel.getPercentage().setText(percent+"%");
//				panel.getCount().setText(currentRecordCount+"/"+totalRecordCount);
			}
		});
	}
}
