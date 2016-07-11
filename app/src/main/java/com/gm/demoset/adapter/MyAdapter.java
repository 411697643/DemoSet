package com.gm.demoset.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gm.demoset.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> dataSet;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof LinearLayout) {
                mCardView = (CardView) itemView.findViewById(R.id.card_view);
                mTextView = (TextView) itemView.findViewById(R.id.text_info);
            }



        }
    }

    public MyAdapter(List<String> dataSet) {
        this.dataSet = dataSet;
    }
}
