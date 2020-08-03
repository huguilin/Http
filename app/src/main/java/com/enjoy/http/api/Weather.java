/**
 * Copyright 2020 bejson.com
 */
package com.enjoy.http.api;

import com.enjoy.network.beans.AmapBaseResponse;

import java.util.Date;
import java.util.List;

public class Weather extends AmapBaseResponse {

    public String count;
    public int infocode;
    public List<Lives> lives;

    public class Lives {
        public String province;
        public String city;
        public String adcode;
        public String weather;
        public String temperature;
        public String winddirection;
        public String windpower;
        public String humidity;
        public String reporttime;

    }
}