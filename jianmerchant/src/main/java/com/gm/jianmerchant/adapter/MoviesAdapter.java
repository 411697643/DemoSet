package com.gm.jianmerchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gm.jianmerchant.R;
import com.gm.jianmerchant.bean.Subject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/7/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private static final int FOOTER = 1;
    private static final int NORMAL = 0;
    private List<Subject> subjects;
    private Context context;
    private StringBuffer sb = null;
    public int currentState = 1;

    public MoviesAdapter(List<Subject> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case NORMAL:
                View view_normal = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder(view_normal, viewType);
                break;
            case FOOTER:
                View view_footer = LayoutInflater.from(context).inflate(R.layout.recycler_footer, parent, false);
                viewHolder = new ViewHolder(view_footer, viewType);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position == subjects.size()) {
            onRefreshListenner.onRefresh(holder.tv_footer);
        } else {
            holder.title.setText(subjects.get(position).getTitle());
            holder.year.setText(subjects.get(position).getYear());
            holder.original_title.setText(subjects.get(position).getOriginal_title());
            holder.average.setText(String.valueOf(subjects.get(position).getRating().getAverage()));
            for (int i=0;i<3;i++) {
                if (sb == null) {
                    sb = new StringBuffer();
                }
                sb.append(subjects.get(position).getCasts().get(i).getName());
                if (i < 2) {
                    sb.append("、");
                }
            }
            holder.casts.setText(sb.toString());
            sb = null;
            holder.genres.setText(subjects.get(position).getGenres().get(0));
            holder.name.setText(subjects.get(position).getDirectors().get(0).getName());
            Picasso.with(context).load(subjects.get(position).getImages().getMedium())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        if (subjects != null && subjects.size() > 1) {
            return subjects.size() + 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (subjects != null) {
            if (position == subjects.size()) {
                return FOOTER;
            } else {
                return NORMAL;
            }
        }
        return FOOTER;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout movie_main_info;
        private TextView title;
        private TextView year;
        private TextView original_title;
        private TextView average;
        private ImageView image;
        private TextView casts;
        private TextView genres;
        private TextView name;
        private TextView tv_footer;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            switch (type) {
                case FOOTER:
                    tv_footer = (TextView) itemView.findViewById(R.id.tv_footer);
                    break;
                case NORMAL:
                    movie_main_info = (RelativeLayout) itemView.findViewById(R.id.movie_main_info);
                    title = (TextView) itemView.findViewById(R.id.title);
                    year = (TextView) itemView.findViewById(R.id.year);
                    original_title = (TextView) itemView.findViewById(R.id.original_title);
                    average = (TextView) itemView.findViewById(R.id.average);
                    image = (ImageView) itemView.findViewById(R.id.image);
                    casts = (TextView) itemView.findViewById(R.id.casts);
                    genres = (TextView) itemView.findViewById(R.id.genres);
                    name = (TextView) itemView.findViewById(R.id.name);

                    break;
            }
        }
    }

    public void setOnRefreshListenner(MoviesAdapter.onRefreshListenner onRefreshListenner) {
        this.onRefreshListenner = onRefreshListenner;
    }

    private onRefreshListenner onRefreshListenner;



    public interface onRefreshListenner{
        void onRefresh(TextView tv);
    }

}
