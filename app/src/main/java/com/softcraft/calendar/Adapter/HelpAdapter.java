package com.softcraft.calendar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softcraft.calendar.Activity.Help;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

/**
 * Created by Softcraft Systems on 10/28/2016.
 */
public class HelpAdapter extends BaseAdapter
{
    Context context;
    String[] getText;
    Integer[] getImage;
    LayoutInflater inflater;
    public HelpAdapter(Help help, String[] textId, Integer[] imageId)
    {
        this.context=help;
        this.getText=textId;
        this.getImage=imageId;
    }
    @Override
    public int getCount()
    {
        return getText.length;
    }
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = null;
        TextView tText;
        ImageView tImage;
        view = convertView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.help,  null);

        tText=(TextView)view.findViewById(R.id.help_txt);
        tImage=(ImageView)view.findViewById(R.id.help_img);
        tImage.setImageResource(getImage[position]);
        tImage.setColorFilter(Color.parseColor("#FF663f"));
        //set font style
        if(MiddlewareInterface.bRendering)
        {
            tText.setText(getText[position]);
            tText.setTypeface(Typeface.DEFAULT);
        }
        else
        {
            tText.setText(UnicodeUtil.unicode2tsc(getText[position]));
            tText.setTypeface(MiddlewareInterface.tf_mylai);
        }
        return view;
    }
}
