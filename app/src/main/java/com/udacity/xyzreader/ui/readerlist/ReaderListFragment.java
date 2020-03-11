package com.udacity.xyzreader.ui.readerlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.udacity.xyzreader.R;
import com.udacity.xyzreader.ui.events.ItemSelectionEventListener;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReaderListFragment extends Fragment {

    public static final String TAG = "ReaderListFragment";

    private ItemSelectionEventListener listener;
    private ReaderListViewModel viewModel;
    private ReaderListAdapter adapter;

    private static final String REFRESH_KEY = "refreshing_key";

    @BindView(R.id.sr_swipe_to_refresh)
    SwipeRefreshLayout swipeToRefresh;

    @BindView(R.id.rv_readers)
    RecyclerView recyclerView;

    @BindString(R.string.error)
    String errorText;

    public static ReaderListFragment newInstance() {
        Bundle args = new Bundle();

        ReaderListFragment fragment = new ReaderListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity()).get(ReaderListViewModel.class);
            listener = (ItemSelectionEventListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reader_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.span_count)));

        if (savedInstanceState != null && savedInstanceState.containsKey(REFRESH_KEY)) {
            boolean isRefreshing = savedInstanceState.getBoolean(REFRESH_KEY);
            swipeToRefresh.setRefreshing(isRefreshing);
        }

        swipeToRefresh.setOnRefreshListener(() -> {
            viewModel.loadData();
        });

        viewModel.readers.observe(getViewLifecycleOwner(), items -> {
            if (adapter == null) {
                adapter = new ReaderListAdapter(items, viewModel);
                recyclerView.setAdapter(adapter);

            } else {
                adapter.updateData(items);
            }
        });

        viewModel.reader.observe(getViewLifecycleOwner(), readerEvent -> {
            if (readerEvent.getIfNotHandled() != null) {
                listener.onItemSelectedEvent(readerEvent.peek());
            }
        });

        viewModel.loading.observe(getViewLifecycleOwner(), loading -> {
            if (loading.getIfNotHandled() == null) return;
            swipeToRefresh.setRefreshing(loading.peek());
        });

        viewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error.getIfNotHandled() == null) return;
            if (error.peek()) Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(REFRESH_KEY, swipeToRefresh.isRefreshing());
    }
}
