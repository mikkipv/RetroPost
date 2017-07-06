package com.example.codemac.retropost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by codemac on 16/6/17.
 */

public class login {
    @SerializedName("name")
    private String rname;

    @SerializedName("latitude")
    private String rlat;

    @SerializedName("longitude")
    private String rlong;

    @SerializedName("address")
    private String radd;

    @SerializedName("location")
    private String rloc;

    @SerializedName("image")
    private String image;


    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRlat() {
        return rlat;
    }

    public void setRlat(String rlat) {
        this.rlat = rlat;
    }

    public String getRlong() {
        return rlong;
    }

    public void setRlong(String rlong) {
        this.rlong = rlong;
    }

    public String getRadd() {
        return radd;
    }

    public void setRadd(String radd) {
        this.radd = radd;
    }

    public String getRloc() {
        return rloc;
    }

    public void setRloc(String rloc) {
        this.rloc = rloc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
