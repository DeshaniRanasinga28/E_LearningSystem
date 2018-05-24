package com.elearning.elearning.model;

public class Module {
    private String name;
    private String code;
    private String lecturer;

    public Module(String name, String code, String lecturer) {
        this.name = name;
        this.code = code;
        this.lecturer = lecturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
