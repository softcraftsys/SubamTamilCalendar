package com.softcraft.calendar.MarriageMatch;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.softcraft.calendar.R;

import java.util.ArrayList;

public class NatchathiramDropDownAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private ArrayList data;
    LayoutInflater inflater;
    public Resources res;
    NatchathiramVO tempValues = null;

    public NatchathiramDropDownAdapter(Activity activity, int i, ArrayList arrayList, Resources resources) {
        super(activity, i, arrayList);
        this.activity = activity;
        this.data = arrayList;
        this.res = resources;
        this.inflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getCustomCheckedTextView(i, view, viewGroup);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getCustomTextView(i, view, viewGroup);
    }

    public View getCustomCheckedTextView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.spinner_natchathiram_rows, viewGroup, false);
        this.tempValues = null;
        this.tempValues = (NatchathiramVO) this.data.get(i);
        ((CheckedTextView) view.findViewById(R.id.natchathiramDropDown)).setText(this.tempValues.getNatchathiramName());
        return view;
    }

    public View getCustomTextView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.spinner_custom_natchathiram_text_view, viewGroup, false);
        this.tempValues = null;
        this.tempValues = (NatchathiramVO) this.data.get(i);
        ((TextView) view.findViewById(R.id.spinnerNatchathiramTextView)).setText(this.tempValues.getNatchathiramName());
        return view;
    }
}
