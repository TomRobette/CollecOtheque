package com.tomrobette.collecotheque;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterBib.Listener{
    private RecyclerView recyclerView;
    private LocalAccess localAccess = new LocalAccess(this);
    private Button btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void init() {
        Session.setUser(new Utilisateur(0, "bobby@gmal.com", "1234", "Bobby",new ArrayList<Bibliotheque>()));
        Session.setTestMode(true);
        btAdd = findViewById(R.id.btListeBibAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AjoutBibActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.listeBibs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterBib(this, localAccess.getAllBibByUserId(Session.user.getId()), localAccess));
    }

    @Override
    public void onClickButton(int position) {
        Intent intent = new Intent(MainActivity.this, ListEtaActivity.class);
        intent.putExtra("bib", position);
        startActivity(intent);
    }
}