package com.softcraft.calendar.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.softcraft.calendar.R;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    public int crntGpos;
    private static final String ARG_POSITION = "param1";
    public static ArrayList<List<String>> dateList;
    public static ArrayList<List<String>> listH;
    public static  ArrayList<ArrayList<List<String>>> listH3,monthLists1;
    public static int tabLayoutPos = 0;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(R.layout.gowripanchagam_fragment_layout, container, false);
        try {
            recyclerView = view.findViewById(R.id.recyclerviewPeyarchiPalan);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
//            List<String> listH1 = listH.get(crntGpos);
//            List<String> dateList1 = dateList.get(crntGpos);
            ArrayList<List<String>> listH4 = listH3.get(crntGpos);
            ArrayList<List<String>> monthLists3 = monthLists1.get(crntGpos);

//            for(int i=0;i<dateList1.size();i++){
//                if(crntGpos == 0){
//                    listH1.add(listH.get(crntGpos).get(i));
//                }
//                else if(crntGpos == 1){
//                    listH1.add(listH.get(crntGpos).get(i));
//                }
//            }


            recyclerView.setAdapter(new HistoryFragmentAdapter(getActivity(), monthLists3, listH4));
        } catch (Exception e) {
            String strEx = e.getMessage();
            Log.d(strEx, strEx);
            e.printStackTrace();
        }
        return view;
    }

    public static HistoryFragment newInstance(int position, ArrayList<List<String>> dateList1, ArrayList<List<String>> listH1, ArrayList<ArrayList<List<String>>> listH2, ArrayList<ArrayList<List<String>>> monthLists2) {
        try {
            dateList = dateList1;
            listH = listH1;
            listH3 = listH2;
            monthLists1 = monthLists2;
            tabLayoutPos = position;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                crntGpos = getArguments().getInt(ARG_POSITION);
            }
        } catch (Exception ignored) {
        }
    }

    public class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private int countItem;
        public Context mContext;
        ArrayList<List<String>> listH,dateList;


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView headingTV, dayTV1, dayTV2,dayTV3,dayTV4,dayTV5;


            public ViewHolder(View view) {
                super(view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + headingTV.getText();
            }
        }

        public HistoryFragmentAdapter(Context context, ArrayList<List<String>> dateList,ArrayList<List<String>> listH) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                this.dateList = dateList;
                this.listH = listH;
                countItem = dateList.size();
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public HistoryFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyfragment_layout, parent, false);
            ViewHolder vh = new ViewHolder(view);
            vh.headingTV = view.findViewById(R.id.headingTV);
            vh.dayTV1 = view.findViewById(R.id.dayTV1);
            vh.dayTV2 = view.findViewById(R.id.dayTV2);
            vh.dayTV3 = view.findViewById(R.id.dayTV3);
            vh.dayTV4 = view.findViewById(R.id.dayTV4);
            vh.dayTV5 = view.findViewById(R.id.dayTV5);
            view.setBackgroundResource(mBackground);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int itemPosition) {
            try {
                List<String> listHistory = listH.get(itemPosition);
                holder.headingTV.setText(dateList.get(0).get(itemPosition));
                holder.dayTV1.setText(listHistory.get(0));
                holder.dayTV2.setText(listHistory.get(2));
                holder.dayTV3.setText(listHistory.get(3));
                holder.dayTV4.setText(listHistory.get(4));
                holder.dayTV5.setText(listHistory.get(5));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemViewType(int position) {
            return position % 2 * 2;
        }

        @Override
        public int getItemCount() {
            return countItem;
        }

    }
}
