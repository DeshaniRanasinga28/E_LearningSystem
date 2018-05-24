package com.elearning.elearning;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.elearning.elearning.config.Constants;
import com.elearning.elearning.model.Answers;
import com.elearning.elearning.model.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class McqSummaryActivity extends AppCompatActivity {
    //the listview
    ListView listView;

    //database reference to get uploads data
    DatabaseReference mDatabaseReference;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user;

    //list to store uploads data
    List<Answers> answersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_summary);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        checkInternetConnection();
//        Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
//        final String userEmail = user.getEmail();

        answersList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS_USERANSWERS);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Answers answers = postSnapshot.getValue(Answers.class);
                    answersList.add(answers);
                }

                String[] uploads = new String[answersList.size()];
                String currentUser = user.getEmail();

                for (int i = 0; i < uploads.length; i++) {
                        String email = answersList.get(i).getUsername();

                    if(currentUser.equalsIgnoreCase(email)){
                        String userAnswer = answersList.get(i).getUserAnswer();
                        String correctAnswer = answersList.get(i).getCorrectAnswer();

                        uploads[i] = answersList.get(i).getMcqNum() + " Answer : " + answersList.get(i).getUserAnswer() + "\n"
                        + "correct answers = " + answersList.get(i).getCorrectAnswer()
                        + "\nmarks = " + answersList.get(i).getMaek();
                    }
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClickClose(View view){
        startActivity(new Intent(McqSummaryActivity.this, MainActivity.class));
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(McqSummaryActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(McqSummaryActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
