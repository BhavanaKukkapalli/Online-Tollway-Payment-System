package com.example.bhavana.gataway_2;


public class msg_fill {

    String v_trip;
    String v_type;


    public msg_fill(String v_trip,String v_type) {

        this.v_trip = v_trip;
        this.v_type=v_type;

    }


    public msg_fill() {

    }

    public String getV_type() {
        return v_type;
    }

    public String getV_trip() {
        return v_trip;
    }

}