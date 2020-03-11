package com.udacity.xyzreader.ui.readerlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.xyzreader.R;
import com.udacity.xyzreader.data.model.Reader;

import java.util.List;

public class ReaderListAdapter extends RecyclerView.Adapter<ReaderViewHolder> {

    private List<Reader> readers;
    private ReaderListViewModel viewModel;

    public ReaderListAdapter(List<Reader> readers, ReaderListViewModel viewModel) {
        this.readers = readers;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reader_list_item, parent, false);
        return new ReaderViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ReaderViewHolder holder, int position) {
        holder.bind(readers.get(position), position);
    }

    @Override
    public int getItemCount() {
        return readers.size();
    }

    public void updateData(List<Reader> items) {
        readers.clear();
        readers.addAll(items);
        notifyItemRangeChanged(0, items.size());
    }
}
