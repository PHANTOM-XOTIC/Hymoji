package com.nerbly.bemoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nerbly.bemoji.Functions.RequestNetwork;
import com.nerbly.bemoji.Functions.RequestNetworkController;
import com.nerbly.bemoji.Functions.Utils;
import com.nerbly.bemoji.UI.MainUIMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class TutorialActivity extends AppCompatActivity {
    private final Timer _timer = new Timer();
    private final ArrayList<HashMap<String, Object>> shimmerList = new ArrayList<>();
    BottomSheetBehavior sheetBehavior;
    private ArrayList<HashMap<String, Object>> tutorialList = new ArrayList<>();
    private LinearLayout bsheetbehavior;
    private LinearLayout background;
    private LinearLayout adview;
    private LinearLayout slider;
    private TextView textview3;
    private TextView textview4;
    private RecyclerView recyclerview1;
    private RecyclerView loadingRecycler;

    private RequestNetwork requestTutorial;
    private RequestNetwork.RequestListener _requestTutorial_request_listener;
    private TimerTask loadTutorialTmr;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.tutorial);
        initialize(_savedInstanceState);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        CoordinatorLayout linear1 = findViewById(R.id.linear1);
        bsheetbehavior = findViewById(R.id.bsheetbehavior);
        background = findViewById(R.id.background);
        adview = findViewById(R.id.adview);
        slider = findViewById(R.id.slider);
        textview3 = findViewById(R.id.textview3);
        textview4 = findViewById(R.id.textview4);
        recyclerview1 = findViewById(R.id.recyclerview1);
        loadingRecycler = findViewById(R.id.loadingRecycler);
        requestTutorial = new RequestNetwork(this);

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        _requestTutorial_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    tutorialList = new Gson().fromJson(response, new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    recyclerview1.setAdapter(new Recyclerview1Adapter(tutorialList));
                } catch (Exception e) {
                    Utils.showMessage(getApplicationContext(), (e.toString()));
                }
                loadTutorialTmr = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerview1.setVisibility(View.VISIBLE);
                                loadingRecycler.setVisibility(View.GONE);
                            }
                        });
                    }
                };
                _timer.schedule(loadTutorialTmr, 1000);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onErrorResponse(String tag, String message) {

            }
        };
    }

    private void initializeLogic() {
        _LOGIC_FRONTEND();
        _LOGIC_BACKEND();
    }

    @Override
    public void onBackPressed() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void _LOGIC_BACKEND() {
        sheetBehavior = BottomSheetBehavior.from(bsheetbehavior);
        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        loadingRecycler.setLayoutManager(new LinearLayoutManager(this));
        requestTutorial.startRequestNetwork(RequestNetworkController.GET, "https://nerbly.com/bemoji/tutorial.json", "", _requestTutorial_request_listener);
        for (int _repeat14 = 0; _repeat14 < 20; _repeat14++) {
            HashMap<String, Object> shimmerMap = new HashMap<>();
            shimmerMap.put("key", "value");
            shimmerList.add(shimmerMap);
        }
        loadingRecycler.setAdapter(new LoadingRecyclerAdapter(shimmerList));
        _BottomSheetBehaviorListener();
        recyclerview1.setHasFixedSize(true);
        loadingRecycler.setHasFixedSize(true);
        AudienceNetworkAds.initialize(this);

        AdView bannerAd = new AdView(this, "3773974092696684_3774022966025130", AdSize.BANNER_HEIGHT_50);

        adview.addView(bannerAd);

        bannerAd.loadAd();
    }


    public void _LOGIC_FRONTEND() {
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/whitney.ttf"), Typeface.BOLD);
        textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/whitney.ttf"), Typeface.NORMAL);
        MainUIMethods.advancedCorners(background, "#FFFFFF", 40, 40, 0, 0);

        MainUIMethods.setViewRadius(slider, 90, "#E0E0E0");

        MainUIMethods.DARK_ICONS(this);

        MainUIMethods.transparentStatusBar(this);
    }


    public void _BottomSheetBehaviorListener() {
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    finish();
                } else {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        MainUIMethods.shadAnim(background, "elevation", 20, 200);
                        MainUIMethods.shadAnim(slider, "translationY", 0, 200);
                        MainUIMethods.shadAnim(slider, "alpha", 1, 200);
                        slider.setVisibility(View.VISIBLE);
                    } else {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            MainUIMethods.shadAnim(background, "elevation", 0, 200);
                            MainUIMethods.shadAnim(slider, "translationY", -200, 200);
                            MainUIMethods.shadAnim(slider, "alpha", 0, 200);
                            slider.setVisibility(View.INVISIBLE);
                        } else {
                            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                                MainUIMethods.shadAnim(background, "elevation", 20, 200);
                                MainUIMethods.shadAnim(slider, "translationY", 0, 200);
                                MainUIMethods.shadAnim(slider, "alpha", 1, 200);
                                slider.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

    }


    public void _setImageFromUrl(final ImageView _image, final String _url) {
        Glide.with(this)

                .load(_url)
                .fitCenter()
                .into(_image);

    }


    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View _v = _inflater.inflate(R.layout.tutorialview, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final TextView textview1 = _view.findViewById(R.id.textview1);
            final TextView textview2 = _view.findViewById(R.id.textview2);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);

            if (Objects.requireNonNull(_data.get(_position).get("isTitled")).toString().equals("true")) {
                textview1.setVisibility(View.VISIBLE);
                textview1.setText(Objects.requireNonNull(_data.get(_position).get("title")).toString());
            } else {
                textview1.setVisibility(View.GONE);
            }
            if (Objects.requireNonNull(_data.get(_position).get("isSubtitled")).toString().equals("true")) {
                textview2.setVisibility(View.VISIBLE);
                textview2.setText(Objects.requireNonNull(_data.get(_position).get("subtitle")).toString());
            } else {
                textview2.setVisibility(View.GONE);
            }
            if (Objects.requireNonNull(_data.get(_position).get("isImaged")).toString().equals("true")) {
                imageview1.setVisibility(View.VISIBLE);
                _setImageFromUrl(imageview1, Objects.requireNonNull(_data.get(_position).get("image")).toString());
            } else {
                imageview1.setVisibility(View.GONE);
            }
            textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/whitney.ttf"), Typeface.BOLD);
            textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/whitney.ttf"), Typeface.NORMAL);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(_lp);
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

    }

    public class LoadingRecyclerAdapter extends RecyclerView.Adapter<LoadingRecyclerAdapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public LoadingRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View _v = _inflater.inflate(R.layout.loadingview, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final LinearLayout categoriesShimmer = _view.findViewById(R.id.categoriesShimmer);
            final LinearLayout shimmer2 = _view.findViewById(R.id.shimmer2);
            final LinearLayout shimmer3 = _view.findViewById(R.id.shimmer3);
            final LinearLayout shimmer4 = _view.findViewById(R.id.shimmer4);


            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(_lp);
            categoriesShimmer.setVisibility(View.GONE);
            MainUIMethods.setClippedView(shimmer2, "#FFFFFF", 30, 0);
            MainUIMethods.setClippedView(shimmer3, "#FFFFFF", 200, 0);
            MainUIMethods.setClippedView(shimmer4, "#FFFFFF", 200, 0);
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

    }
}