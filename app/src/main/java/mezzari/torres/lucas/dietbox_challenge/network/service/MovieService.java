package mezzari.torres.lucas.dietbox_challenge.network.service;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.ListWrapper;
import mezzari.torres.lucas.dietbox_challenge.model.NetworkPromise;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public final class MovieService implements IMovieService {

    private final ITmdbApi api;

    public MovieService(ITmdbApi api) {
        this.api = api;
    }

    @Override
    public NetworkPromise<ListWrapper<Movie>> getTrendingMovies(int page) {
        return new NetworkPromise<>((promise) -> {
            api.getTrendingMovies("movie", "week", page).enqueue(promise);
        });
    }

    @Override
    public NetworkPromise<ListWrapper<Movie>> getSearchMovies(String query, int page) {
        return new NetworkPromise<>((promise) -> {
            api.getSearchMovies(query, page).enqueue(promise);
        });
    }

    @Override
    public NetworkPromise<Movie> getMovie(long id) {
        return new NetworkPromise<>((promise) -> {
            api.getMovie(id).enqueue(promise);
        });
    }
}
