package com.tomrobette.collecotheque;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterEta extends RecyclerView.Adapter<ViewHolderEta> {
    public interface Listener {
        void onClickButton(int position);
    }

    private final Listener callback;
    private List<Etagere> list;
    private LocalAccess localAccess;

    //ajouter un constructeur prenant en entrée une liste
    public AdapterEta(Listener callback, List<Etagere> list, LocalAccess localAccess) {
        this.callback = callback;
        this.list = list;
        this.localAccess = localAccess;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public ViewHolderEta onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ligne,viewGroup,false);
        return new ViewHolderEta(view, localAccess);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ViewHolderEta viewHolderEta, int position) {
        Etagere etagere = list.get(position);
        viewHolderEta.bind(etagere, callback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
