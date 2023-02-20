package com.example.admin;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.common.MedCentre;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<MedCentre> localDataSet;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView[] textViews = new TextView[2];

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViews[0] = (TextView) view.findViewById(R.id.textView);
            textViews[1] = (TextView) view.findViewById(R.id.textView2);
        }

        public TextView[] getTextViews() {
            return textViews;
        }

    }


    /**
     * STEP 1: Initialize the dataset of the Adapter
     */
    public CustomAdapter(ArrayList<MedCentre> al) {
        localDataSet = al;
    }


    // STEP 2: Create new views (invoked by the layout manager)
    @Override @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.data_row, viewGroup, false);

        return new ViewHolder(view);
    }


    // STEP 3: Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        try{
            viewHolder.getTextViews()[0].setText(localDataSet.get(position).getCentreName());
            viewHolder.getTextViews()[1].setText(localDataSet.get(position).getSlots()+"");
        }
        catch (Exception ne){
            viewHolder.getTextViews()[0].setText("Error!");
            viewHolder.getTextViews()[1].setText("404");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


