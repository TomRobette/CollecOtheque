package com.tomrobette.collecotheque;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ViewHolderLine extends RecyclerView.ViewHolder{
    private TextView tv_simple;
    private View lineView;

    public ViewHolderLine(View lineView) {
        super(lineView);
        this.lineView = lineView;
        tv_simple = lineView.findViewById(R.id.tv_simple);

    }

    public void bind(String chaine){
        tv_simple.setText(""+chaine);

    }
}