package mezzari.torres.lucas.dietbox_challenge.network.state;

import android.app.Application;

import mezzari.torres.lucas.dietbox_challenge.util.ConnectionUtils;

/**
 * @author Lucas T. Mezzari
 * @since 07/09/2021
 */
public class NetworkStateImpl implements INetworkState {
    private final Application application;

    private boolean isNetworkActive = false;

    public NetworkStateImpl(Application application) {
        this.application = application;
    }

    @Override
    public void setNetworkActive(boolean isActive) {
        this.isNetworkActive = isActive;
    }

    @Override
    public boolean isNetworkActive() {
        return isNetworkActive;
    }

    @Override
    public boolean checkNetworkState() {
        return this.isNetworkActive = ConnectionUtils.isInternetConnected(application);
    }
}
