package com.rw.whoismyusrepresentative;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RepresentativeFragment extends Fragment {

    RepresentativeData repData = new RepresentativeData();
    TextView name;
    TextView party;
    TextView state;
    TextView district;
    TextView phone_number;
    TextView website;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_representative, container, false);
        // Inflate the layout for this fragment
        name = (TextView)  view.findViewById(R.id.textViewRepName);
        party = (TextView) view.findViewById(R.id.textViewParty);
        state = (TextView) view.findViewById(R.id.textViewState);
        district = (TextView) view.findViewById(R.id.textViewDistrict);
        phone_number = (TextView) view.findViewById(R.id.textViewPhone);
        website = (TextView) view.findViewById(R.id.textViewWebsite);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(repData != null) {
            name.setText(repData.getFullName());
            party.setText(repData.getParty());
            state.setText(repData.getState());
            district.setText(repData.getDistrict());
            phone_number.setText(repData.getPhone());
            website.setText(repData.getWebsite());
        }else {
            name.setText("No Data passed :(");
        }
    }

    public void setRepData(RepresentativeData data){
        repData = data;
    }

}
