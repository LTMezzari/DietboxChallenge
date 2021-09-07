package mezzari.torres.lucas.dietbox_challenge.di.module;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mezzari.torres.lucas.dietbox_challenge.network.state.INetworkState;
import mezzari.torres.lucas.dietbox_challenge.network.state.NetworkStateImpl;

/**
 * @author Lucas T. Mezzari
 * @since 07/09/2021
 */
@Module
public final class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public INetworkState providesNetworkState() {
        return new NetworkStateImpl(application);
    }
}