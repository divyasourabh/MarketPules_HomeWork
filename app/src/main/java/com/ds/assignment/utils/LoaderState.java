package com.ds.assignment.utils;

public enum LoaderState {

    LOADING,
    LOADING_FINISHED,
    LOADING_FAILED;

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    LoaderState() {
        message = "";
    }

    public LoaderState setLoaderState(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

}
