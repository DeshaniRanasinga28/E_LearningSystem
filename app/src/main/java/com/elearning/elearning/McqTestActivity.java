package com.elearning.elearning;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.elearning.elearning.config.Constants;
import com.elearning.elearning.fragment.McqFragment;
import com.elearning.elearning.model.Mcq;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class McqTestActivity extends AppCompatActivity implements McqFragment.OnFragmentInteractionListener{
    private ImageView leftBtn;
    private ImageView rightBtn;

    private String module;
    private String code;

    private int stCurrentPosition = 0;

    DatabaseReference mDatabaseReference;
    List<Mcq> mcqList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_test);

        mcqList = new ArrayList<>();

        leftBtn = (ImageView)findViewById(R.id.setLeftBtn);
        rightBtn = (ImageView)findViewById(R.id.setRightBtn);

        Intent details = this.getIntent();
        module = details.getStringExtra("MODULE");
        code = details.getStringExtra("CODE");

        checkInternetConnection();

        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_MCQ);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Mcq mcq = postSnapshot.getValue(Mcq.class);
                    mcqList.add(mcq);
                }

                String[] mcqs = new String[mcqList.size()];

                int n = 1;
            replaceFragment(McqFragment.newInstance(stCurrentPosition, mcqList.get(stCurrentPosition), n, module, code));
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stCurrentPosition++;
                    if(stCurrentPosition < mcqList.size()){
                        if(stCurrentPosition == 1){
                            leftBtn.setVisibility(View.VISIBLE);
                        }
                        int n = 2;
                        replaceFragment(McqFragment.newInstance(stCurrentPosition, mcqList.get(stCurrentPosition), 2, module, code));

                    }else{
                        startActivity(new Intent(McqTestActivity.this, McqSummaryActivity.class));
                    }
                }
            });

            leftBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v) {
                stCurrentPosition--;
                if(stCurrentPosition == 0){
                    leftBtn.setVisibility(View.GONE);
                }
                onBackPressed();
            }
        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void replaceFragment(android.support.v4.app.Fragment fragment) {
//        steExerciseNumber();
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if(stCurrentPosition == 0){
            ft.setCustomAnimations(R.anim.slide_up,  R.anim.stay, R.anim.slide_in_left, R.anim.slide_out_right);

        }else{
            ft.setCustomAnimations(R.anim.slide_in_right,  R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        ft.replace(R.id.setContainer, fragment);
        ft.addToBackStack(backStateName);
        ft.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
//            steExerciseNumber();
            getSupportFragmentManager().popBackStackImmediate();
//            EventBus.getDefault().post(new MessageEvent(MessageEvent.BACK_PRESS, 2));
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(McqTestActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(McqTestActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
