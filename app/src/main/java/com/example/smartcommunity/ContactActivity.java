package com.example.smartcommunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.smartcommunity.Interface.CallbackFragment;

public class ContactActivity extends AppCompatActivity implements CallbackFragment {

    //Toolbar toolbar;
    Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //toolbar = findViewById(R.id.toolbarcontact);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle("ผู้มาติดต่อ");
        //toolbar.setNavigationIcon(R.drawable.ic_baseline_backspace_24);
        //.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_contact, new Fm_contact_page_1())
                    .commit();
        }
    }


    @Override
    public void someEvent(Fragment fragment) {
        replaceFragment(fragment);
        mFragment = fragment;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_contact, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        Log.d("fragmentINDEX" ,fragment.toString());
        //String name;
//        if(fragment.toString().contains("Fm_contact_page1")){
//            getSupportActionBar().setTitle("ผู้มาติดต่อ");
//        }else if(fragment.toString().contains("Fm_contact_page2")){
//            getSupportActionBar().setTitle("ผู้มาติดต่อ เข้า/ออก");}

    }

}