package com.tomrobette.collecotheque;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AjoutLivActivity extends AppCompatActivity{

    private TextView textView, textView2;
    private EditText nameZone;
    private Button bt_test, bt_valider, bt_recomm;
    private LocalAccess localAccess = new LocalAccess(this);
    private Collection col;
    private boolean gotExtra;
    private BookAccess bookAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_livre);

        init();
    }

    private void getExtraCol(){
        try {
            Bundle extras = getIntent().getExtras();
            int position = (int) extras.getSerializable("col");
            col = (Collection) localAccess.getColById(position);
            gotExtra=true;
        } catch (Exception e) {
            gotExtra=false;
        }
    }

    private void init(){
        getExtraCol();
        bookAccess = new BookAccess(AjoutLivActivity.this);
        bookAccess.getInstance(AjoutLivActivity.this);
        bookAccess.getRequestQueue();
        textView = findViewById(R.id.textView);
        textView.setText(R.string.ajout_liv_titre);
        textView2 = findViewById(R.id.textView2);
        textView2.setText(R.string.ajout_liv_titre_nom);
        nameZone = findViewById(R.id.nameZone);
        nameZone.setHint(R.string.ajout_liv_hint);
        bt_test = findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = nameZone.getText().toString();
                if (!isbn.isEmpty()){
                    if (Session.isTest())
                        Toast.makeText(AjoutLivActivity.this, "READ!", Toast.LENGTH_SHORT).show();
                    if (!localAccess.doesLivISBNExist(isbn, col.getId())) {
                        if (Session.isTest())
                            Toast.makeText(AjoutLivActivity.this, "ORIGINAL!", Toast.LENGTH_SHORT).show();
                        if (Session.getUser() != null)
                            if (isbn.length() > 0 && isbn.length() < 14) {
                                bookAccess.addQueue(bookAccess.doJSONObjectRequestAjout("https://openlibrary.org/isbn/" + isbn + ".json"));
                            } else {
                                Toast.makeText(AjoutLivActivity.this, "ISBN inccorect", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            }
        });
        bt_valider = findViewById(R.id.bt_valider);
        bt_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    localAccess.addLivre(nameZone.getText().toString(), ((TextView)findViewById(R.id.card_tv_title)).getText().toString(), col.getId());
                    if (Session.isTest())
                        Toast.makeText(AjoutLivActivity.this, "DONE!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AjoutLivActivity.this, ListLivActivity.class);
                    intent.putExtra("col", col.getId());
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(AjoutLivActivity.this, "Erreur lors de l'insertion !", Toast.LENGTH_SHORT).show();
                    Log.d("INSERTION :", ""+e);
                    reset();
                }
            }
        });
        bt_recomm = findViewById(R.id.bt_recom);
        bt_recomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset(){
        findViewById(R.id.scrollView2).setVisibility(View.GONE);
        findViewById(R.id.constraint).setVisibility(View.VISIBLE);
        onResume();
    }
}