package com.elearning.elearning;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEdtxt;
    private EditText passwordEdtxt;
    private Button submitBtn;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEdtxt = (EditText)findViewById(R.id.usernameEdTxt);
        passwordEdtxt = (EditText)findViewById(R.id.passwordEdTxt);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameEdtxt.getText().toString();
                password = passwordEdtxt.getText().toString();

                if(username.equals("admin@gmail.com") && password.equals("123456789")){
                    Intent instant = new Intent(LoginActivity.this, AdminActivity.class);
                    instant.putExtra("USERNAME", username);
                    startActivity(instant);
                }

                else{

                    if(TextUtils.isEmpty(username)){
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(password)){
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    auth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(!task.isSuccessful()){
                                        if(password.length() < 6 ){
                                            usernameEdtxt.setError(getString(R.string.error_password));
                                        }else{
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.putExtra("USERNAME", username);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }

            }
        });
    }
}
