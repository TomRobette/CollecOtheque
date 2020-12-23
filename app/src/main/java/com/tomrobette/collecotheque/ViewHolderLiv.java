package com.tomrobette.collecotheque;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;


public class ViewHolderLiv extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Button bt;
    private TextView voidView;
    private View itemView;

    private WeakReference<AdapterLiv.Listener> callbackWeakRef;
    private LocalAccess localAccess;

    public ViewHolderLiv(View itemView, LocalAccess localAccess) {
        super(itemView);
        this.itemView=itemView;
        bt = itemView.findViewById(R.id.bt);
        voidView = itemView.findViewById(R.id.voidView);
        this.localAccess=localAccess;

    }

    public void bind(Livre livre, AdapterLiv.Listener callback){
        bt.setText(livre.getTitre());
        bt.setOnClickListener(this);
        voidView.setText(""+livre.getIsbn());
        this.callbackWeakRef = new WeakReference<AdapterLiv.Listener>(callback);



    }

    @Override
    public void onClick(View view) {
        AdapterLiv.Listener callback = callbackWeakRef.get();
        String isbn = "";
        try {
            isbn = voidView.getText().toString();
        }catch (Exception e){
            isbn = null;
        }
        if (callback != null && isbn!=null){
            callback.onClickButton(isbn);
        }else{
            System.out.println("ERREUR LORS DU TRANSFERT DE L'ID");
        }
    }
}