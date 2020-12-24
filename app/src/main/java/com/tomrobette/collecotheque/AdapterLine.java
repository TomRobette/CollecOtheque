package com.tomrobette.collecotheque;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLine extends RecyclerView.Adapter<ViewHolderLine> {
    private List<String> list;

    public AdapterLine(List<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolderLine onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ligne_simple,viewGroup,false);
        return new ViewHolderLine(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderLine viewHolderLine, int position) {
        String chaine = list.get(position);
        viewHolderLine.bind(chaine);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
