
/*
Mobile Community Solution LAB
BA03

1. 2301869512 - Septatrivanto Wandy -
2. 2301864120 - Sie Yesaya Selvix Sindhunata
3. 2301901596 - Aurel Theodore Sondakh

 */




package com.example.mcsprojectakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity{

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    public static final int REQUEST_CODE_LOGIN = 1;

    Button buttonRegistration;
    Button buttonLogin;
    EditText userName;
    EditText password;

    int userIdSend;

    UserDataDBHelper UDdb;
    public static Vector<UserData> UDV = new Vector<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegistration = findViewById(R.id.registerButton);
        buttonLogin = findViewById(R.id.loginButton);
        userName = findViewById(R.id.editTextTextUserName);
        password = findViewById(R.id.editTextPassword);

        UDdb = new UserDataDBHelper(MainActivity.this);

        UDV.clear();
        storeUDInVector();

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() ,RegistrationForm.class);
                startActivity(intent);
                finish();

            }
        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validation() == 1){

                    Intent intent = new Intent(getApplicationContext(), HomeForm.class);

                    intent.putExtra(SEND_ID, userIdSend);


                    startActivity(intent);
                    finish();


                }

            }
        });

    }

    public int storeUDInVector () {
        Cursor cursor = UDdb.readAllUserData();

        if(cursor.getCount() == 0) {

            return -1;

        } else {

            while (cursor.moveToNext()) {

                UserData obj =  new UserData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                UDV.add(obj);
            }
            return 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_LOGIN){
            if(resultCode == Activity.RESULT_OK){

            }
        }
    }

    public int validation(){

        String strUserName = userName.getText().toString().trim();
        String strPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(userName.getText())) {
            userName.setError("input your username");
            userName.requestFocus();
            return -1;
        }
        else if(TextUtils.isEmpty(password.getText())) {
            password.setError("input your password");
            password.requestFocus();
            return -1;
        }

        else if(UDV.size() == 0){
            Error();
            return 0;
        }

        else if(UDV.size() != 0){
            for(int i = 0 ; i < UDV.size() ; i++){
                if(strUserName.contentEquals(UDV.get(i).getUserName()) && strPassword.contentEquals(UDV.get(i).getUserPassword())){
                    userIdSend = i;


                    return 1;
                }

            }

            password.setError("either username or password cannot be found or false");
            password.requestFocus();
            return 0;

        }

        return  0;
    }

    public void Error(){
        password.setError("either username or password cannot be found or false");
        password.requestFocus();
        return;
    }

}