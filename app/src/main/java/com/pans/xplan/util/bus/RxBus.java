package com.pans.xplan.util.bus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {
    private static volatile RxBus defaultInstance;

    private final Subject<Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    // 单例RxBus
    public static RxBus get() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 发送一个新的事件
     */
    public void post(Object event) {
        bus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public void reset() {
        defaultInstance = null;
    }

    public <T> Observable<T> change(Class<T> eventType) {
        return bus.ofType(eventType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*
     * 订阅事件
    private void subscribeEvent() {
        RxSubscriptions.remove(disposable);
        disposable = RxBus.get().change(EventMessage.class).subscribe(new RxBusSubscriber<EventMessage>() {
            @Override
            protected void onEvent(EventMessage event) {
                if (event == null || event.getType() != EventMessage.DAY_EVENT) return;
                currUserId = (int) event.getParams().get("userId");
                year = (int) event.getParams().get("year");
                month = (int) event.getParams().get("month");
                day = (int) event.getParams().get("day");
                date = String.format(Locale.getDefault(), Const.Y_M_D_L, year, month, day);
                handleDataRequest((DaySummaryBean) event.getParams().get("daySummaryBean"));
            }
        });
        RxSubscriptions.add(disposable);
    }*/

}
