package com.elearning.elearning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameEdtxt;
    private EditText passwordEdtxt;
    private EditText conPasswordEdtxt;
    private Button submitBtn;
    private Button cancelBtn;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        usernameEdtxt = (EditText) findViewById(R.id.usernameEdtxt);
        passwordEdtxt = (EditText) findViewById(R.id.passwordEdtxt);
        conPasswordEdtxt = (EditText) findViewById(R.id.conPasswordEdtxt);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        cancelBtn = (Button) findViewById(R.id.cancleBtn);

        checkInternetConnection();
        progressDialog = new ProgressDialog(this);

        submitBtn.setOnClickListener(this);

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        }
    }

    private void registerUser(){
        String username = usernameEdtxt.getText().toString().trim();
        String password = passwordEdtxt.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(RegistrationActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if(v == submitBtn){
            registerUser();
        }

        if(v == cancelBtn){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, AdminActivity.class));
        }

    }

    public void onClickClose(View view){
        finish();
        startActivity(new Intent(this, AdminActivity.class));
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(RegistrationActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(RegistrationActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
