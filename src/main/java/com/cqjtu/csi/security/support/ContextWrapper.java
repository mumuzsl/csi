package com.cqjtu.csi.security.support;

/**
 * @author mumu
 * @date 2020/1/14
 */
public class ContextWrapper<T> {

    private T context;

    public T get() {
        return context;
    }

    public void set(T context) {
        this.context = context;
    }
}
