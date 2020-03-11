package com.udacity.xyzreader.ui.readerdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.udacity.xyzreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReaderDetailsFragment extends Fragment {

    public static final String TAG = "ReaderDetailsFragment";
    private static final String READER_POSITION_KEY = "reader_key";

    private ReaderDetailsViewModel viewModel;
    private int readerPosition;

    private ViewPager2.OnPageChangeCallback callback;
    private ViewPager2.PageTransformer animator;

    @BindView(R.id.vp_details)
    ViewPager2 viewPager;

    private ReaderDetailsAdapter adapter;

    public static ReaderDetailsFragment newInstance(Integer position) {
        Bundle args = new Bundle();
        args.putInt(READER_POSITION_KEY, position);

        ReaderDetailsFragment fragment = new ReaderDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(READER_POSITION_KEY)) {
            viewModel = new ViewModelProvider(this).get(ReaderDetailsViewModel.class);
            readerPosition = getArguments().getInt(READER_POSITION_KEY, -1);
        }

        callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        };

        animator = (page, position) -> {
            float absPos = Math.abs(position);
            float scale = absPos > 1 ? 0F : 1 - absPos;

            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setTranslationX(absPos * 350f);
            page.setTranslationY(absPos * 500f);
            page.setAlpha(0.5f + (scale - 0.4f) / (1 - 0.4f) * (1 - 0.5f));
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reader_details, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager.registerOnPageChangeCallback(callback);
        viewPager.setPageTransformer(animator);

        viewModel.readers.observe(getViewLifecycleOwner(), readers -> {
            adapter = new ReaderDetailsAdapter(this, readers);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(readerPosition, false);
        });

        viewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error.getIfNotHandled() == null) return;
            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        viewPager.unregisterOnPageChangeCallback(callback);
        super.onDestroyView();
    }
}
