package com.softcraft.calendar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

/**
 * Created by Softcraft Systems on 12/16/2016.
 */
public class CustomGrid extends BaseAdapter
{
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
boolean selected;
    private int selectedPos = -1;
    LayoutInflater inflater;
    public SparseBooleanArray mSelectedItemsIds;
    public CustomGrid(Context c,String[] web,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        mSelectedItemsIds = new SparseBooleanArray();

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public void setSelectedPosition(int pos){
        selectedPos = pos;
        notifyDataSetChanged();
    }
    public int getSelectedPosition(){
        return selectedPos;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        ImageView imageView ;
        TextView textView ;
         inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = new View(mContext);
            grid = inflater.inflate(R.layout.single_grid, null);
             textView = (TextView) grid.findViewById(R.id.grid_text);
             imageView = (ImageView)grid.findViewById(R.id.grid_image);

        if(MiddlewareInterface.bRendering)
        {
            textView.setText(web[position]);
            textView.setTypeface(Typeface.DEFAULT);
        }
        else
        {
            textView.setText(UnicodeUtil.unicode2tsc(web[position]));
            textView.setTypeface(MiddlewareInterface.tf_mylai);
        }
        imageView.setImageResource(Imageid[position]);

        if(selectedPos == position)
        {
            textView.setBackgroundColor(Color.parseColor("#2E9AFE"));
            notifyDataSetChanged();
        }

        else if(mSelectedItemsIds.get(position))
        {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.LINEAR_TEXT_FLAG);
            textView.setTextColor(Color.parseColor("#808080"));  //#57130c
            imageView.setColorFilter(Color.GRAY);
            notifyDataSetChanged();
        }
        else
        {

        }
        return grid;
    }
    public void toggleSelection(int position)
    {
        selectView(position, !mSelectedItemsIds.get(position));

    }
    private void selectView(int position, boolean b)
    {
        if (b)
        {
            mSelectedItemsIds.put(position, b);
            notifyDataSetChanged();}
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }
    public void removeSelection()
    {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
}