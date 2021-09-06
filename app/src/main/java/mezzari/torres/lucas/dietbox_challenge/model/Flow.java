package mezzari.torres.lucas.dietbox_challenge.model;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public class Flow<T> {
    private final OnFlowSetUpListener<T> onFlowSetUpListener;
    private OnFlowUpdateListener<T> onFlowUpdateListener;

    @Nullable
    private T value;

    @Nullable
    private Thread thread;

    public Flow(@NotNull T value, OnFlowSetUpListener<T> onFlowSetUpListener) {
        this.value = value;
        this.onFlowSetUpListener = onFlowSetUpListener;
        runDelegate();
    }

    public Flow(OnFlowSetUpListener<T> onFlowSetUpListener) {
        this.onFlowSetUpListener = onFlowSetUpListener;
        runDelegate();
    }

    public void collect(OnFlowUpdateListener<T> onFlowUpdate) {
        this.onFlowUpdateListener = onFlowUpdate;
        postUpdate(this.value);
    }

    public void emit(T value) {
        this.value = value;
        postUpdate(value);
    }

    private void runDelegate() {
        this.thread = new Thread(() -> {
            this.onFlowSetUpListener.onFlowSetUp(this);
        });
        this.thread.start();
    }

    private void postUpdate(T value) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            onFlowUpdateListener.onFlowUpdate(this, value);
        });
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Nullable
    public Thread getThread() {
        return thread;
    }

    public interface OnFlowSetUpListener<T> {
        void onFlowSetUp(Flow<T> flow);
    }

    public interface OnFlowUpdateListener<T> {
        void onFlowUpdate(Flow<T> flow, T value);
    }
}
