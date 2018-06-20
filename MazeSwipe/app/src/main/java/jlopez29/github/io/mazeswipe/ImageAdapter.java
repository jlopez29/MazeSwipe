package jlopez29.github.io.mazeswipe;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static jlopez29.github.io.mazeswipe.GameScreen.mazeSize;

public class ImageAdapter extends BaseAdapter {
    ArrayList<Integer> imageList;
    Context context;
    public ImageAdapter(Context ctx,ArrayList<Integer> iconImages ) {
        context = ctx;
        imageList = iconImages;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        ImageButton iconImage;
    }
    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        ImageView iv = new ImageView(context);
        iv.setImageDrawable(context.getResources().getDrawable(R.drawable.circle,null));
        return iv;
    }
}
