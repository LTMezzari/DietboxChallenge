package mezzari.torres.lucas.dietbox_challenge.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public final class NetworkPromise<T> implements Callback<T> {

    private final OnPromiseSetUpListener<T> onPromiseSetUpListener;
    private OnPromiseSuccessfulListener<T> onPromiseSuccessfulListener;
    private OnPromiseFailedListener<T> onPromiseFailedListener;

    public NetworkPromise(OnPromiseSetUpListener<T> onPromiseSetUpListener) {
        this.onPromiseSetUpListener = onPromiseSetUpListener;
    }

    @Nullable
    private Call<T> call;

    @Nullable
    private Throwable throwable;

    @Nullable
    private Response<T> response;

    public NetworkPromise<T> onSuccess(OnPromiseSuccessfulListener<T> onPromiseSuccessfulListener) {
        this.onPromiseSuccessfulListener = onPromiseSuccessfulListener;
        if (onPromiseFailedListener != null) {
            onPromiseSetUpListener.onPromiseSetUp(this);
        }
        return this;
    }

    public NetworkPromise<T> onFailure(OnPromiseFailedListener<T> onPromiseFailedListener) {
        this.onPromiseFailedListener = onPromiseFailedListener;
        if (onPromiseSuccessfulListener != null) {
            onPromiseSetUpListener.onPromiseSetUp(this);
        }
        return this;
    }

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        this.call = call;
        this.response = response;
        if (response.isSuccessful()) {
            this.onPromiseSuccessfulListener.onSuccessful(this, response.body());
            return;
        }
        this.onPromiseFailedListener.onFailure(this, response.message());
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        this.call = call;
        this.throwable = t;
        this.onPromiseFailedListener.onFailure(this, t.getMessage());
    }

    @Nullable
    public Response<T> getResponse() {
        return response;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    @Nullable
    public Call<T> getCall() {
        return call;
    }

    public interface OnPromiseSetUpListener<T> {
        void onPromiseSetUp(NetworkPromise<T> promise);
    }

    public interface OnPromiseSuccessfulListener<T> {
        void onSuccessful(@NotNull NetworkPromise<T> promise, @Nullable T data);
    }

    public interface OnPromiseFailedListener<T> {
        void onFailure(@NotNull NetworkPromise<T> promise, @Nullable String error);
    }
}
