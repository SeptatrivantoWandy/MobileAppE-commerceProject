package com.example.mcsprojectakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Vector;

public class RegistrationForm extends AppCompatActivity {

    public static final String SEND_UDSIZE = "com.example.mcsprojectakhir.SEND_ID";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    int userDataSize;

    EditText UserName;
    EditText PhoneNumber;
    EditText Password;
    EditText ConfirmPassword;

    TextView TVDate;
    DatePickerDialog.OnDateSetListener setListener;

    RadioGroup radioGroupGenderReg;
    RadioButton genderRadio;

    CheckBox checkBoxATC;

    Button buttonRegister;

    public static Vector<UserDataDBHelper> db_helper_vector = new Vector<>();
    Vector<UserData> UDV = new Vector<>();
    UserDataDBHelper UDdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        UserName = findViewById(R.id.editTextUserNameReg);
        PhoneNumber = findViewById(R.id.editTextPhoneReg);
        Password = findViewById(R.id.editTextTextPasswordReg);
        ConfirmPassword = findViewById(R.id.editTextTextConfirmPasswordReg);
        buttonRegister = findViewById(R.id.buttonRegisterReg);

        TVDate = findViewById(R.id.textViewDOBInput);

        radioGroupGenderReg = findViewById(R.id.radioGroupGenderReg);

        checkBoxATC = findViewById(R.id.checkBoxATC);

        UDdb = new UserDataDBHelper(RegistrationForm.this);

        UDV.clear();
        storeUDInVector();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        TVDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegistrationForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        TVDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int correct = 0;

                if(storeUDInVector() == 1){
                    correct = validation();
                }

                if(checkBoxATC.isChecked() == true) {
                    if(correct == 1){}
                    else{

                        int radioId = radioGroupGenderReg.getCheckedRadioButtonId();
                        genderRadio = findViewById(radioId);

                        String name = UserName.getText().toString().trim();
                        String phonenum = PhoneNumber.getText().toString().trim();
                        String password = Password.getText().toString().trim();
                        String conpasword = ConfirmPassword.getText().toString().trim();
                        String DOB = TVDate.getText().toString().trim();
                        String strGender = genderRadio.getText().toString().trim();
                        long wallet = 0;

                        UserDataDBHelper db_helper = new UserDataDBHelper(RegistrationForm.this);
                        db_helper.insertUD(new UserData("1", name, phonenum, password, DOB, strGender, wallet));

                        db_helper_vector.add(db_helper);


                        userDataSize = UDV.size();

                        Intent intent = new Intent( getApplicationContext(), MainActivity.class);

                        intent.putExtra(SEND_UDSIZE, userDataSize);

                        startActivity(intent);
                        finish();
                    }
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


                UserData obj =  new UserData(cursor.getInt(0) + "", cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
                UDV.add(obj);
            }
            return 1;
        }
    }


    public void checkButton(View v){
        int radioId = radioGroupGenderReg.getCheckedRadioButtonId();

        genderRadio = findViewById(radioId);
    }

    public int validation() {

        if(TextUtils.isEmpty(UserName.getText().toString().trim())){
            UserName.setError("input your username");
            UserName.requestFocus();
            return 1;
        } else if(UserName.getText().toString().trim().length() < 6 || UserName.getText().toString().trim().length() > 12){
            UserName.setError("username must be between 6 and 12 characters");
            UserName.requestFocus();
            return 1;
        }


        if(UDV.size() != 0){
            for(int i = 0 ; i < UDV.size() ; i++){
                if(UserName.getText().toString().trim().contentEquals(UDV.get(i).getUserName())){
                    UserName.setError("username has been used");
                    UserName.requestFocus();
                    return 1;
                }
            }
        }


        if(TextUtils.isEmpty(PhoneNumber.getText().toString().trim())){
            PhoneNumber.setError("input your phone number");
            PhoneNumber.requestFocus();
            return 1;
        } else if(PhoneNumber.getText().toString().trim().length() < 10 || PhoneNumber.getText().toString().trim().length() > 12) {
            PhoneNumber.setError("phone number must be between 10 and 12 digits");
            PhoneNumber.requestFocus();
            return 1;
        }

        if(TextUtils.isEmpty(Password.getText().toString().trim())){
            Password.setError("input your password");
            Password.requestFocus();
            return 1;
        } else if(Password.getText().toString().trim().length() < 8){
            Password.setError("password must be more than 8 characters");
            Password.requestFocus();
            return 1;
        } else if(Password.getText().toString().trim().matches("[a-zA-Z0-9]")){
            Password.setError("password must contains alphanumeric");
            Password.requestFocus();
            return 1;
        }

        if(TextUtils.isEmpty(ConfirmPassword.getText().toString().trim())){
            ConfirmPassword.setError("confirm your password");
            ConfirmPassword.requestFocus();
            return 1;
        } else if(!ConfirmPassword.getText().toString().trim().contentEquals(Password.getText().toString().trim())){
            ConfirmPassword.setError("password do not match");
            ConfirmPassword.requestFocus();
            return 1;
        }

        if(TextUtils.isEmpty(TVDate.getText().toString().trim())){
            Toast.makeText(RegistrationForm.this, "input your date of birth", Toast.LENGTH_SHORT).show();
            return 1;
        }

        int isSelected = radioGroupGenderReg.getCheckedRadioButtonId();

        if(isSelected == -1){

            Toast.makeText(RegistrationForm.this, "select your gender", Toast.LENGTH_SHORT).show();
            return 1;
        }

        if(!checkBoxATC.isChecked()){

            PopUpATC popupatc = new PopUpATC();
            popupatc.show(getSupportFragmentManager(), "example");
            return 1;
        }

        return 0;
    }

}