package com.tomrobette.collecotheque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListEtaActivity extends AppCompatActivity implements AdapterEta.Listener{
    private RecyclerView recyclerView;
    private LocalAccess localAccess = new LocalAccess(this);
    private Button btAdd;
    private Bibliotheque bib;
    private boolean gotExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void getExtraBib(){
        try {
            Bundle extras = getIntent().getExtras();
            int position = (int) extras.getSerializable("bib");
            if (Session.isTest())
                Toast.makeText(ListEtaActivity.this, position+"!", Toast.LENGTH_SHORT).show();
            bib = (Bibliotheque) localAccess.getBibById(position);
            if (Session.isTest())
                Toast.makeText(ListEtaActivity.this, bib.getNom()+"!", Toast.LENGTH_SHORT).show();
            gotExtra=true;
        } catch (Exception e) {
            gotExtra=false;
        }
    }

    private void init() {
        getExtraBib();
        btAdd = findViewById(R.id.btListeBibAdd);
        btAdd.setText(R.string.liste_eta_ajout);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEtaActivity.this, AjoutEtaActivity.class);
                intent.putExtra("bib", bib.getId());
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.listeBibs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterEta(this, localAccess.getAllEtaByBibId(bib.getId()), localAccess));
    }

    @Override
    public void onClickButton(int position) {
        Intent intent = new Intent(ListEtaActivity.this, ListColActivity.class);
        intent.putExtra("eta", position);
        startActivity(intent);
    }
}