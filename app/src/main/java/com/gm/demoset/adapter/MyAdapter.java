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
    private onClickListener mOnClickListener;

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface onClickListener {
        void onItemClick(View itemView, int position);

        void onItemLongClick(View v, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(dataSet.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnClickListener.onItemClick(holder.itemView,layoutPosition);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnClickListener != null) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnClickListener.onItemLongClick(v, layoutPosition);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addData(int pos) {
        dataSet.add(pos,"seven & &");
        notifyItemInserted(pos);
    }

    public void delData(int pos) {
        dataSet.remove(pos);
        notifyItemRemoved(pos);
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
