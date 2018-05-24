package com.elearning.elearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;


public class LearningMaterialActivity extends AppCompatActivity {
    private String currentUser;
    private String module;
    private String code;
    private String lecturer;

    private TextView moduleTxt;
    private TextView codeTxt;
    private TextView lecturerTxt;
    private TextView currentUserTxt;
    private Button singoutBtn;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_material);

        moduleTxt = (TextView)findViewById(R.id.nameTxt);
        codeTxt = (TextView)findViewById(R.id.codeTxt);
        lecturerTxt = (TextView)findViewById(R.id.lecturerTxt);
        currentUserTxt = (TextView)findViewById(R.id.currentUserTxt);
        singoutBtn = (Button)findViewById(R.id.singoutBtn);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
        singoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        Intent details = this.getIntent();
//        currentUser = details.getStringExtra("username");
        module = details.getStringExtra("name");
        code = details.getStringExtra("code");
        lecturer = details.getStringExtra("lecturer");

        currentUserTxt.setText(user.getEmail());
        moduleTxt.setText(module);
        codeTxt.setText(code);
        lecturerTxt.setText(lecturer);
    }

    public void signOut(){
        auth.signOut();
        startActivity(new Intent(LearningMaterialActivity.this, LoginActivity.class));
    }

    public void onClickTestMcq(View view){
        Intent intent = new Intent(LearningMaterialActivity.this, McqTestActivity.class);
        intent.putExtra("MODULE", module);
        intent.putExtra("CODE", code);
        startActivity(intent);
    }

    public void onClickVedio(View view){
        Intent intent = new Intent(LearningMaterialActivity.this, ViewUploadVideo.class);
        intent.putExtra("MODULE", module);
        startActivity(intent);
    }

    public void onClickDocument(View view){
        Intent intent = new Intent(LearningMaterialActivity.this, DocumentViewActivity.class);
        intent.putExtra("MODULE", module);
        startActivity(intent);
    }

    public void onClickExamPaper(View view){
        Intent intent = new Intent(LearningMaterialActivity.this, ViewUploadsActivity.class);
        intent.putExtra("MODULE", module);
        startActivity(intent);
    }


}
