package com.softcraft.calendar.Fragment;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.softcraft.calendar.Activity.DevotionalSongsActivity;
import com.softcraft.calendar.Activity.SingersCategorySongsActivity;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class SingersCategoryFragment extends Fragment {
    private RecyclerView recyclerListView;
    private SingersCategoryFragmentAdapter listAdapter;
    private HashMap<String, String> resultp, resultp1;
    Context context;
    Boolean emptyArrCheck = false;
    public ArrayList<HashMap<String, String>> singerArraylist, songArrayList, SingerSongArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_songs_layout, container, false);
        recyclerListView = (RecyclerView) rootView.findViewById(R.id.fragmentListView);

        ListOnClick();
        return rootView;
    }


    public void ListOnClick() {
        try {
            singerArraylist = new ArrayList<>();
            songArrayList = new ArrayList<>();
            SingerSongArraylist = new ArrayList<>();
            resultp = new HashMap<>();

            singerArraylist = ((DevotionalSongsActivity) getActivity()).getSingerArrayList();
            songArrayList = ((DevotionalSongsActivity) getActivity()).getSongArrayList();
            try {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.list_item_anim);
                recyclerListView.setLayoutAnimation(new LayoutAnimationController(animation));
            } catch (Exception e) {
                e.printStackTrace();
            }

            listAdapter = new SingersCategoryFragmentAdapter(getContext(), singerArraylist);
            recyclerListView.setLayoutManager(new LinearLayoutManager(recyclerListView.getContext()));
            recyclerListView.setAdapter(listAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnection(Bundle myInfo) {
        try {
//            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            if (activeNetwork != null) { // connected to the internet
//                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    gotoPlayerPage(myInfo);
//                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                    gotoPlayerPage(myInfo);
//                }
//            } else {
//                Toast toast = Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_LONG);
//                toast.show();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoPlayerPage(Bundle bundle) {
        try {
            Intent intent = new Intent(getActivity(), SingersCategorySongsActivity.class);
            intent.putExtra("singers", songArrayList);
            startActivityForResult(intent.putExtras(bundle), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SingersCategoryFragmentAdapter extends RecyclerView.Adapter<SingersCategoryFragmentAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        Context context;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> data;
        HashMap<String, String> resultp = new HashMap<>();
        Context mContext;
        View viewSub;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView SingerName, CategoryId;
            private ImageView thumbImg;
            CardView cardViewItem;

            public ViewHolder(View view) {
                super(view);
                SingerName = (TextView) itemView.findViewById(R.id.singerNameTv);
                CategoryId = (TextView) itemView.findViewById(R.id.singerId);
                thumbImg = (ImageView) itemView.findViewById(R.id.singer_img);
                cardViewItem = (CardView) itemView.findViewById(R.id.singerCategoryCardView);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + SingerName.getText();
            }
        }

        public SingersCategoryFragmentAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                data = arraylist;
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public SingersCategoryFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singers_category_fragment_adapter, parent, false);
            viewSub = view;
            view.setBackgroundResource(mBackground);
            return new SingersCategoryFragmentAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SingersCategoryFragmentAdapter.ViewHolder holder, final int itemPosition) {
            try {
                resultp = data.get(itemPosition);
                holder.CategoryId.setText(resultp.get(DevotionalSongsActivity.SingerIdDetail));

                try {
                    if (MiddlewareInterface.bRendering) {
                        holder.SingerName.setText(resultp.get(DevotionalSongsActivity.SingerNameDetail));
                        holder.SingerName.setTypeface(Typeface.DEFAULT);
                    } else {
                        holder.SingerName.setText(UnicodeUtil.unicode2tsc(resultp.get(DevotionalSongsActivity.SingerNameDetail)));
                        holder.SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Glide.with(mContext)
                            .load(resultp.get(DevotionalSongsActivity.SingerImg))
                            .into(holder.thumbImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getContext(), v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        resultp = singerArraylist.get(itemPosition);
                                        String strSingerId = resultp.get(DevotionalSongsActivity.SingerIdDetail);

                                        for (int i = 0; i < songArrayList.size(); i++) {
                                            resultp1 = songArrayList.get(i);
                                            String keySingerIds = resultp1.get(DevotionalSongsActivity.SingerId);
                                            emptyArrCheck = true;
                                            if (strSingerId.equalsIgnoreCase(keySingerIds)) {

                                                String SongImgUrl = resultp1.get(DevotionalSongsActivity.SongImgUrl);
                                                String SongTitle = resultp1.get(DevotionalSongsActivity.SongTitle);
                                                String SongUrl = resultp1.get(DevotionalSongsActivity.Songurl);
                                                String SingerName = resultp1.get(DevotionalSongsActivity.SingerName);
                                                String SongId = resultp1.get(DevotionalSongsActivity.SongID);
                                                String SingerId = resultp1.get(DevotionalSongsActivity.SingerId);
                                                String SongSize = resultp1.get(DevotionalSongsActivity.SongSize);
                                                String SongDuration = resultp1.get(DevotionalSongsActivity.SongDuration);

                                                HashMap<String, String> SingerAlbums = new HashMap<>();

                                                SingerAlbums.put("id", SongId);
                                                SingerAlbums.put("songtitle", SongTitle);
                                                SingerAlbums.put("singerid", SingerId);
                                                SingerAlbums.put("singername", SingerName);
                                                SingerAlbums.put("thumbimage", SongImgUrl);
                                                SingerAlbums.put("songfile", SongUrl);
                                                SingerAlbums.put("songfilesize", SongSize);
                                                SingerAlbums.put("songfilelength", SongDuration);

                                                SingerSongArraylist.add(SingerAlbums);
                                                emptyArrCheck = false;

                                            }
                                        }

                                        if(SingerSongArraylist.size()==0){
                                            Toast toast = Toast.makeText(getContext(), "Will be soon", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }else {
                                            String keySingerName = resultp.get(DevotionalSongsActivity.SingerNameDetail);
                                            String keySingerImgUrl = resultp.get(DevotionalSongsActivity.SingerImg);
                                            String keySingerId = resultp.get(DevotionalSongsActivity.SingerIdDetail);

                                            Bundle myInfo = new Bundle();
                                            myInfo.putString("imgUrl", keySingerImgUrl);
                                            myInfo.putString("keySingerId", keySingerId);
                                            myInfo.putString("singerName", keySingerName);
                                            checkInternetConnection(myInfo);
                                            SingerSongArraylist.clear();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}