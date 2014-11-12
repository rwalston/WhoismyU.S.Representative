package com.rw.whoismyusrepresentative;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchFragment extends Fragment {

    private String TAG = SearchFragment.class.getSimpleName();
    private DelegateCallback delegate;
    private Button mSearch;
    protected EditText mEditZip;
    protected EditText mEditLastName;
    protected Spinner mSpinnerState;
    protected JSONObject mRepListData;

    private final String KEY_REP_NAME = "name";
    private final String KEY_REP_STATE = "state";
    private String finalUrlQuery = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mSearch = (Button) view.findViewById(R.id.buttonSearchForRep);
        mEditZip = (EditText) view.findViewById(R.id.editTextZipCode);
        mEditLastName = (EditText) view.findViewById(R.id.editTextLastName);
        mSpinnerState = (Spinner) view.findViewById(R.id.spinnerListOfStates);


        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvaiable()) {
                    String param = "";
                    if (!mEditZip.getText().toString().isEmpty()) {
                        //append zip to query
                        //TODO check that the zip is a zip, aka only numbers and 7 digits, preferable to do a zip check
                        param = "getall_mems.php?zip=" + mEditZip.getText().toString();
                    } else if (!mSpinnerState.getSelectedItem().toString().equals("Select a State")) {
                        //append state to query
                        //TODO get spinner to have defulat blank and select data for query
                        //TOOD search for house and senate
                        param = "getall_sens_bystate.php?state=" + mSpinnerState.getSelectedItem().toString();
                    } else if (!mEditLastName.getText().toString().isEmpty()) {
                        //append Last name to query
                        //TODO do a search for house and senate
                        //TODO check that the name is a name, aka only letters
                        param = "getall_sens_byname.php?name=" + mEditLastName.getText().toString();
                    } else {
                        Toast.makeText(getActivity().getBaseContext(), "Please enter a Zip cod OR a State OR a Last Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                    finalUrlQuery = "http://whoismyrepresentative.com/" + param + "&jquery";
                    Toast.makeText(getActivity().getBaseContext(), finalUrlQuery, Toast.LENGTH_LONG).show();

                    //Calling Fragment
                    delegate.onButtonClick(finalUrlQuery);

                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Network Unavailable", Toast.LENGTH_LONG).show();
                }
            }

            private boolean isNetworkAvaiable() {

                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkinfo = manager.getActiveNetworkInfo();

                boolean isAvailable = false;
                if (null != networkinfo && networkinfo.isConnected()) {
                    isAvailable = true;
                }
                return isAvailable;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private String getFullURL(){
        String url = "http://blog.teamtreehouse.com/api/get_recent_summary/?count=20";

        return url;
    }

   private void logException(Exception e) {
        showErrorDialog();
        Log.e(TAG, "Exception caught!", e);
    }

   private void showErrorDialog() {
       //TODO make less generic by passing in message
       DialogFragment newFragment = ErrorDialogFragment.newInstance();
       newFragment.show(getFragmentManager(), "dialog");
   }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DelegateCallback){
            delegate = (DelegateCallback) activity;
        } else {
            throw new ClassCastException(activity.toString() + "must implement DelegateCallback");
        }
    }
}

