package com.rw.whoismyusrepresentative;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ListRepresentativesActivity extends FragmentActivity implements DelegateCallback{

    public static final String FINAL_URL = "final_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check orientation, if landscape, we are finished with this activity
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            finish();
            return;
        }

        setContentView(R.layout.activity_list_representatives);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String url = extras.getString(FINAL_URL);
            ListRepresentativesFragment listRepresentativesFragment = (ListRepresentativesFragment) getSupportFragmentManager().findFragmentById(R.id.list_reps_fragment);
            listRepresentativesFragment.setFinalURL(url);
        }
    }

    @Override
    public void onButtonClick(String url) {
        //ignore for now
    }

    @Override
    public void onButtonClick(RepresentativeData data) {
        RepresentativeFragment fragment = (RepresentativeFragment)  getSupportFragmentManager().findFragmentById(R.id.rep_fragment);

        if(fragment != null && fragment.isInLayout()){
            fragment.setRepData(data);
        } else {
            Intent intent = new Intent(getApplicationContext(), RepresentativeActivity.class);
            intent.putExtra(RepresentativeActivity.KEY_REP_DATA, data);
            startActivity(intent);
        }
    }
}
