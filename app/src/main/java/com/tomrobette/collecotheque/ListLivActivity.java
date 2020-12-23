package com.tomrobette.collecotheque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListLivActivity extends AppCompatActivity implements AdapterLiv.Listener{
    private RecyclerView recyclerView;
    private LocalAccess localAccess = new LocalAccess(this);
    private Button btAdd;
    private Collection col;
    private boolean gotExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void getExtraCol(){
        try {
            Bundle extras = getIntent().getExtras();
            int position = (int) extras.getSerializable("col");
            if (Session.isTest())
                Toast.makeText(ListLivActivity.this, position+"!", Toast.LENGTH_SHORT).show();
            col = (Collection) localAccess.getColById(position);
            if (Session.isTest())
                Toast.makeText(ListLivActivity.this, col.getNom()+"!", Toast.LENGTH_SHORT).show();
            gotExtra=true;
        } catch (Exception e) {
            gotExtra=false;
        }
    }

    private void init() {
        getExtraCol();
        btAdd = findViewById(R.id.btListeBibAdd);
        btAdd.setText(R.string.liste_liv_ajout);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListLivActivity.this, AjoutLivActivity.class);
                intent.putExtra("col", col.getId());
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.listeBibs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterLiv(this, localAccess.getAllLivByColId(col.getId()), localAccess));
    }

    @Override
    public void onClickButton(String isbn) {
        Intent intent = new Intent(ListLivActivity.this, VueLivreActivity.class);
        intent.putExtra("liv", isbn);
        startActivity(intent);
    }
}