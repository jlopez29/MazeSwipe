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
import static jlopez29.github.io.mazeswipe.GameScreen.visitedDrawables;
import static jlopez29.github.io.mazeswipe.GameScreen.x;
import static jlopez29.github.io.mazeswipe.GameScreen.y;

public class ImageAdapter extends BaseAdapter {
    Integer[] imageList;
    Context context;
    public ImageAdapter(Context ctx,Integer[] iconImages ) {
        context = ctx;
        imageList = iconImages;
    }

    @Override
    public int getCount() {
        return mazeSize*mazeSize;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        ImageButton iconImage;
    }
    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        Log.e("Pos","is " + position);

        ImageView iv = new ImageView(context);
//        if(visited[position] == 1)
        iv.setImageDrawable(context.getResources().getDrawable(visitedDrawables[position],null));
//        else if(visited[position] == 0)
//            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.circle,null));
//        else
//            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_curr,null));

        return iv;
    }

}
