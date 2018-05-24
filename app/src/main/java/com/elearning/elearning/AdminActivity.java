package com.elearning.elearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    private TextView emailTxt;

    Bundle bundle;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        emailTxt =  (TextView)findViewById(R.id.emailTxt);

        bundle = getIntent().getExtras();
        if(bundle != null){
            username = bundle.getString("USERNAME");
            emailTxt.setText(username);
        }

    }

    public void onLogout(View view){
        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
    }

    public void onUserRegistratin(View view){
        startActivity(new Intent(AdminActivity.this, RegistrationActivity.class));
    }

    public void onClickMcq(View view){
            startActivity(new Intent(AdminActivity.this, AddMcqActivity.class));
    }

    public void onClickVideoUpload(View view){
        startActivity(new Intent(AdminActivity.this, AddVideoActivity.class));
    }

    public void onClickDocUpload(View view){
        startActivity(new Intent(AdminActivity.this, UploadDocumentActivity.class));
    }

    public void onClickExUpload(View view){
        startActivity(new Intent(AdminActivity.this, UploadExamPreparationActivity.class));
    }


}
