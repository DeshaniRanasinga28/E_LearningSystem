package com.elearning.elearning;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elearning.elearning.config.Constants;
import com.elearning.elearning.model.Mcq;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddMcqActivity extends AppCompatActivity {
    private EditText moduleNameEdTxt;
    private EditText moduleCodeEdTxt;
    private EditText questionEdTxt;
    private EditText mcqQuestionNumberEdTxt;
    private EditText answerEdTxt1;
    private EditText answerEdTxt2;
    private EditText answerEdTxt3;
    private EditText correctAnswerEdTxt;

    DatabaseReference databaseMcq;
    FirebaseDatabase firebaseDatabase;
    List<Mcq> mcqList;

    String id;
    String moduleName;
    String moduleCode;
    String questionNum;
    String question;
    String answerOne;
    String answerTwo;
    String answerThree;
    String correctAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mcq);

        databaseMcq = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_MCQ);
        mcqList = new ArrayList<>();
        //Check internet connection
        checkInternetConnection();
        setupView();
    }

    public void setupView()
    {
        moduleNameEdTxt = (EditText)findViewById(R.id.moduleNameEdTxt);
        moduleCodeEdTxt = (EditText)findViewById(R.id.moduleCodeEdTxt);
        mcqQuestionNumberEdTxt = (EditText)findViewById(R.id.mcqQuestionNumberTxtEd);
        questionEdTxt = (EditText)findViewById(R.id.questionEdTxt);
        answerEdTxt1 = (EditText)findViewById(R.id.answerEdTxt1);
        answerEdTxt2 = (EditText)findViewById(R.id.answerEdTxt2);
        answerEdTxt3 = (EditText)findViewById(R.id.answerEdTxt3);
        correctAnswerEdTxt = (EditText)findViewById(R.id.correctAnswerEdTxt);
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(AddMcqActivity.this, "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            Toast.makeText(AddMcqActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void onClickCancle(View view){
        finish();
    }

    public void onClickSumit(View view){
        if(checkInternetConnection() == true){
            moduleName = moduleNameEdTxt.getText().toString();
            moduleCode = moduleCodeEdTxt.getText().toString();
            questionNum = mcqQuestionNumberEdTxt.getText().toString();
            question = questionEdTxt.getText().toString();
            answerOne = answerEdTxt1.getText().toString();
            answerTwo = answerEdTxt2.getText().toString();
            answerThree = answerEdTxt3.getText().toString();
            correctAnswer = correctAnswerEdTxt.getText().toString();

            id = databaseMcq.push().getKey();
            Mcq mcq = new Mcq(id, moduleName, moduleCode, questionNum, question, answerOne, answerTwo, answerThree, correctAnswer);
            databaseMcq.child(id).setValue(mcq);
            Toast.makeText(AddMcqActivity.this, "Successfully Added new Question", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(AddMcqActivity.this, "Network Connection is not Available", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
