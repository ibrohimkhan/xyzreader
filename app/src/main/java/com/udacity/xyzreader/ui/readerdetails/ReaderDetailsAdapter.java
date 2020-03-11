package com.udacity.xyzreader.ui.readerdetails;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.udacity.xyzreader.data.model.Reader;

import java.util.List;

public class ReaderDetailsAdapter extends FragmentStateAdapter {

    private List<Reader> readers;

    public ReaderDetailsAdapter(@NonNull Fragment fragment, List<Reader> readers) {
        super(fragment);
        this.readers = readers;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Reader reader = readers.get(position);
        return DetailsFragment.newInstance(reader);
    }

    @Override
    public int getItemCount() {
        return readers.size();
    }
}
