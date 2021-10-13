package com.example.mcsprojectakhir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class StoreFormRecyclerView extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;
    int productSize;

    public static Vector<ProductDataDBHelper> db_helper_vector = new Vector<>();
    public static Vector<Product> PDV = new Vector<>();
    ProductDataDBHelper PDdb;

    RecyclerView productRV;

    int productId;
    String productName;
    int productMinPlayer;
    int productMaxPlayer;
    int productPrice;
    String productCreated;
    String longitude;
    String latitude;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeForm.class);
        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_form_recycler_view);


        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);

        productRV = findViewById(R.id.productRV);

        PDdb = new ProductDataDBHelper(StoreFormRecyclerView.this);

        PDV.clear();
        storePDInVector();

        productRV.setLayoutManager(new GridLayoutManager(this, 1));
        ProductAdapter adp = new ProductAdapter(userId, this);
        adp.setProducts(PDV);
        productRV.setAdapter(adp);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/b/5eb51c6947a2266b1474d701/7";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        productId = i;
                        productName = item.getString("name");
                        productMinPlayer = item.getInt("min_player");
                        productMaxPlayer = item.getInt("max_player");
                        productPrice = item.getInt("price");
                        productCreated = item.getString("created_at");
                        latitude = item.getString("latitude");
                        longitude = item.getString("longitude");

                        if(PDV.size() != 0){
                            for(int j = 0 ; j < PDV.size() ; j++){
                                if(productName.contentEquals(PDV.get(j).getProductName())){

                                    break;
                                } else if(j == PDV.size()-1){



                                    ProductDataDBHelper db_helper = new ProductDataDBHelper(StoreFormRecyclerView.this);
                                    db_helper.insertPD(new Product("1", productName, productMinPlayer+"", productMaxPlayer+"", productPrice, productCreated, longitude, latitude));
                                    db_helper_vector.add(db_helper);
                                    adp.notifyDataSetChanged();


                                    productSize = PDV.size();

                                }
                            }
                        }else{


                            ProductDataDBHelper db_helper = new ProductDataDBHelper(StoreFormRecyclerView.this);
                            db_helper.insertPD(new Product("1", productName, productMinPlayer+"", productMaxPlayer+"", productPrice, productCreated, longitude, latitude));
                            db_helper_vector.add(db_helper);
                            adp.notifyDataSetChanged();

                            productSize = PDV.size();

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                PDV.clear();
                storePDInVector();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);


    }


    public int storePDInVector () {
        Cursor cursor = PDdb.readAllProductData();
        if(cursor.getCount() == 0) {

            return -1;

        } else {

            while (cursor.moveToNext()) {

                Product obj =  new Product(cursor.getInt(0) + "", cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                PDV.add(obj);

            }
            return 1;
        }
    }

}