package com.elearning.elearning.model;

import java.util.ArrayList;

public class Enroll {
    private String enrollKey;

    public Enroll(String enrollKey) {
        this.enrollKey = enrollKey;
    }

    public Enroll(){

    }

    public String getEnrollKey() {
        return enrollKey;
    }

    public void setEnrollKey(String enrollKey) {
        this.enrollKey = enrollKey;
    }

}
