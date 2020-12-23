package com.tomrobette.collecotheque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListColActivity extends AppCompatActivity implements AdapterCol.Listener{
    private RecyclerView recyclerView;
    private LocalAccess localAccess = new LocalAccess(this);
    private Button btAdd;
    private Etagere eta;
    private boolean gotExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void getExtraEta(){
        try {
            Bundle extras = getIntent().getExtras();
            int position = (int) extras.getSerializable("eta");
            if (Session.isTest())
                Toast.makeText(ListColActivity.this, position+"!", Toast.LENGTH_SHORT).show();
            eta = (Etagere) localAccess.getEtaById(position);
            if (Session.isTest())
                Toast.makeText(ListColActivity.this, eta.getNom()+"!", Toast.LENGTH_SHORT).show();
            gotExtra=true;
        } catch (Exception e) {
            gotExtra=false;
        }
    }

    private void init() {
        getExtraEta();
        btAdd = findViewById(R.id.btListeBibAdd);
        btAdd.setText(R.string.liste_col_ajout);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListColActivity.this, AjoutColActivity.class);
                intent.putExtra("eta", eta.getId());
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.listeBibs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterCol(this, localAccess.getAllColsByEtaId(eta.getId()), localAccess));
    }

    @Override
    public void onClickButton(int position) {
        Intent intent = new Intent(ListColActivity.this, ListLivActivity.class);
        intent.putExtra("col", position);
        startActivity(intent);
    }
}