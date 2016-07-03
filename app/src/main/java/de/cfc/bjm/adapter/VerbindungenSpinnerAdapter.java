package de.cfc.bjm.adapter;

import android.R;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import de.cfc.bjm.model.Verbindung;

public class VerbindungenSpinnerAdapter implements SpinnerAdapter{
	Context ctx;
	List<Verbindung> verbindungen;


	public VerbindungenSpinnerAdapter(Context ctx, List<Verbindung> verbindungen) {
		this.ctx = ctx;
		this.verbindungen = verbindungen;
	}
	
	public int getCount() {
		return verbindungen.size();
	}

	public Verbindung getItem(int position) {
		return verbindungen.get(position);
	}

	public long getItemId(int position) {
		return verbindungen.get(position).id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = LayoutInflater.from(ctx);
			view = inflater.inflate(R.layout.simple_spinner_item, null);
		}
		TextView textview = (TextView) view.findViewById(R.id.text1);
		textview.setText(verbindungen.get(position).name);
		return view;
	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isEmpty() {
		return verbindungen.size() == 0;
	}

	public void registerDataSetObserver(DataSetObserver observer) {
		
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = LayoutInflater.from(ctx);
			view = inflater.inflate(R.layout.simple_spinner_dropdown_item, null);
		}
		TextView textview = (TextView) view.findViewById(R.id.text1);
		textview.setText(verbindungen.get(position).name);
		return view;
	}

	public int getViewTypeCount() {
		return 1;
	}
}
