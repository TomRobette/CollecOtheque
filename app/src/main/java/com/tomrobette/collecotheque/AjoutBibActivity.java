package com.tomrobette.collecotheque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AjoutBibActivity extends AppCompatActivity{

    private TextView textView, textView2;
    private EditText nameZone;
    private Button button;
    private LocalAccess localAccess = new LocalAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_base);

        init();
    }

    private void init(){
        textView = findViewById(R.id.textView);
        textView.setText(R.string.ajout_bib_titre);
        textView2 = findViewById(R.id.textView2);
        textView2.setText(R.string.ajout_bib_titre_nom);
        nameZone = findViewById(R.id.nameZone);
        nameZone.setHint(R.string.ajout_bib_hint);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameZone.getText().toString().isEmpty()){
                    if (Session.isTest())
                        Toast.makeText(AjoutBibActivity.this, "READ!", Toast.LENGTH_SHORT).show();
                    if (!localAccess.doesBibNameExist(nameZone.getText().toString(), Session.user.getId())){
                        if (Session.isTest())
                            Toast.makeText(AjoutBibActivity.this, "ORIGINAL!", Toast.LENGTH_SHORT).show();
                        if (Session.getUser()!=null)
                            if (Session.isTest())
                                Toast.makeText(AjoutBibActivity.this, "DONE!", Toast.LENGTH_SHORT).show();
                            localAccess.addBibliotheque(nameZone.getText().toString(), Session.user.getId());
                            Intent intent = new Intent(AjoutBibActivity.this, MainActivity.class);
                            startActivity(intent);
                    }
                }
            }
        });
    }
}