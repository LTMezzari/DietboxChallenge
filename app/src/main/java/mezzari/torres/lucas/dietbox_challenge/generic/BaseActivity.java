package mezzari.torres.lucas.dietbox_challenge.generic;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import mezzari.torres.lucas.dietbox_challenge.R;
import mezzari.torres.lucas.dietbox_challenge.util.ConnectionUtils;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConnectionUtils.getConnectivityManager(this)
                    .registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(@NonNull Network network) {
                            super.onAvailable(network);
                            new Handler(Looper.getMainLooper()).post(() -> onConnectionAvailable(network));
                        }

                        @Override
                        public void onLost(@NonNull Network network) {
                            super.onLost(network);
                            new Handler(Looper.getMainLooper()).post(() -> onConnectionUnavailable(network));
                        }

                        @Override
                        public void onUnavailable() {
                            super.onUnavailable();
                            new Handler(Looper.getMainLooper()).post(() -> onConnectionUnavailable(null));
                        }
                    });
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        getSupportActionBar().setTitle(title);
    }

    protected void changeToolbarVisibility(boolean shouldShowActionBar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        if (shouldShowActionBar) {
            actionBar.show();
            return;
        }
        actionBar.hide();
    }

    protected void onConnectionAvailable(@NotNull Network network) {}

    protected void onConnectionUnavailable(@Nullable Network network) {}
}
