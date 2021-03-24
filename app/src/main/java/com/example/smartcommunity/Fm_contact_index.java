package com.example.smartcommunity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.smartcommunity.Interface.CallbackFragment;

public class Fm_contact_index extends Fragment implements View.OnClickListener {

    CallbackFragment mCallbackFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallbackFragment = (CallbackFragment) context;
        }catch (ClassCastException e){
            throw new ClassCastException((context.toString() + "must implement Calback"));
        }
    }

    public  Fm_contact_index(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_contact_index,container,false);
        Toolbar toolbar = view.findViewById(R.id.toolbarcontact);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ข้อมูลผู้ติดต่อ ประวัติ");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_backspace_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.btn_index_confrim);

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()){
            case R.id.card_contact_car:
                //fragment = new Fm_contact_index();
                //mCallback.someEvent(fragment);
        }
        fragment = new Fm_contact_index();
        mCallbackFragment.someEvent(fragment);
    }
}
