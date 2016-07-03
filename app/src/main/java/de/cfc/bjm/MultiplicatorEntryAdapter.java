package de.cfc.bjm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Can on 02/07/16.
 */

public class MultiplicatorEntryAdapter extends BaseAdapter {

    List<MultiplicatorEntry> entries;
    Context context;

    public MultiplicatorEntryAdapter(final Context context, final List<MultiplicatorEntry> entries) {
        this.entries = entries;
        this.context = context;
    }

    public int getCount() {
        return entries.size();
    }

    public MultiplicatorEntry getItem(final int position) {
        return entries.get(position);
    }

    public long getItemId(final int position) {
        return entries.get(position).hashCode();
    }

    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View entryView = convertView;

        if (entryView == null) {
            entryView = LayoutInflater.from(context).inflate(R.layout.xp_entry, parent, false);
        }

        MultiplicatorEntry entry = getItem(position);

        TextView entryName = (TextView) entryView.findViewById(R.id.entryName);
        TextView entryValue = (TextView) entryView.findViewById(R.id.entryValue);

        entryName.setText(entry.getMultiplicatorName());
        String multiplicator = entry.getMultiplicator();
        entryValue.setText(multiplicator);

        return entryView;
    }
}
