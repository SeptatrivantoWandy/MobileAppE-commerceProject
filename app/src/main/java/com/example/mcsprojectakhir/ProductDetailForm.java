package com.example.mcsprojectakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Vector;

public class ProductDetailForm extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";
    public static final String SEND_PRODUCT_ID = "com.example.mcsprojectakhir.SEND_PRODUCT_ID";

    public static Vector<TransactionHistoryData> TDV = new Vector<>();

    public static Vector<UserData> UDV = new Vector<>();

    public static Vector<TransactionDataDBHelper> Tdb_helper_vector = new Vector<>();

    int userId;

    UserData obj_UD;
    TransactionDataDBHelper TDdb;
    UserDataDBHelper UDdb;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);

        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    public static final String DATE_FORMAT = "dd/MM/yy";

    TextView nameTV, minTV, maxTV, priceTV;

    Button confirmBuy;
    Button showLocation;

    int productId;
    String productName;
    String productMin;
    String productMax;
    int productPrice;

    String userName;
    String userPhoneNumber;
    String userPassword;
    String userDOB;
    String userGender;
    long userWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_form);



        TDdb = new TransactionDataDBHelper(ProductDetailForm.this);
        UDdb = new UserDataDBHelper(ProductDetailForm.this);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        nameTV = findViewById(R.id.textViewProductNameDetail);
        minTV = findViewById(R.id.textViewMinimalPlayerDIsplayDetail);
        maxTV = findViewById(R.id.textViewMaximalPlayerDisplayDetail);
        priceTV = findViewById(R.id.textViewPriceDisplayDetail);
        confirmBuy = findViewById(R.id.buttonBuyProduct);
        showLocation = findViewById(R.id.buttonShowLocation);

        Intent intent = getIntent();
        productId = intent.getIntExtra("id", -1);
        productName = intent.getStringExtra("nama");
        productMin = intent.getStringExtra("min");
        productMax = intent.getStringExtra("max");
        productPrice = intent.getIntExtra("harga", 0);
        userId = intent.getIntExtra("userid", -1);

        userName = MainActivity.UDV.get(userId).getUserName();
        userPhoneNumber = MainActivity.UDV.get(userId).getUserPhoneNumber();
        userPassword = MainActivity.UDV.get(userId).getUserPassword();
        userDOB = MainActivity.UDV.get(userId).getUserDOB();
        userGender = MainActivity.UDV.get(userId).getUserGender();
        userWallet = MainActivity.UDV.get(userId).getWallet();

        nameTV.setText(productName);
        minTV.setText(productMin + " players");
        maxTV.setText("" + productMax + " players");
        priceTV.setText("RP." + productPrice);


        TDV.clear();
        storeTransactionDataInVector();


        confirmBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeForm.class);

                if(MainActivity.UDV.get(userId).getWallet() < productPrice){
                    Toast.makeText(ProductDetailForm.this, "not enough wallet, you must top up first", Toast.LENGTH_SHORT).show();

                    TDV.clear();
                    storeTransactionDataInVector();

                } else{
                    obj_UD = new UserData(userId+1+"", userName, userPhoneNumber, userPassword, userDOB, userGender, userWallet - productPrice);

                    boolean isUpdate = UDdb.updateUD(obj_UD);

                    TDdb = new TransactionDataDBHelper(ProductDetailForm.this);
                    TDdb.insertTD(new TransactionHistoryData("1", userId+1+"", productId+"", currentDate));
                    Tdb_helper_vector.add(TDdb);

                    TDV.clear();
                    storeTransactionDataInVector();


                    Toast.makeText(getApplicationContext(), "product purchased", Toast.LENGTH_SHORT).show();

                    intent.putExtra(SEND_ID, userId);
                    startActivity(intent);
                    finish();

                }


            }
        });

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                intent.putExtra(SEND_ID, userId);
                intent.putExtra(SEND_PRODUCT_ID, productId);


                startActivity(intent);

            }
        });


    }

    public int storeTransactionDataInVector () {
        Cursor cursor = TDdb.readAllTransactionData();
        if(cursor.getCount() == 0) {

            return -1;

        } else {


            while (cursor.moveToNext()) {

                TransactionHistoryData obj =  new TransactionHistoryData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                TDV.add(obj);
            }
            return 1;
        }
    }


}