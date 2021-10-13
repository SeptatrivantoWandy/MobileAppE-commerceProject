package com.example.mcsprojectakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class HomeForm extends AppCompatActivity {

    RecyclerView transactionRV;

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;
    TextView emptyTrData;

    UserDataDBHelper UDdb;
    TransactionDataDBHelper TDdb;
    ProductDataDBHelper PDdb;

    public static Vector<TransactionHistoryData> TDVi = new Vector<>();
    public static Vector<TransactionHistoryData> TDV = new Vector<>();

    public static Vector<Product> PDV = new Vector<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_form);

        UDdb = new UserDataDBHelper(HomeForm.this);
        TDdb = new TransactionDataDBHelper(HomeForm.this);
        PDdb = new ProductDataDBHelper(HomeForm.this);

        MainActivity.UDV.clear();
        storeUDInVector();

        PDV.clear();
        storePDInVector();

        Intent intent = getIntent();
        userId = intent.getIntExtra(MainActivity.SEND_ID, -1);
        emptyTrData = findViewById(R.id.textViewEmptyData);

        if(userId == -1){
            Intent intentC = getIntent();
            userId = intentC.getIntExtra(ProfileForm.SEND_ID, -1);
            emptyTrData = findViewById(R.id.textViewEmptyData);
        }

        TDV.clear();
        storeTransactionDataInVector();

        TDVi.clear();
        for(int i = 0 ; i < TDV.size() ; i++){
            if(TDV.get(i).getTrUserId().contentEquals(userId+1+"")){
                TDVi.add(new TransactionHistoryData(TDV.get(i).getTrId(), Integer.parseInt(TDV.get(i).getTrUserId())-1+"", Integer.parseInt(TDV.get(i).getTrProductId())-1+"", TDV.get(i).getTrDate()));

            }
        }

        if(TDVi.size() == 0){
            emptyTrData.setText("there is no transaction");
        } else{
            transactionRV = findViewById(R.id.tranHisRV);
            TransactionDataHistoryAdapter tradp = new TransactionDataHistoryAdapter(this, userId);
            tradp.setTransactions(TDVi);

            transactionRV.setAdapter(tradp);
            transactionRV.setLayoutManager(new GridLayoutManager(this, 1));

        }


    }

    public int storeUDInVector () {
        Cursor cursor = UDdb.readAllUserData();

        if(cursor.getCount() == 0) {

            return -1;

        } else {

            while (cursor.moveToNext()) {

                UserData obj =  new UserData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                MainActivity.UDV.add(obj);
            }
            return 1;
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.menuHome:
                return true;
            case R.id.menuStore:


                intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);
                intent.putExtra(SEND_ID, userId);

                startActivity(intent);
                finish();

                return true;

            case R.id.menuProfile:
                intent = new Intent(getApplicationContext(), ProfileForm.class);

                intent.putExtra(SEND_ID, userId);
                startActivity(intent);
                finish();

                return true;

            case R.id.menuLogOut:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }


}