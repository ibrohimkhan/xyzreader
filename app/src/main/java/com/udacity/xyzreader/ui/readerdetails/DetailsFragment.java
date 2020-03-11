package com.udacity.xyzreader.ui.readerdetails;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.SubtitleCollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.udacity.xyzreader.R;
import com.udacity.xyzreader.common.DateUtil;
import com.udacity.xyzreader.data.model.Reader;
import com.udacity.xyzreader.ui.MainActivity;
import com.udacity.xyzreader.ui.readerdetails.textadapter.TextAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment";
    private static final String READER_KEY = "reader_key";

    private Reader reader;

    @BindView(R.id.subtitlecollapsingtoolbarlayout)
    SubtitleCollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.tb_toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @BindView(R.id.rv_body)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindString(R.string.by)
    String by;

    public static DetailsFragment newInstance(Reader reader) {
        Bundle args = new Bundle();
        args.putSerializable(READER_KEY, reader);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(READER_KEY)) {
            reader = (Reader) getArguments().getSerializable(READER_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1);

        Picasso.get()
                .load(reader.photo)
                .into(ivPhoto);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });

        collapsingToolbarLayout.setTitle(reader.title);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleTextColor(Color.WHITE);

        collapsingToolbarLayout.setSubtitle(
                DateUtil.format(reader.publishedDate).concat(" ")
                        .concat(by).concat(" ").concat(reader.author)
        );

        collapsingToolbarLayout.setCollapsedSubtitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedSubtitleTextColor(Color.WHITE);

        String[] body = reader.body.split("(\r\n|\n)");
        TextAdapter adapter = new TextAdapter(body);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(view1 ->
                startActivity(
                        Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                                .setType("text/plain")
                                .setText(reader.title)
                                .getIntent(), getString(R.string.action_share)
                        )
                )
        );
    }
}
