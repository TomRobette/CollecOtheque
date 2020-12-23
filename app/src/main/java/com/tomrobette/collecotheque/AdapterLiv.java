package com.tomrobette.collecotheque;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLiv extends RecyclerView.Adapter<ViewHolderLiv> {
    public interface Listener {
        void onClickButton(String isbn);
    }

    private final Listener callback;
    private List<Livre> list;
    private LocalAccess localAccess;

    public AdapterLiv(Listener callback, List<Livre> list, LocalAccess localAccess) {
        this.callback = callback;
        this.list = list;
        this.localAccess = localAccess;
    }

    @Override
    public ViewHolderLiv onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ligne,viewGroup,false);
        return new ViewHolderLiv(view, localAccess);
    }

    @Override
    public void onBindViewHolder(ViewHolderLiv viewHolderLiv, int position) {
        Livre livre = list.get(position);
        viewHolderLiv.bind(livre, callback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
