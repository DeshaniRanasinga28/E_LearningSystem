package com.elearning.elearning.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elearning.elearning.R;
import com.elearning.elearning.model.Module;

import java.util.ArrayList;

public class ModuleCustomAdapter extends ArrayAdapter<Module>{
    ArrayList<Module> moduleList = new ArrayList<>();


    public ModuleCustomAdapter(Context context, int resource, ArrayList<Module> object) {
        super(context, resource, object);
        moduleList = object;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.module_item_row, null);
        TextView moduleName = (TextView)v.findViewById(R.id.moduleNameTxt);
        TextView moduleCode = (TextView)v.findViewById(R.id.moduleCodeTxt);
        TextView moduleLecturer = (TextView)v.findViewById(R.id.leecturerNameTxt);

        moduleName.setText(moduleList.get(position).getName());
        moduleCode.setText(moduleList.get(position).getCode());
        moduleLecturer.setText(moduleList.get(position).getLecturer());

        return v;
    }
}
