package com.example.alex.dstuapp.network;

import android.util.Pair;

import java.util.concurrent.Executors;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ApiUtils {

    private static final Scheduler NETWORK_SCHEDULER = Schedulers.from(Executors.newFixedThreadPool(4));
    private static final Scheduler LOCAL_IO_SCHEDULER = Schedulers.from(Executors.newFixedThreadPool(1));

    public static void executeWithRuntimeException(ApiTask task) {
        try {
            task.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public interface ApiTask {
        void execute() throws Exception;
    }

    public static <T> T executeWithRuntimeExceptionForResult(ApiTaskWithResult<T> task) {
        try {
            return task.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public interface ApiTaskWithResult<T> {
        T execute() throws Exception;
    }

    /*public static <T> T callInTransaction(Callable<T> task) {
        try {
            return TransactionManager.callInTransaction(DatabaseHelper.getInstance().getConnectionSource(), task);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }*/

    public static <S, T> Observable<Pair<S, T>> zipInPair(Observable<S> first, Observable<T> second) {
        return Observable.zip(first, second, Pair::create);
    }

    public static Scheduler networkScheduler() {
        return NETWORK_SCHEDULER;
    }

    public static Scheduler localIoScheduler() {
        return LOCAL_IO_SCHEDULER;
    }



}
