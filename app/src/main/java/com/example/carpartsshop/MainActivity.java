package com.example.carpartsshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 8765;
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();


    EditText userEmailET;
    EditText passwordET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    Button loginButton;
    Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmailET = (EditText) findViewById(R.id.editTextEmail);
        passwordET =  findViewById(R.id.editTextPassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View view) {

        //animation
        loginButton = (Button)findViewById(R.id.loginButton);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
        loginButton.startAnimation(animation);


        String userEmail = userEmailET.getText().toString();
        String password = passwordET.getText().toString();
        Log.i(LOG_TAG, "Bejelentkezett: "+ userEmail + ", jelszó: "+ password);
        if( password.equals("")  ||  userEmail.equals("")  ) {
            Toast.makeText(MainActivity.this,getString( R.string.fill_all_data), Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(userEmail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startShopping();
                }else{
                Toast.makeText(MainActivity.this, getString(R.string.login_fail) + task.getException().getMessage() , Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    public void register(View view) {
        //animation
        regButton = (Button)findViewById(R.id.registerButton);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
        regButton.startAnimation(animation);


        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAuth.signOut();
       // Toast.makeText(MainActivity.this, "Kijelentkezve" , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();


        if(  !userEmailET.getText().toString().equals("") || !passwordET.getText().toString().equals("") ) {
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("userEmail",userEmailET.getText().toString());
            editor.putString("password", passwordET.getText().toString());
            editor.apply();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startShopping(/*registered user data*/){
        Intent intent = new Intent(this, CarSelectActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

}