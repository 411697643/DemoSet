package com.gm.jianmerchant.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/16.
 */
public class BaseFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "BaseFragment";
    public Context getHoldingContext() {
        return context;
    }

    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.i(TAG, "onAttach: ----");
    }
    

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.i(TAG, "onAttachFragment: ----");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ----");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ----");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ----");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ----");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ----");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ----");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ----");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ----");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ----");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
