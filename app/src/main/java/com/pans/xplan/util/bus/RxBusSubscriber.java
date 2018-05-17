package com.pans.xplan.util.bus;

import io.reactivex.functions.Consumer;

public abstract class RxBusSubscriber<T> implements Consumer<T> {

    @Override
    public void accept(T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onEvent(T t);
}
