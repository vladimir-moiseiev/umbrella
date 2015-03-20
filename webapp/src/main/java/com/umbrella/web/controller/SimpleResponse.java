package com.umbrella.web.controller;


public class SimpleResponse<T> {

    private final T result;

    public SimpleResponse(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public static <T> SimpleResponse<T> create(T result){
        return new SimpleResponse<>(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleResponse that = (SimpleResponse) o;

        if (result != null ? !result.equals(that.result) : that.result != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }
}