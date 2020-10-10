package com.example.samsmile;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSourceList extends RecyclerView.Adapter<AdapterSourceList.holder> implements Filterable {

    private Context context;
    public ArrayList<ModelSourceList> sourceLists, filterList;
    private FilterSourcesList filter;

    public AdapterSourceList(Context context, ArrayList<ModelSourceList> sourceLists) {
        this.context = context;
        this.sourceLists = sourceLists;
        this.filterList = sourceLists;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_source_list,  parent, false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        ModelSourceList model = sourceLists.get(position);
        String id = model.getId();
        String name = model.getName();
        String description = model.getDescription();
        String country = model.getCountry();
        String category = model.getCategory();
        String language = model.getLanguage();

        holder.nameTv.setText(name);
        holder.languageTv.setText("Language: "+language);
        holder.countryTv.setText("Country: "+country);
        holder.descriptionTv.setText(description);
        holder.categoryTv.setText("Category: "+category);

        /**
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });**/
    }

    @Override
    public int getItemCount() {
        return sourceLists.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterSourcesList(this, filterList);
        }
        return filter;
    }

    class holder extends RecyclerView.ViewHolder{

        TextView nameTv, descriptionTv, categoryTv, languageTv, countryTv;
        public holder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            countryTv  = itemView.findViewById(R.id.countryTv);
            categoryTv = itemView.findViewById(R.id.categoryTv);
            languageTv = itemView.findViewById(R.id.languageTv);
        }
    }
}
