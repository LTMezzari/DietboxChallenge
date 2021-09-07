package mezzari.torres.lucas.dietbox_challenge.repository;

import java.util.List;

import mezzari.torres.lucas.dietbox_challenge.model.Flow;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.Resource;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public interface IMoviesRepository {
    Flow<Resource<List<Movie>>> getTrendingMovies(int page);

    Flow<Resource<List<Movie>>> getSearchMovies(String query, int page);

    Flow<Resource<Movie>> getMovie(long id);
}
