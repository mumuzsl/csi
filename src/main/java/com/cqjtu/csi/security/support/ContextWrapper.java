package com.cqjtu.csi.security.support;

public class ContextWrapper<T> {

    private T context;

    public T get() {
        return context;
    }

    public void set(T context) {
        this.context = context;
    }
}
