package mezzari.torres.lucas.dietbox_challenge.network.service;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.ListWrapper;
import mezzari.torres.lucas.dietbox_challenge.model.NetworkPromise;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public interface IMovieService {
    NetworkPromise<ListWrapper<Movie>> getTrendingMovies(int page);

    NetworkPromise<ListWrapper<Movie>> getSearchMovies(String query, int page);

    NetworkPromise<Movie> getMovie(long id);
}
