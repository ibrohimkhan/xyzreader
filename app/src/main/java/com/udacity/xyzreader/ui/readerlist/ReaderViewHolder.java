package com.udacity.xyzreader.ui.readerlist;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.xyzreader.R;
import com.udacity.xyzreader.common.DateUtil;
import com.udacity.xyzreader.common.Event;
import com.udacity.xyzreader.data.model.Reader;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "ReaderViewHolder";

    private ReaderListViewModel viewModel;
    private int position;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_date)
    TextView date;

    @BindView(R.id.tv_author)
    TextView author;

    @BindView(R.id.iv_thumb)
    ImageView thumb;

    @BindString(R.string.by)
    String by;

    public ReaderViewHolder(@NonNull View itemView, ReaderListViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Reader reader, int position) {
        this.position = position;

        title.setText(reader.title);
        date.setText(DateUtil.format(reader.publishedDate));
        author.setText(by.concat(" ").concat(reader.author));

        if (!TextUtils.isEmpty(reader.thumb)) {
            Picasso.get()
                    .load(reader.thumb)
                    .fit()
                    .into(thumb);
        }
    }

    @Override
    public void onClick(View v) {
        viewModel.onItemSelected(new Event<>(position));
    }
}
