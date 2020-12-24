package com.tomrobette.collecotheque;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BookAccess {


    // attribut
    private static BookAccess instance;
    private RequestQueue requestQueue;
    private Activity activity;

    //Constructeur
    public BookAccess(Activity activity){
        this.activity = activity;

    }

    public static synchronized BookAccess getInstance(Activity activity){
        if(instance==null){
            instance = new BookAccess(activity);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(activity);
        }
        return requestQueue;
    }

    /*
    Methodes pour créer les requêtes à placer dans la file d'attente de Volley
     */
    // pour les reponses au format JSONArray
    public JsonArrayRequest doJSONArrayRequest(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("====OnResponse====", "Json Resultat on response : "+response.toString());

                        try {
                            ArrayList<JSONObject> listeDonnees = new ArrayList<JSONObject>();
                            for (int i=0; i<response.length(); i++ ) {
                                listeDonnees.add(response.getJSONObject(i));
                            }

                            // gestion information dans un recycler view de la liste de donnée

                        }catch (Exception e){}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("====OnError====", "Json Resultat du OnError : "+error.toString());

                    }
                }
        );
        return jsonArrayRequest;
    }

    // pour les reponses au formats String
    public StringRequest doStringRequest(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("====OnResponse====", "String - Resultat on response : "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("====OnError====", "String - Resultat du OnError : "+error.toString());
                    }
                });
       return stringRequest;
    }

    // pour les reponses au format JSONObjectRequest
    public JsonObjectRequest doJSONObjectRequestAjout(final String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("====OnResponse====", "Request JSON - Resultat on response : "+response.toString());

                        try {
                            // titre
                            ((TextView)activity.findViewById(R.id.card_tv_title)).setText(response.get("title").toString());
                            // isbn
                            String isbn = response.get("isbn_13").toString();
                            ((TextView)activity.findViewById(R.id.card_tv_isbn)).setText("n°ISBN: "+isbn.substring(1, isbn.length()-1));
                            // image
                            // utilisation de la librairie GLIDE
                            String cover=response.get("covers").toString();
                            cover=cover.substring(1, cover.length()-1);
                            String urlImg="https://covers.openlibrary.org/b/id/"+cover+"-L.jpg";
                            Glide.with(activity).load(urlImg).into((ImageView)activity.findViewById(R.id.card_iv_cover));

                            activity.findViewById(R.id.constraint).setVisibility(View.GONE);
                            activity.findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                        }catch (Exception e){}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("====OnError====", "Request JSON - Resultat du OnError : "+error.toString());

                    }
                }
        );
        return jsonObjectRequest;
    }

    public JsonObjectRequest doJSONObjectRequestVue(final String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("====OnResponse====", "Request JSON - Resultat on response : "+response.toString());

                        try {
                            ((TextView)activity.findViewById(R.id.card_tv_title)).setText(activity.getResources().getString(R.string.vue_liv_titre)+response.get("title").toString());
                            ((TextView)activity.findViewById(R.id.card_tv_isbn)).setText(activity.getResources().getString(R.string.vue_liv_isbn)+response.get("isbn_13").toString());
                            ((TextView)activity.findViewById(R.id.card_tv_pages)).setText(activity.getResources().getString(R.string.vue_liv_pages)+response.get("number_of_pages").toString());
                            ((TextView)activity.findViewById(R.id.card_tv_date_pub)).setText(activity.getResources().getString(R.string.vue_liv_date_pub)+response.get("publish_date").toString());
                            ((TextView)activity.findViewById(R.id.card_tv_editeur)).setText(activity.getResources().getString(R.string.vue_liv_editeur)+response.get("publishers").toString());
                            ((TextView)activity.findViewById(R.id.card_tv_contri)).setText(activity.getResources().getString(R.string.vue_liv_contri));

                            JSONArray jsonArray = response.getJSONArray("contributors");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                VueLivreActivity.listContri.add(""+object.get("name")+", "+object.get("role"));
                            }


                            String cover=response.get("covers").toString();
                            cover=cover.substring(1, cover.length()-1);
                            String urlImg="https://covers.openlibrary.org/b/id/"+cover+"-L.jpg";
                            Glide.with(activity).load(urlImg).into((ImageView)activity.findViewById(R.id.card_iv_cover));

                        }catch (Exception e){}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("====OnError====", "Request JSON - Resultat du OnError : "+error.toString());
                    }
                }
        );
        return jsonObjectRequest;
    }

    /*
    Methodes surchargées pour ajouter les requêtes dans la file d'attente de Volley
     */
    public void addQueue (StringRequest stringRequest){
        this.requestQueue.add(stringRequest);
    }
    public void addQueue (JsonArrayRequest jsonArrayRequest){
        this.requestQueue.add(jsonArrayRequest);
    }
    public void addQueue (JsonObjectRequest jsonObjectRequest){
        this.requestQueue.add(jsonObjectRequest);
    }

    // Récupération d'image via une URL
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
