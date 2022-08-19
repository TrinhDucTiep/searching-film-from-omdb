package com.example.searchingfilm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.searchingfilm.model.Search;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;
    private boolean isLoadingAdd;

    private Context context;
    private ArrayList<Search> listSearch;

    public void setData(ArrayList<Search> list){
        listSearch = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(listSearch !=null && position == listSearch.size()-1 && isLoadingAdd){

            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if(viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
            return new FilmViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == TYPE_ITEM){
            Search filmSearch = listSearch.get(position);
            FilmViewHolder filmViewHolder = (FilmViewHolder) holder;
            filmViewHolder.titleTv.setText(filmSearch.getTitle());
            filmViewHolder.yearTv.setText(filmSearch.getYear());
            Glide.with(context).load(filmSearch.getPoster()).into(filmViewHolder.poster);

            filmViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailFilmActivity.class);
                    intent.putExtra("id", filmSearch.getImdbID());
                    //todo
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(listSearch != null)
            return listSearch.size();
        return 0;
    }

    public void addFooterLoading(){
        isLoadingAdd = true;
        listSearch.add(new Search("buffer for loading"));
    }

    public void removeFooterLoading(){
        isLoadingAdd = false;

        int positon = listSearch.size()-1;
        Search search = listSearch.get(positon);
        if(search!=null){
            listSearch.remove(positon);
            notifyItemRemoved(positon);
        }
    }

//    @NonNull
//    @Override
//    public FimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
//        context = parent.getContext();
//        return new FimViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FimViewHolder holder, int position) {
//        Search filmSearch = listSearch.get(position);
//        if(filmSearch == null){
//            return;
//        }
//        holder.titleTv.setText(filmSearch.getTitle());
//        holder.yearTv.setText(filmSearch.getYear());
//        Glide.with(context).load(filmSearch.getPoster()).into(holder.poster);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailFilmActivity.class);
//                intent.putExtra("id", filmSearch.getImdbID());
//                //todo
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if(listSearch != null){
//            return listSearch.size();
//        }
//        return 0;
//    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTv;
        private final TextView yearTv;
        private final ImageView poster;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_txt);
            yearTv = itemView.findViewById(R.id.year_txt);
            poster = itemView.findViewById(R.id.poster_img);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_loading);
        }
    }
}
