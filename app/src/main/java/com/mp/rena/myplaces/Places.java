package com.mp.rena.myplaces;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Places implements Serializable {
    String address;
    String lat;
    String lng;

    Places(String lat, String lng, String address){
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}