package com.softcraft.calendar.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcraft.calendar.Activity.MonthActivity;
import com.softcraft.calendar.Adapter.TestRecyclerViewAdapter;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class RecyclerViewFragment extends Fragment {
    Context context;
    static final boolean GRID_LAYOUT = true;
    private static final int ITEM_COUNT = 24;
    private int Position;
    DataBaseHelper db;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<String> mContentItems = new ArrayList<>();
    public GregorianCalendar month;
    public static String ARG_POSITION;

    public interface CallViewPager {
        void yourDesiredMethod(int i);
    }

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance(int pos) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                Position = getArguments().getInt(ARG_POSITION);
            }

        } catch (Exception ignored) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Check", "Check" + Position);

        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        try {

            try {
                db = new DataBaseHelper(context);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

            if (Position == 0)
                month = new GregorianCalendar(2020, Calendar.JANUARY, 1);
            else if (Position == 1)
                month = new GregorianCalendar(2020, Calendar.FEBRUARY, 1);
            else if (Position == 2)
                month = new GregorianCalendar(2020, Calendar.MARCH, 1);
            else if (Position == 3)
                month = new GregorianCalendar(2020, Calendar.APRIL, 1);
            else if (Position == 4)
                month = new GregorianCalendar(2020, Calendar.MAY, 1);
            else if (Position == 5)
                month = new GregorianCalendar(2020, Calendar.JUNE, 1);
            else if (Position == 6)
                month = new GregorianCalendar(2020, Calendar.JULY, 1);
            else if (Position == 7)
                month = new GregorianCalendar(2020, Calendar.AUGUST, 1);
            else if (Position == 8)
                month = new GregorianCalendar(2020, Calendar.SEPTEMBER, 1);
            else if (Position == 9)
                month = new GregorianCalendar(2020, Calendar.OCTOBER, 1);
            else if (Position == 10)
                month = new GregorianCalendar(2020, Calendar.NOVEMBER, 1);
            else if (Position == 11)
                month = new GregorianCalendar(2020, Calendar.DECEMBER, 1);
            else if (Position == 12)
                month = new GregorianCalendar(2021, Calendar.JANUARY, 1);
            else if (Position == 13)
                month = new GregorianCalendar(2021, Calendar.FEBRUARY, 1);
            else if (Position == 14)
                month = new GregorianCalendar(2021, Calendar.MARCH, 1);
            else if (Position == 15)
                month = new GregorianCalendar(2021, Calendar.APRIL, 1);
            else if (Position == 16)
                month = new GregorianCalendar(2021, Calendar.MAY, 1);
            else if (Position == 17)
                month = new GregorianCalendar(2021, Calendar.JUNE, 1);
            else if (Position == 18)
                month = new GregorianCalendar(2021, Calendar.JULY, 1);
            else if (Position == 19)
                month = new GregorianCalendar(2021, Calendar.AUGUST, 1);
            else if (Position == 20)
                month = new GregorianCalendar(2021, Calendar.SEPTEMBER, 1);
            else if (Position == 21)
                month = new GregorianCalendar(2021, Calendar.OCTOBER, 1);
            else if (Position == 22)
                month = new GregorianCalendar(2021, Calendar.NOVEMBER, 1);
            else if (Position == 23)
                month = new GregorianCalendar(2021, Calendar.DECEMBER, 1);

            RecyclerView.LayoutManager layoutManager;

            if (GRID_LAYOUT) {
                layoutManager = new GridLayoutManager(getActivity(), 7);
            } else {
                layoutManager = new LinearLayoutManager(getActivity());
            }
            mRecyclerView.setLayoutManager(layoutManager);
            //Use this now
            MonthActivity monthActivity = (MonthActivity) getActivity();
            ViewPager viewPager = monthActivity.getViewpager();
            mAdapter = new TestRecyclerViewAdapter(getActivity(), mContentItems, month, Position, viewPager);
            mRecyclerView.setAdapter(mAdapter);
            {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                    mContentItems.add(String.valueOf(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}



