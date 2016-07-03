package de.cfc.bjm.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.cfc.bjm.R;
import de.cfc.bjm.model.User;

public class RankCursorAdapter extends BaseAdapter{
	
	LayoutInflater inflater ;
	int position=1;
	Context ctx;
	List<User> users;
	
	public RankCursorAdapter(Context context, List<User> users){
		this.ctx = context;
		this.users = users;
		inflater = LayoutInflater.from(context);
	}


	public int getCount() {
		return users.size();
	}


	public User getItem(int position) {
		return users.get(position);
	}


	public long getItemId(int position) {
		return users.get(position).id;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if(view == null){
			view = inflater.inflate(R.layout.bestlist_entry, null);
		}
		TextView lid = (TextView) view.findViewById(R.id.rangzahl);
		TextView name = (TextView) view.findViewById(R.id.name_entry);
		TextView dur = (TextView) view.findViewById(R.id.duration_entry);
		
		name.setText(users.get(position).name);
		lid.setText(users.get(position).id+"");
		dur.setText(users.get(position).xp+"");
		return view;
	}
	

}
