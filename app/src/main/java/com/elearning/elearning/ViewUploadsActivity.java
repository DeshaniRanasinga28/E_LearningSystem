package com.elearning.elearning;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.elearning.elearning.config.Constants;
import com.elearning.elearning.model.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViewUploadsActivity extends AppCompatActivity {

    ListView listView;

    DatabaseReference mDatabaseReference;
    DatabaseReference enrollNode;

    List<Upload> uploadList;
    ArrayList enrollList = new ArrayList();
    Upload upload;

    String module;
    String enrollKey;
    String name;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);

        Intent details = this.getIntent();
        module = details.getStringExtra("MODULE");
        if(module.equalsIgnoreCase("Strategic Marketing")){
            enrollKey = "j0001";
        }
        else{
            Toast.makeText(ViewUploadsActivity.this, "Please check your EnrollKey", Toast.LENGTH_SHORT).show();
        }

        checkInternetConnection();

        listView = (ListView) findViewById(R.id.listView);

        if(enrollKey == "j0001") {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
            mDatabaseReference.child("j0001").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String a = dataSnapshot.getValue().toString();
                    for(DataSnapshot postSnaphot : dataSnapshot.getChildren()){
                        name = (String) postSnaphot.child("name").getValue();
                        url = (String)postSnaphot.child("url").getValue();
                        Toast.makeText(ViewUploadsActivity.this, url, Toast.LENGTH_SHORT).show();

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String downUrl = url;
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(downUrl));
                                startActivity(intent);
                            }
                        });
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Collections.singletonList(name));
                    listView.setAdapter(adapter);
//                    Toast.makeText(ViewUploadsActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(ViewUploadsActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(ViewUploadsActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
