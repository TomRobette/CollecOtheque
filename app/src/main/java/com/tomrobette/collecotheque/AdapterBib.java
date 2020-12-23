package com.tomrobette.collecotheque;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterBib extends RecyclerView.Adapter<ViewHolderBib> {
    public interface Listener {
        void onClickButton(int position);
    }

    private final Listener callback;
    private List<Bibliotheque> list;
    private LocalAccess localAccess;

    //ajouter un constructeur prenant en entrée une liste
    public AdapterBib(Listener callback, List<Bibliotheque> list, LocalAccess localAccess) {
        this.callback = callback;
        this.list = list;
        this.localAccess = localAccess;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public ViewHolderBib onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ligne,viewGroup,false);
        return new ViewHolderBib(view, localAccess);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ViewHolderBib viewHolderBib, int position) {
        Bibliotheque bibliotheque = list.get(position);
        viewHolderBib.bind(bibliotheque, callback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
