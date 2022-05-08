package com.example.carpartsshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = RegistrationActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 8765;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    EditText userNameET;
    EditText userEmailET;
    EditText passwordET;
    EditText passwordAgainET;
    EditText phoneET;
    EditText addressET;

    private NotificationHandler mNotificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = getIntent().getExtras();
        //int secret_key = bundle.getInt("SECRET_KEY");
        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if(secret_key != 8765) finish();

        setContentView(R.layout.activity_registration);
        userNameET = findViewById(R.id.userNameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        passwordAgainET = findViewById(R.id.passwordAgainEditText);
        userEmailET = findViewById(R.id.userEmail);
        phoneET = findViewById(R.id.phoneEditText);
        addressET = findViewById(R.id.addressEditText);


        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("userEmail","");
        String password = preferences.getString("password","");

        userEmailET.setText(email);
        passwordET.setText(password);

        mAuth = FirebaseAuth.getInstance();

        mNotificationHandler = new NotificationHandler(this);


    }

    public void register(View view) {
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        String email = userEmailET.getText().toString();
        String passwordAgain = passwordAgainET.getText().toString();
        String phoneNumber = phoneET.getText().toString();
        String address = addressET.getText().toString();



        if(userName.equals("") || password.equals("")  || passwordAgain.equals("") || phoneNumber.equals("") || email.equals("") || address.equals("") ) {
            Toast.makeText(RegistrationActivity.this,getString( R.string.fill_all_data), Toast.LENGTH_LONG).show();
            return;
        }
        if (!passwordAgain.equals(password)){
            //Log.e(LOG_TAG,"Nem egyezik a ket jelszo");
            Toast.makeText(RegistrationActivity.this, getString(R.string.two_password_not_same), Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(LOG_TAG, "Regisztrált "+ userName + ", jelszó: "+ password +",email:"+ email);
        //TODO A regisztrációs funkcionalitás

        //

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, getString(R.string.registration_succesfull), Toast.LENGTH_LONG).show();
                    mNotificationHandler.sendNotification("Üdvözöljük a frissen regisztrált vásárlónkat. Kupon:UJ_R3G");
                    startShopping();
                }else{
                    Toast.makeText(RegistrationActivity.this, getString(R.string.registration_fail) + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }
    private void startShopping(/*registered user data*/){
        Intent intent = new Intent(this, CarSelectActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }
}