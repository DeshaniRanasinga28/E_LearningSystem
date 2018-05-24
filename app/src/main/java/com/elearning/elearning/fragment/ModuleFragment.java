package com.elearning.elearning.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elearning.elearning.LearningMaterialActivity;
import com.elearning.elearning.R;
import com.elearning.elearning.adapter.ModuleCustomAdapter;
import com.elearning.elearning.model.Module;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModuleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModuleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    ArrayList<Module> moduleArrayList = new ArrayList<>();
    ModuleCustomAdapter adapter;
    View rootView;

    public ModuleFragment() {
        // Required empty public constructor
    }

    public static ModuleFragment newInstance(String param1, String param2) {
        ModuleFragment fragment = new ModuleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_module, container, false);
        setupView();
        return rootView;
    }

    public void setupView(){
        listView = (ListView) rootView.findViewById(R.id.listView);
        modulesListData();
        adapter = new ModuleCustomAdapter(getActivity(), R.layout.module_item_row, moduleArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {

//                String userEmail = getArguments().getString("USERNAME");
                Module module = moduleArrayList.get(position);
                String moduleName = module.getName();
                String moduleCode = module.getCode();
                String lecturerName = module.getLecturer();

                Intent details = new Intent(getActivity(), LearningMaterialActivity.class);
//                details.putExtra("username", userEmail);
                details.putExtra("name", moduleName);
                details.putExtra("code", moduleCode);
                details.putExtra("lecturer", lecturerName);
                startActivity(details);
            }

        });
    }


    public void modulesListData(){
            moduleArrayList.add(new Module("Strategic Marketing", "A-001", "Miss Montera Fernando"));
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
