package com.elearning.elearning.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.elearning.AddMcqActivity;
import com.elearning.elearning.R;
import com.elearning.elearning.config.Constants;
import com.elearning.elearning.model.Answers;
import com.elearning.elearning.model.Mcq;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link McqFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link McqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class McqFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View rootView;
    private TextView numberTxt;
    private TextView questionTxt;
    private RadioGroup radioGroup;
    private RadioButton questionOneRdiBtn;
    private RadioButton questionTwoRdiBtn;
    private RadioButton questionThreeRdiBtn;
    private Button submitAnswer;
    private Mcq mcq;

    private String id;
    String moduleName;
    String moduleCode;
    String qname;
    String qcode;

    DatabaseReference databaseUserAnswers;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user;

    public McqFragment() {
        // Required empty public constructor
    }

    public static McqFragment newInstance(int position, Mcq mcq, int n, String moduleName, String code) {
        McqFragment fragment = new McqFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        args.putParcelable("MCQ", mcq);
        args.putInt("VISIBLE", n);
        args.putString("MODULES", moduleName);
        args.putString("CODE", code);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_mcq, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numberTxt = (TextView)view.findViewById(R.id.questionNum);
        questionTxt = (TextView)view.findViewById(R.id.question);
        radioGroup = (RadioGroup)view.findViewById(R.id.multipleAnswers);
        questionOneRdiBtn = (RadioButton) view.findViewById(R.id.questionOne);
        questionTwoRdiBtn = (RadioButton)view.findViewById(R.id.questionTwo);
        questionThreeRdiBtn = (RadioButton)view.findViewById(R.id.questionThree);
        submitAnswer = (Button)view.findViewById(R.id.submitAnswerBtn);

        switch (getArguments().getInt("POSITION")){
            case 0:
                mcq = getArguments().getParcelable("MCQ");
                moduleName = getArguments().getParcelable("MODULES");
                moduleCode = getArguments().getParcelable("CODE");
                qname = mcq.getModuleName().toString();
                setMcqList();
//                Toast.makeText(getActivity(), qname, Toast.LENGTH_LONG).show();
                submitAnswer();
                break;
            case 1:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 2:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 3:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 4:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 5:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 6:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 7:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 8:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
            case 9:
                mcq = getArguments().getParcelable("MCQ");
                setMcqList();
                submitAnswer();
                break;
        }
    }

    public void setMcqList(){
        numberTxt.setText(mcq.getQuestionNum());
        questionTxt.setText(mcq.getQuestion());
        questionOneRdiBtn.setText(mcq.getAnswerOne());
        questionTwoRdiBtn.setText(mcq.getAnswerTwo());
        questionThreeRdiBtn.setText(mcq.getAnswerThree());
    }

    public void submitAnswer(){
        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)rootView.findViewById(selectedId);

                databaseUserAnswers = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS_USERANSWERS);
                if(checkInternetConnection() == true) {

                    auth = FirebaseAuth.getInstance();
                    user = FirebaseAuth.getInstance().getCurrentUser();

                    String userEmail = user.getEmail();
                    String userAnswer = String.valueOf(radioButton.getText());
                    Toast.makeText(getContext(), userAnswer, Toast.LENGTH_SHORT).show();
                    String qNum = mcq.getQuestion();
                    String correctAnswer = mcq.getCorrectAnswer();
                    String mark;

                    if(userAnswer.equalsIgnoreCase(correctAnswer)){
                        Toast.makeText(getActivity(), "Correct", Toast.LENGTH_LONG).show();
                        mark = "1";
                    }else{
                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_LONG).show();
                        mark = "0";
                    }

                    id = databaseUserAnswers.push().getKey();
                    Answers answers = new Answers(id, userEmail, qNum, userAnswer, correctAnswer, mark);
                    databaseUserAnswers.child(id).setValue(answers);

                }else{
                    Toast.makeText(getActivity(), "Network Connection is not Available", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
//            Toast.makeText(getActivity(), "Connected", Toast.LENGTH_LONG).show();
            return true;
        }else {
//            Toast.makeText(getActivity(), "Network Connection is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
