package com.marcalbert.findingcouples;

/**
 * Created by Marc Albert Piñeiro on 05/03/2017.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds;

    // Constructor
    public ImageAdapter(Context c, int totalGridColum){
        mContext = c;
        setDefaultImages(totalGridColum);
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }

    //set default images
    public void setDefaultImages(int totalGridColum) {

        mThumbIds = new Integer[totalGridColum];
        for(int i=0;i<totalGridColum;i++){
            mThumbIds[i] = R.drawable.pic_init;
        }
    }
}