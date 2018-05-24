package com.elearning.elearning;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.elearning.adapter.ModuleCustomAdapter;
import com.elearning.elearning.fragment.ModuleFragment;
import com.elearning.elearning.model.Module;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ModuleFragment.OnFragmentInteractionListener{
    private Button signOut;
    private TextView usernameTxt;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOut = (Button)findViewById(R.id.signoutBtn);
        usernameTxt = (TextView)findViewById(R.id.usernameTxt);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        usernameTxt.setText(user.getEmail());

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(MainActivity.this, LearningMaterialActivity.class));
                }
            }
        };

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        ModuleFragment fragment = ModuleFragment.newInstance("","");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        // fragmen container id in first parameter is the  container(Main layout id) of Activity
        // this will manage backstack
        transaction.addToBackStack(null);
        transaction.commit();

        FirebaseInstanceId.getInstance().getToken();
//        Toast.makeText(getApplicationContext(), FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
    }

    public void signOut(){
        auth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
