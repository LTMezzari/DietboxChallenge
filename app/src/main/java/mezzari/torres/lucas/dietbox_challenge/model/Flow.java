package mezzari.torres.lucas.dietbox_challenge.model;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public final class Flow<T> {
    private final OnFlowSetUpListener<T> onFlowSetUpListener;
    private OnFlowUpdateListener<T> onFlowUpdateListener;

    @Nullable
    private T value;

    private final Executor executor = Executors.newSingleThreadExecutor();

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
        if (this.value != null) {
            postUpdate(this.value);
        }
    }

    public void emit(T value) {
        this.value = value;
        postUpdate(value);
    }

    private void runDelegate() {
        this.executor.execute(() -> this.onFlowSetUpListener.onFlowSetUp(this));
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

    public static void execute(Runnable runnable) {
        new Flow<>((flow) -> runnable.run());
    }

    public interface OnFlowSetUpListener<T> {
        void onFlowSetUp(Flow<T> flow);
    }

    public interface OnFlowUpdateListener<T> {
        void onFlowUpdate(Flow<T> flow, T value);
    }
}
