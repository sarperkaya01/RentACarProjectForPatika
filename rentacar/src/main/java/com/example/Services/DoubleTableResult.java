package com.example.Services;


public class DoubleTableResult<T,R> {

    private final T t;
    private final R r;

    public DoubleTableResult(T t, R r) {
        this.t = t;
        this.r = r;
    }

    // Getter metotları
    public R getR() {
        return r;
    }

    public T getT() {
        return t;
    }

}
