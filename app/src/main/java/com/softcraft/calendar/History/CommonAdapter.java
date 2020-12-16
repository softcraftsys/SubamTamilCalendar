package com.softcraft.calendar.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softcraft.calendar.R;

import java.util.List;

public class CommonAdapter extends BaseAdapter {
    private TamilCalendarApplication mApplication;
    private Context mContext;
    private List<String> mData;
    private int[] mHeaderIndices;

    public long getItemId(int i) {
        return (long) i;
    }

    public CommonAdapter(Context context, TamilCalendarApplication tamilCalendarApplication, List<String> list, int[] iArr) {
        this.mContext = context;
        this.mApplication = tamilCalendarApplication;
        this.mData = list;
        this.mHeaderIndices = iArr;
    }

    public int getCount() {
        return this.mData.size();
    }

    public Object getItem(int i) {
        return this.mData.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (isHeader(i)) {
            view = (LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.fragment_common_head, viewGroup, false);
            textView = (TextView) view.findViewById(R.id.siddha_head);
        } else {
            view = (LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.fragment_common_body, viewGroup, false);
            textView = (TextView) view.findViewById(R.id.siddha_tips);
        }
        textView.setText((CharSequence) this.mData.get(i));
        return view;
    }

    private boolean isHeader(int i) {
        for (int i2 : this.mHeaderIndices) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
