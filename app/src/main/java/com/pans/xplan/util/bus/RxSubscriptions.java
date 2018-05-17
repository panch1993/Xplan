package com.pans.xplan.util.bus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxSubscriptions {
    private static CompositeDisposable mSubscriptions = new CompositeDisposable();

    public static boolean isUnsubscribed() {
        return mSubscriptions.isDisposed();
    }

    public static void add(Disposable s) {
        if (s != null) {
            mSubscriptions.add(s);
        }
    }

    public static void remove(Disposable s) {
        if (s != null) {
            mSubscriptions.remove(s);
        }
    }

    public static void clear() {
        mSubscriptions.clear();
    }

    public static void unsubscribe() {
        mSubscriptions.dispose();
    }
}