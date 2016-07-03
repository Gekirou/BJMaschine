package de.cfc.bjm.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cfc.bjm.R;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.model.Record;

public class BLCursorAdapter extends BaseAdapter {
	
	LayoutInflater inflater ;
	int pos=1;
	boolean saufmode;
	List<Record> entries;

	
	public BLCursorAdapter(Context context,List<Record> entries, int position, boolean saufmode) {
		inflater = LayoutInflater.from(context);
		this.entries = entries;
		this.pos = position;
		this.saufmode = saufmode;
	}

	public int getCount() {
		return entries.size();
	}

	public Record getItem(int position) {
		return entries.get(position);
	}

	public long getItemId(int position) {
		return entries.get(position).id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			view = inflater.inflate(R.layout.bestlist_entry, null);
		}
		
		int offset;
		TextView lid = (TextView) view.findViewById(R.id.rangzahl);
//		DataHolder.rankIdMap.put(cursor.getPosition()+1, cursor.getInt(0));

		if(pos < 15){
			offset = pos-1;
		}else{
			offset = 15;
		}
//		lid.setText((cursor.getPosition() + position - offset)+"");
		lid.setText((pos - offset + position)+"");
		
		TextView name = (TextView) view.findViewById(R.id.name_entry);
		name.setText(entries.get(position).username);
		
		TextView dur = (TextView) view.findViewById(R.id.duration_entry);
		
		String mseconds;
		int sec = (int) (entries.get(position).duration/1000);
		int msec = (int) ((entries.get(position).duration)-(sec*1000));
		if(msec<10){
		   mseconds ="00"+msec;
		}else{
			if(msec<100){
			   mseconds="0"+msec;
			} else{
				mseconds = ""+msec;
			}
		}
		
		dur.setText(sec+"."+mseconds);
		if(saufmode){
			LinearLayout bestLinLay = (LinearLayout) view.findViewById(R.id.bestlistlinlay);
			Log.d("BLCursorAdapter", "id: "+entries.get(position).id+" LID: "+DataHolder.lid+" 2nd LID: "+DataHolder.lid2nd);
		if(entries.get(position).id== DataHolder.lid ){
			bestLinLay.setBackgroundResource(R.color.green);
		}else{ if(entries.get(position).id == DataHolder.lid2nd){
			bestLinLay.setBackgroundResource(R.color.light_grey);
		}else{
			bestLinLay.setBackgroundResource(R.color.white);
		}
		}}
		
		return view;
	}
	

}
