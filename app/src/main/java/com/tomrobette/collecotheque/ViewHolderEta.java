package com.tomrobette.collecotheque;


import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;


public class ViewHolderEta extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Button bt;
    private ConstraintLayout constraint;
    private View itemView;

    private WeakReference<AdapterEta.Listener> callbackWeakRef;
    private LocalAccess localAccess;

    public ViewHolderEta(View itemView, LocalAccess localAccess) {
        super(itemView);
        this.itemView=itemView;
        bt = itemView.findViewById(R.id.bt);
        constraint = itemView.findViewById(R.id.contrainte);
        this.localAccess=localAccess;

    }

    public void bind(Etagere etagere, AdapterEta.Listener callback){
        bt.setText(etagere.getNom());
        bt.setOnClickListener(this);
        constraint.setId(etagere.getId());
        this.callbackWeakRef = new WeakReference<AdapterEta.Listener>(callback);



    }

    @Override
    public void onClick(View view) {
        AdapterEta.Listener callback = callbackWeakRef.get();
        Integer position = 0;
        try {
            position = constraint.getId();
        }catch (Exception e){
            position = null;
        }
        if (callback != null && position!=null){
            callback.onClickButton(position);
        }else{
            System.out.println("ERREUR LORS DU TRANSFERT DE L'ID");
        }
    }
}