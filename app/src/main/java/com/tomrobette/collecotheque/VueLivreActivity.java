package com.tomrobette.collecotheque;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class VueLivreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LocalAccess localAccess = new LocalAccess(this);
    private Livre livre;
    private Boolean gotExtra;
    private BookAccess bookAccess;
    public static List<String> listContri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_livre);
        init();
    }



    private void getExtraLiv(){
        try {
            Bundle extras = getIntent().getExtras();
            String isbn = (String) extras.getSerializable("liv");
            if (Session.isTest())
                Toast.makeText(VueLivreActivity.this, isbn+"!", Toast.LENGTH_SHORT).show();
            livre = (Livre) localAccess.getLivByISBN(isbn);
            if (Session.isTest())
                Toast.makeText(VueLivreActivity.this, livre.getTitre()+"!", Toast.LENGTH_SHORT).show();
            gotExtra=true;
        } catch (Exception e) {
            gotExtra=false;
        }
    }

    private void init(){
        VueLivreActivity.listContri = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int index) {
                return null;
            }

            @Override
            public String set(int index, String element) {
                return null;
            }

            @Override
            public void add(int index, String element) {

            }

            @Override
            public String remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<String> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        bookAccess = new BookAccess(VueLivreActivity.this);
        bookAccess.getInstance(VueLivreActivity.this);
        bookAccess.getRequestQueue();
        getExtraLiv();
        String isbn = (String) livre.getIsbn();
        Log.d("ISBN : ", isbn);
        if (!isbn.isEmpty()) {
            if (Session.getUser() != null){
                if (isbn.length() > 0 && isbn.length() < 14) {
                    bookAccess.addQueue(bookAccess.doJSONObjectRequestVue("https://openlibrary.org/isbn/" + isbn + ".json"));
                } else {
                    Toast.makeText(VueLivreActivity.this, "ISBN inccorect", Toast.LENGTH_SHORT).show();
                }
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.list_contri);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterLine(listContri));
    }
}