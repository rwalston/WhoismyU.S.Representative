package com.rw.whoismyusrepresentative;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ListRepresentativesFragment extends ListFragment {

    private String TAG = ListRepresentativesFragment.class.getSimpleName();
    protected ProgressBar mProgressBar;
    private DelegateCallback delegate;

    private static final String KEY_REP_URL = "url";
    private final String KEY_REP_NAME = "name";
    private final String KEY_REP_STATE = "state";
    private  String mFinalURL = "";
    protected JSONObject mRepListData;
    protected RepresentativeData selectedRep = new RepresentativeData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_representatives, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        if(isNetworkAvaiable()){
            mProgressBar.setVisibility(View.VISIBLE);
            GetRepListTask getRepListTask = new GetRepListTask();
            getRepListTask.execute();
        } else{
            Toast.makeText(getActivity().getBaseContext(), "Network Unavailable", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try{

//            JSONArray jsonResults = mRepListData.getJSONArray("results");
            JSONArray jsonResults = mRepListData.getJSONArray("posts");
            JSONObject jsonRep = jsonResults.getJSONObject(position);
//            //get data from Json for Rep View
//            selectedRep.setFullName(jsonRep.getString("name"));
//            selectedRep.setParty(jsonRep.getString("party"));
//            selectedRep.setState(jsonRep.getString("state"));
//            selectedRep.setDistrict(jsonRep.getString("district"));
//            selectedRep.setPhone(jsonRep.getString("phone"));
//            selectedRep.setWebsite(jsonRep.getString("link"));

            //for testing: since URL issues... used other URL for development purposes
            selectedRep.setFullName(jsonRep.getString("author"));
            selectedRep.setParty(jsonRep.getString("title"));
            selectedRep.setState(jsonRep.getString("title"));
            selectedRep.setDistrict(jsonRep.getString("id"));
            selectedRep.setPhone(jsonRep.getString("date"));
            selectedRep.setWebsite(jsonRep.getString("url"));

            //calling new fragment
            delegate.onButtonClick(selectedRep);

        }catch(JSONException e){
            logException(e);
        }

    }

    private boolean isNetworkAvaiable(){

        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if(null != networkinfo && networkinfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private class GetRepListTask extends AsyncTask<Object, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Object... params) {

            int responseCode = -1;
            JSONObject jsonResponse = null;

            //Try connection to URL
            try {
//                URL whoIsMyRepUrl = new URL("http://whoismyrepresentative.com/getall_sens_bystate.php?state=ME&output=json");
                //blog teamtreehouse is a URL I am testing with since...
                // whoismyrep API does not return legit json as far as I can tell
                // I decided to continue with developement as if it was a json in hopes taht I could figure out why
                // I am having issues with the returned Json... hmm... TODO:FIX
                URL whoIsMyRepUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=20");
                HttpURLConnection connection = (HttpURLConnection) whoIsMyRepUrl.openConnection();
                connection.connect();

                responseCode = connection.getResponseCode();
                //on success, read data into JSON object
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Reader reader = new InputStreamReader(inputStream);
                    int contentLength = connection.getContentLength();
                    char[] charArray = new char[contentLength];
                    reader.read(charArray);
                    String responseData = new String(charArray);

                    jsonResponse = new JSONObject(responseData);
                } else {
                    Log.i(TAG, "Unsuccessful HTTP Response Code: " + responseCode);
                }
            } catch (MalformedURLException e) {
                logException(e);
            } catch (IOException e) {
                logException(e);
            } catch (Exception e) {
                logException(e);
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            mRepListData = result;
            handleRepWebsiteResponse();
        }
    }

    public void handleRepWebsiteResponse() {

        mProgressBar.setVisibility(View.INVISIBLE);

        if (null == mRepListData) {
            showErrorDialog();
        } else {
            try {
//                JSONArray jsonPosts = mRepListData.getJSONArray("results");
                JSONArray jsonPosts = mRepListData.getJSONArray("posts");
                ArrayList<HashMap<String, String>> repList = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < jsonPosts.length(); i++) {
                    JSONObject rep = jsonPosts.getJSONObject(i);
//                    String name = rep.getString("name");
                    String name = rep.getString("title");
                    name = Html.fromHtml(name).toString();
//                    String state = rep.getString("state");
                    String state = rep.getString("author");
                    state = Html.fromHtml(state).toString();

                    HashMap<String, String> repItem = new HashMap<String, String>();
                    repItem.put(KEY_REP_NAME, name);
                    repItem.put(KEY_REP_STATE, state);

                    repList.add(repItem);
                }

                String[] keys = {KEY_REP_NAME, KEY_REP_STATE};
                int[] ids = {android.R.id.text1, android.R.id.text2};
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), repList, android.R.layout.simple_list_item_2, keys, ids);
                setListAdapter(adapter);
            } catch (JSONException e) {
                logException(e);
            }
        }

    }

    private void logException(Exception e) {
        showErrorDialog();
        Log.e(TAG, "Exception caught!", e);
    }

    private void showErrorDialog() {
        DialogFragment newFragment = ErrorDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void setFinalURL (String url){
        mFinalURL = url;
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
