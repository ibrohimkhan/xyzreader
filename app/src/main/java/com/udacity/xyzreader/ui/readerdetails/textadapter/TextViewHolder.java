package com.udacity.xyzreader.ui.readerdetails.textadapter;

import android.graphics.Typeface;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.xyzreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_paragraph)
    TextView paragraph;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        paragraph.setTypeface(
                Typeface.createFromAsset(itemView.getResources().getAssets(), "Rosario-Regular.ttf")
        );
    }

    public void bind(String s){
        paragraph.setText(Html.fromHtml(s));
    }
}
