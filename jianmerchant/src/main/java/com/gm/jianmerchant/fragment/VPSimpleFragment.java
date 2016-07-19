package com.gm.jianmerchant.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gm.jianmerchant.R;
import com.gm.jianmerchant.adapter.MoviesAdapter;
import com.gm.jianmerchant.base.BaseFragment;
import com.gm.jianmerchant.bean.Subject;
import com.gm.jianmerchant.http.HttpMethods;
import com.gm.jianmerchant.http.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/16.
 */
public class VPSimpleFragment extends BaseFragment {
    private static final String TAG = "VPSimpleFragment";
    private static final String PARAM = "type";
    private static final int SUCCESS = 1;
    private static final int FAIL = 0;
    private String type;
    private boolean isPrepared;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private View inflate = null;
    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;
    private TextView view;
    private int i = 0;
    private SwipeRefreshLayout swipe;
    private RecyclerView recycler;
    private LinearLayoutManager linearLayoutManager;
    private List<Subject> subjectList;
    private List<Subject> addSubjects = new ArrayList<>();
    private MoviesAdapter moviesAdapter;
    private ProgressSubscriber<List<Subject>> progressSubscriber;
    private int a=0;
    private int b=1;
    private android.os.Handler handler= new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    a++;
                    swipe.setRefreshing(false);
                    moviesAdapter.notifyDataSetChanged();
                    break;
                case FAIL:
                    break;
            }
        }
    };
    /**
     * 当前最后可见的位置
     */
    private int lastPosition;


    public static VPSimpleFragment newInstance(String type) {
        VPSimpleFragment vpSimpleFragment = new VPSimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, type);
        vpSimpleFragment.setArguments(bundle);
        return vpSimpleFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: VPSimpleFragment ");
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(PARAM);
        }

    }

    /**
     * 获取电影
     */
    private void getItemMovies(int start,int count) {
        progressSubscriber = new ProgressSubscriber<>(new ProgressSubscriber.SubscriberOnNextListenner<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                subjectList = subjects;
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    addSubjects.addAll(0, subjectList);
                } else {
                    addSubjects.addAll(subjectList);
                }
                handler.sendEmptyMessage(SUCCESS);
            }
        }, getHoldingContext());
        Log.i(TAG, "网络加载数据"+getArguments().getString(PARAM));
        swipe.setRefreshing(true);
        HttpMethods.getInstance().getTopMovie(progressSubscriber,start,count);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }


    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (type == "g") {
            inflate = inflater.inflate(R.layout.fragment_a, container, false);
        } else if (type=="a") {
            inflate = inflater.inflate(R.layout.fragment_b, container, false);
        } else if (type == "o") {
            inflate = inflater.inflate(R.layout.fragment_c, container, false);
        }
        initview();
        return inflate;

    }

    private void initview() {
        swipe = (SwipeRefreshLayout) inflate.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: ab-->"+a+"---"+b);
                getItemMovies(10*(a),10);
            }
        });
        swipe.setProgressViewOffset(false,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        swipe.setRefreshing(true);
        recycler = (RecyclerView) inflate.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getHoldingContext());
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition + 1 == moviesAdapter.getItemCount()) {
                    //最后一行松开
                    Log.i(TAG, "最后一行松开");
                    moviesAdapter.currentState = 0;
                    Log.i(TAG, "a b --->" +a+"----"+b);
                    getItemMovies(10*(a),10);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING && lastPosition + 1 == moviesAdapter.getItemCount()){
                    //最后一行拖拽
                    Log.i(TAG, "最后一行拖拽");//
                    moviesAdapter.currentState = 1;
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING&& lastPosition+1==moviesAdapter.getItemCount()) {
                    Log.i(TAG, "最后一行漂移");//加载数据
                    moviesAdapter.currentState = 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "onScrolled: "+linearLayoutManager.findLastVisibleItemPosition());
                    lastPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        moviesAdapter = new MoviesAdapter(addSubjects, getHoldingContext());
        moviesAdapter.setOnRefreshListenner(new MoviesAdapter.onRefreshListenner() {
            @Override
            public void onRefresh(TextView tv) {
                tv.setText("还有可能更多 @_@");
            }
        });
        recycler.setAdapter(moviesAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {

        getItemMovies(10*a,10);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//最先执行在attach之前
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {

    }
}
