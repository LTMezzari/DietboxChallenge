package mezzari.torres.lucas.dietbox_challenge.di.module;

import android.os.Handler;
import android.os.Looper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mezzari.torres.lucas.dietbox_challenge.BuildConfig;
import mezzari.torres.lucas.dietbox_challenge.network.INetwork;
import mezzari.torres.lucas.dietbox_challenge.network.NetworkImpl;
import mezzari.torres.lucas.dietbox_challenge.network.service.IMovieService;
import mezzari.torres.lucas.dietbox_challenge.network.service.ITmdbApi;
import mezzari.torres.lucas.dietbox_challenge.network.service.MovieService;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Module
public class NetworkModule {
    @Provides
    @Singleton
    public INetwork providesNetwork() {
        return new NetworkImpl(BuildConfig.BASE_URL);
    }

    @Provides
    @Singleton
    public ITmdbApi providesApi(INetwork network) {
        return network.build(ITmdbApi.class);
    }

    @Provides
    @Singleton
    public IMovieService providesMovieService(ITmdbApi iTmdbApi) {
        return new MovieService(iTmdbApi);
    }
}
