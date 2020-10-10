package com.example.samsmile;

import android.graphics.ColorSpace;
import android.widget.Filter;

import java.util.ArrayList;

public class FilterSourcesList extends Filter {
    private AdapterSourceList adapter;
    private ArrayList<ModelSourceList> filterList;

    public FilterSourcesList(AdapterSourceList adapter, ArrayList<ModelSourceList> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() >0){
            constraint = constraint.toString().toUpperCase();

            ArrayList<ModelSourceList> filteredModel = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++){
                if (filterList.get(i).getName().toUpperCase().contains(constraint)){
                    filteredModel.add(filterList.get(i));
                }
            }
            results.count = filteredModel.size();
            results.values = filteredModel;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.sourceLists = (ArrayList<ModelSourceList>) results.values;
        adapter.notifyDataSetChanged();

    }
}
