package com.rw.whoismyusrepresentative;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Hold data for representative click on the rep list
 */
public class RepresentativeData implements Parcelable {

    private String fullName;
    private String party;
    private String state;
    private String district;
    private String phone;
    private String website;

    public RepresentativeData(){}

    public RepresentativeData(Parcel in){
        this.fullName = in.readString();
        this.party = in.readString();
        this.state = in.readString();
        this.district = in.readString();
        this.phone = in.readString();
        this.website = in.readString();
    }

    public String getFullName() {
        return fullName;
    }

    public String getParty() {
        return party;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(fullName);
        out.writeString(party);
        out.writeString(state);
        out.writeString(district);
        out.writeString(phone);
        out.writeString(website);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RepresentativeData createFromParcel(Parcel in) {
            return new RepresentativeData(in);
        }

        public RepresentativeData[] newArray(int size) {
            return new RepresentativeData[size];
        }
    };

}
