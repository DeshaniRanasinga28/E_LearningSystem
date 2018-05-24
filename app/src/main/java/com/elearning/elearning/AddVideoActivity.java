package com.elearning.elearning;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.elearning.elearning.config.Constants;
import com.elearning.elearning.model.Video;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AddVideoActivity extends AppCompatActivity{
    private EditText fileName;
    private EditText videoLink;
    private EditText moduleName;
    private EditText enrollKey;

    private String module;
    private String fileNames;
    private String link;
    private String id;

    StorageReference mStorageReference;
    DatabaseReference databaseVideoLink;
    FirebaseDatabase firebaseDatabase;
    List<Video> videoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        databaseVideoLink = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_VIDEO);

        moduleName = (EditText)findViewById(R.id.moduleName);
        enrollKey = (EditText)findViewById(R.id.enrollKeyEdTxt);
        fileName = (EditText)findViewById(R.id.editTextFileName);
        videoLink = (EditText)findViewById(R.id.linkEdtxt);

        checkInternetConnection();

    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(AddVideoActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(AddVideoActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void onClickCancle(View view){
        finish();
    }

    public void onClickSumit(View view){
        if(checkInternetConnection() == true){

            module = moduleName.getText().toString();
            fileNames = fileName.getText().toString();
            link = videoLink.getText().toString();

            Video video = new Video(module,  fileNames, link);
            databaseVideoLink.child(enrollKey.getText().toString()).child(databaseVideoLink.push().getKey()).setValue(video);
            Toast.makeText(AddVideoActivity.this, "Successfully Added a video", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(AddVideoActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
        }
        finish();
    }


}
