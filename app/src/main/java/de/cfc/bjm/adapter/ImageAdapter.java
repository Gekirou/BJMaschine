package de.cfc.bjm.adapter;


import de.cfc.bjm.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int width;

    public ImageAdapter(Context c) {
        mContext = c;
        WindowManager manager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        width = manager.getDefaultDisplay().getWidth();
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams((int)(width/2.5), (int) (width/2.5)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

    	imageView.setImageResource(mThumbIds[position]);
    	return imageView;
    }
    
    public static final Integer[] mThumbIds = {
    		R.drawable.conan,
    		R.drawable.bier,
    		R.drawable.corpsstudent,
    		R.drawable.anonym,
    		R.drawable.hendrix,
    		R.drawable.spongebob
    	};

}