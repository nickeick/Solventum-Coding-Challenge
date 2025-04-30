package com.example.solventum.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyResponse {
    private String url;

    public MyResponse() {}

    public MyResponse(String url) {
        this.url = url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
