package com.rw.whoismyusrepresentative;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;


public class Main extends FragmentActivity implements DelegateCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onButtonClick(String url) {
        ListRepresentativesFragment fragment = (ListRepresentativesFragment)  getSupportFragmentManager().findFragmentById(R.id.list_reps_fragment);

        if(fragment != null && fragment.isInLayout()){
            fragment.setEmptyText(url);
        } else {
            Intent intent = new Intent(getApplicationContext(), ListRepresentativesActivity.class);
            intent.putExtra(ListRepresentativesActivity.FINAL_URL, url);
            startActivity(intent);
        }
    }

    @Override
    public void onButtonClick(RepresentativeData data) {
        //ignore for now
    }
}
