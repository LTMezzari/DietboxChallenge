package mezzari.torres.lucas.dietbox_challenge.repository;

import java.util.Date;
import java.util.List;

import mezzari.torres.lucas.dietbox_challenge.model.Flow;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.NetworkBoundResource;
import mezzari.torres.lucas.dietbox_challenge.model.Resource;
import mezzari.torres.lucas.dietbox_challenge.network.model.ListWrapper;
import mezzari.torres.lucas.dietbox_challenge.network.model.NetworkPromise;
import mezzari.torres.lucas.dietbox_challenge.network.service.IMovieService;
import mezzari.torres.lucas.dietbox_challenge.persistence.dao.MovieDao;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public class MoviesRepository implements IMoviesRepository {

    private final IMovieService service;
    private final MovieDao movieDao;

    public MoviesRepository(IMovieService service, MovieDao movieDao) {
        this.service = service;
        this.movieDao = movieDao;
    }

    @Override
    public Flow<Resource<List<Movie>>> getTrendingMovies(int page) {
        return new NetworkBoundResource<List<Movie>, ListWrapper<Movie>>() {
            @Override
            protected List<Movie> transform(ListWrapper<Movie> data) {
                return data.getResults();
            }

            @Override
            protected void saveData(ListWrapper<Movie> data) {
                movieDao.addMovie(data.getResults().toArray(new Movie[0]));
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return data.isEmpty() || data.get(0).getLastUpdate().compareTo(new Date()) < 0;
            }

            @Override
            protected List<Movie> loadData() {
                int maxSize = 20;
                int startingIndex = maxSize * page;
                return movieDao.getMovies().subList(startingIndex, startingIndex + maxSize);
            }

            @Override
            protected NetworkPromise<ListWrapper<Movie>> fetchData() {
                return service.getTrendingMovies(page);
            }
        }.execute();
    }

    @Override
    public Flow<Resource<List<Movie>>> getSearchMovies(String query, int page) {
        return new NetworkBoundResource<List<Movie>, ListWrapper<Movie>>() {
            @Override
            protected List<Movie> transform(ListWrapper<Movie> data) {
                return data.getResults();
            }

            @Override
            protected void saveData(ListWrapper<Movie> data) {
                movieDao.addMovie(data.getResults().toArray(new Movie[0]));
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return data.isEmpty() || data.get(0).getLastUpdate().compareTo(new Date()) < 0;
            }

            @Override
            protected List<Movie> loadData() {
                int maxSize = 20;
                int startingIndex = maxSize * page;
                return movieDao.getMovies().subList(startingIndex, startingIndex + maxSize);
            }

            @Override
            protected NetworkPromise<ListWrapper<Movie>> fetchData() {
                return service.getSearchMovies(query, page);
            }
        }.execute();
    }

    @Override
    public Flow<Resource<Movie>> getMovie(long id) {
        return new NetworkBoundResource<Movie, Movie>() {
            @Override
            protected Movie transform(Movie data) {
                return data;
            }

            @Override
            protected void saveData(Movie data) {
                movieDao.addMovie(data);
            }

            @Override
            protected boolean shouldFetch(Movie data) {
                return data == null || data.getLastUpdate().compareTo(new Date()) < 0;
            }

            @Override
            protected Movie loadData() {
                return movieDao.getMovie(id);
            }

            @Override
            protected NetworkPromise<Movie> fetchData() {
                return service.getMovie(id);
            }
        }.execute();
    }
}
