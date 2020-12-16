package com.softcraft.calendar.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.softcraft.calendar.R;

public class GowripanchagamFragment extends Fragment {

    public int crntGpos;
    private static final String ARG_POSITION = "param1";
    public static ArrayList<ArrayList<String>> gowriPanchagamList;
    public static int tabLayoutPos = 0;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(R.layout.gowripanchagam_fragment_layout, container, false);
        try {
            recyclerView =  view.findViewById(R.id.recyclerviewPeyarchiPalan);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            ArrayList<String> gowripanchagamList = gowriPanchagamList.get(crntGpos);
            String strTime = gowripanchagamList.get(0);
            String strMrng = gowripanchagamList.get(1);
            String strEve = gowripanchagamList.get(2);

            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> mrngList = new ArrayList<>();
            ArrayList<String> eveList = new ArrayList<>();

            String strTimearr[] = strTime.split("\\$");
            String strMrngarr[] = strMrng.split("\\$");
            String strEvearr[] = strEve.split("\\$");

            for(int i=0;i<strTimearr.length;i++){
                timeList.add(strTimearr[i]);
                mrngList.add(strMrngarr[i]);
                eveList.add(strEvearr[i]);
            }

            recyclerView.setAdapter(new GowripanchagamFragmentAdapter(getActivity(), timeList,mrngList,eveList));
        } catch (Exception e) {
            String strEx = e.getMessage();
            Log.d(strEx,strEx);
            e.printStackTrace();
        }
        return view;
    }

    public static GowripanchagamFragment newInstance(int position, ArrayList<ArrayList<String>> gowriPanchagamList1) {
        try {
            gowriPanchagamList = gowriPanchagamList1;
            tabLayoutPos = position;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        GowripanchagamFragment fragment = new GowripanchagamFragment();
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

    public class GowripanchagamFragmentAdapter extends RecyclerView.Adapter<GowripanchagamFragmentAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private int countItem;
        public Context mContext;
        ArrayList<String> gowripnachagamList;
        ArrayList<String> timeList, mrngList, eveList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView neramTV,pakalTV,nightTV;
            TableRow tablerow;
            public ViewHolder(View view) {
                super(view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + neramTV.getText();
            }
        }

        public GowripanchagamFragmentAdapter(Context context, ArrayList<String> timeList,ArrayList<String> mrngList,ArrayList<String> eveList) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                this.timeList = timeList;
                this.mrngList = mrngList;
                this.eveList = eveList;
                countItem = eveList.size();
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public GowripanchagamFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gowripanchagam_adapter_layout, parent, false);
            ViewHolder vh = new ViewHolder(view);
            vh.tablerow =  view.findViewById(R.id.tablerow);
            vh.neramTV =  view.findViewById(R.id.neramTV);
            vh.pakalTV =  view.findViewById(R.id.pakalTV);
            vh.nightTV =  view.findViewById(R.id.nightTV);
            view.setBackgroundResource(mBackground);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int itemPosition) {
            try {

                if(itemPosition == 0)
                    holder.tablerow.setVisibility(View.VISIBLE);
                else
                    holder.tablerow.setVisibility(View.GONE);

                holder.neramTV.setText(timeList.get(itemPosition));
                holder.pakalTV.setText(mrngList.get(itemPosition));
                holder.nightTV.setText(eveList.get(itemPosition));

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
