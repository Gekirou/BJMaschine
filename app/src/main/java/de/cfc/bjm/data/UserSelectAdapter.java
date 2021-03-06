package de.cfc.bjm.data;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleExpandableListAdapter;

public class UserSelectAdapter extends SimpleExpandableListAdapter{
    @SuppressWarnings("unused")
	private List<? extends List<? extends Map<String, ?>>> mChildData;
    @SuppressWarnings("unused")
	private String[] mChildFrom;
    @SuppressWarnings("unused")
	private int[] mChildTo;
    Context myContext;
	
	public UserSelectAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
        mChildData = childData;
        mChildFrom = childFrom;
        mChildTo = childTo;
        myContext = context;
	}
	
	public long getChildId(int groupPosition, int childPosition) {
		String id = (String) mChildData.get(groupPosition).get(childPosition).get("id");
		return Long.parseLong(id);
	}

}
	


