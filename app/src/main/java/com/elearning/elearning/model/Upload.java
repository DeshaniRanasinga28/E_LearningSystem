package com.elearning.elearning.model;

public class Upload {
    private String module;
    private String name;
    private String url;

    public Upload(String module, String name, String url) {
        this.module = module;
        this.name = name;
        this.url = url;
    }

    public Upload(){

    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
