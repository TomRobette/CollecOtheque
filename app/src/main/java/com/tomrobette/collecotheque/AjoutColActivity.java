package com.tomrobette.collecotheque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AjoutColActivity extends AppCompatActivity{

    private TextView textView, textView2;
    private EditText nameZone;
    private Button button;
    private LocalAccess localAccess = new LocalAccess(this);
    private Etagere eta;
    private boolean gotExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_base);

        init();
    }

    private void getExtraEta(){
        try {
            Bundle extras = getIntent().getExtras();
            int position = (int) extras.getSerializable("eta");
            eta = (Etagere) localAccess.getEtaById(position);
            gotExtra=true;
        } catch (Exception e) {
            gotExtra=false;
        }
    }

    private void init(){
        getExtraEta();
        textView = findViewById(R.id.textView);
        textView.setText(R.string.ajout_col_titre);
        textView2 = findViewById(R.id.textView2);
        textView2.setText(R.string.ajout_col_titre_nom);
        nameZone = findViewById(R.id.nameZone);
        nameZone.setHint(R.string.ajout_col_hint);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameZone.getText().toString().isEmpty()){
                    if (Session.isTest())
                        Toast.makeText(AjoutColActivity.this, "READ!", Toast.LENGTH_SHORT).show();
                    if (!localAccess.doesColNameExist(nameZone.getText().toString(), eta.getId())){
                        if (Session.isTest())
                            Toast.makeText(AjoutColActivity.this, "ORIGINAL!", Toast.LENGTH_SHORT).show();
                        if (Session.getUser()!=null)
                            if (Session.isTest())
                                Toast.makeText(AjoutColActivity.this, "DONE!", Toast.LENGTH_SHORT).show();
                            localAccess.addCollection(nameZone.getText().toString(), eta.getId());
                            Intent intent = new Intent(AjoutColActivity.this, ListColActivity.class);
                            intent.putExtra("eta", eta.getId());
                            startActivity(intent);
                    }
                }
            }
        });
    }
}