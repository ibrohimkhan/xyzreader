package com.udacity.xyzreader.ui.readerdetails.textadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.xyzreader.R;

public class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private String[] paragraph;

    public TextAdapter(String[] paragraph) {
        this.paragraph = paragraph;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list_items, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.bind(paragraph[position]);
    }

    @Override
    public int getItemCount() {
        return paragraph.length;
    }
}
