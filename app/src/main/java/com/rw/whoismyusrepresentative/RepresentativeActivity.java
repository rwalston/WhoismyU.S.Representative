package com.rw.whoismyusrepresentative;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RepresentativeActivity extends FragmentActivity {

    public static final String KEY_REP_DATA = "passRepData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check orientation, if landscape, we are finished with this activity
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            finish();
            return;
        }

        setContentView(R.layout.activity_representative);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            RepresentativeData data = extras.getParcelable(KEY_REP_DATA);
            RepresentativeFragment representativeFragment = (RepresentativeFragment) getSupportFragmentManager().findFragmentById(R.id.rep_fragment);
            representativeFragment.setRepData(data);
        }
    }
}
