package mezzari.torres.lucas.dietbox_challenge.model;

import androidx.annotation.Nullable;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public abstract class Resource<T> {
    @Nullable
    protected T data;

    @Nullable
    protected String message;

    protected boolean isLoading = false;

    private Resource() {}

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public static final class Success<T> extends Resource<T> {
        public Success(T data) {
            this.data = data;
            this.isLoading = false;
            this.message = null;
        }
    }

    public static final class Loading<T> extends Resource<T> {
        public Loading() {
            this.isLoading = true;
            this.data = null;
            this.message = null;
        }
    }

    public static final class Error<T> extends Resource<T> {
        public Error(String message) {
            this.message = message;
            this.isLoading = false;
            this.data = null;
        }

        public Error(String message, T data) {
            this.message = message;
            this.data = data;
            this.isLoading = false;
        }
    }
}
