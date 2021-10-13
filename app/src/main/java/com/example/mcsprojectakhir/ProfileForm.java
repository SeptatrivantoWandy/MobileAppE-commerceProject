package com.example.mcsprojectakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class ProfileForm extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeForm.class);
        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }


    UserData object_userData;


    TextView tvUserName;
    TextView tvUserGender;
    TextView tvUserPhoneNumber;
    TextView tvUserDOB;
    TextView tvUserWallet;

    String strUserId;

    String userName;
    String userGender;
    String userPhoneNumber;
    String userDOB;
    String userPassword;
    long userWallet;
    int userId;
    long numUserTopUp;

    EditText etUserPassword;

    RadioGroup rgUserTopUp;
    RadioButton topUpNom;

    Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);

        UserDataDBHelper UDdb = new UserDataDBHelper(ProfileForm.this);

        tvUserName = findViewById(R.id.textViewDisplauUserNameProf);
        tvUserGender = findViewById(R.id.textViewDisplayGenderProf);
        tvUserPhoneNumber = findViewById(R.id.textViewDisplayPhoneNumberProf);
        tvUserDOB = findViewById(R.id.textViewDIsplayDOBProf);
        tvUserWallet = findViewById(R.id.textViewWalletDisplayProf);
        buttonConfirm = findViewById(R.id.buttonConfirmProf);
        etUserPassword = findViewById(R.id.editTextTextPasswordProf);
        rgUserTopUp = findViewById(R.id.radioGroupTopUpNomProf);

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);


        userName = MainActivity.UDV.get(userId).getUserName();
        userGender = MainActivity.UDV.get(userId).getUserGender();
        userPhoneNumber = MainActivity.UDV.get(userId).getUserPhoneNumber();
        userDOB = MainActivity.UDV.get(userId).getUserDOB();
        userWallet = MainActivity.UDV.get(userId).getWallet();
        userPassword = MainActivity.UDV.get(userId).getUserPassword();


        tvUserName.setText(userName);
        tvUserWallet.setText("Rp." + userWallet);
        tvUserDOB.setText(userDOB);
        tvUserPhoneNumber.setText(userPhoneNumber);
        tvUserGender.setText(userGender);


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int correct = validation();

                if (correct != 1){
                    int radioId = rgUserTopUp.getCheckedRadioButtonId();
                    topUpNom = findViewById(radioId);

                    if(topUpNom.getText().toString().trim().contentEquals("Rp.250.000")){
                        numUserTopUp = 250000;
                    } else if(topUpNom.getText().toString().trim().contentEquals("Rp.500.000")){
                        numUserTopUp = 500000;
                    } else if(topUpNom.getText().toString().trim().contentEquals("Rp.1.000.000")){
                        numUserTopUp = 1000000;
                    }



                    Intent intent = new Intent(getApplicationContext(), HomeForm.class);
                    intent.putExtra(SEND_ID, userId);

                    object_userData = new UserData(userId + 1 + "", userName, userPhoneNumber, userPassword, userDOB, userGender, userWallet + numUserTopUp);
                    boolean isUpdate = UDdb.updateUD(object_userData);

                    Toast.makeText(getApplicationContext(), "top up sucessful", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                    finish();
                }

            }
        });

    }


    public void checkButton(View v){
        int radioId = rgUserTopUp.getCheckedRadioButtonId();

        topUpNom = findViewById(radioId);
    }


    public int validation(){

        int isSelected = rgUserTopUp.getCheckedRadioButtonId();

        if(isSelected == -1){

            Toast.makeText(ProfileForm.this, "select nominal top up", Toast.LENGTH_SHORT).show();
            return 1;
        }

        else if(TextUtils.isEmpty(etUserPassword.getText())) {
            etUserPassword.setError("input your password");
            etUserPassword.requestFocus();
            return 1;
        } else if(!etUserPassword.getText().toString().contentEquals(userPassword)){
            etUserPassword.setError("password do not match");
            etUserPassword.requestFocus();
            return 1;
        }

        return 0;
    }





}