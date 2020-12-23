package com.tomrobette.collecotheque;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCol extends RecyclerView.Adapter<ViewHolderCol> {
    public interface Listener {
        void onClickButton(int position);
    }

    private final Listener callback;
    private List<Collection> list;
    private LocalAccess localAccess;

    public AdapterCol(Listener callback, List<Collection> list, LocalAccess localAccess) {
        this.callback = callback;
        this.list = list;
        this.localAccess = localAccess;
    }

    @Override
    public ViewHolderCol onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ligne,viewGroup,false);
        return new ViewHolderCol(view, localAccess);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ViewHolderCol viewHolderCol, int position) {
        Collection collection = list.get(position);
        viewHolderCol.bind(collection, callback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
