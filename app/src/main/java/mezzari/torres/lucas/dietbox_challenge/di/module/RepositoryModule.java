package mezzari.torres.lucas.dietbox_challenge.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mezzari.torres.lucas.dietbox_challenge.network.service.IMovieService;
import mezzari.torres.lucas.dietbox_challenge.network.state.INetworkState;
import mezzari.torres.lucas.dietbox_challenge.persistence.dao.MovieDao;
import mezzari.torres.lucas.dietbox_challenge.repository.IMoviesRepository;
import mezzari.torres.lucas.dietbox_challenge.repository.MoviesRepository;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Module
public final class RepositoryModule {
    @Provides
    @Singleton
    IMoviesRepository providesRepository(INetworkState networkState, IMovieService movieService, MovieDao movieDao) {
        return new MoviesRepository(networkState, movieService, movieDao);
    }
}
