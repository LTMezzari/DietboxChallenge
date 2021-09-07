package mezzari.torres.lucas.dietbox_challenge.repository;

import java.util.ArrayList;
import java.util.List;

import mezzari.torres.lucas.dietbox_challenge.model.Flow;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.NetworkBoundResource;
import mezzari.torres.lucas.dietbox_challenge.model.Resource;
import mezzari.torres.lucas.dietbox_challenge.model.ListWrapper;
import mezzari.torres.lucas.dietbox_challenge.model.NetworkPromise;
import mezzari.torres.lucas.dietbox_challenge.network.service.IMovieService;
import mezzari.torres.lucas.dietbox_challenge.network.state.INetworkState;
import mezzari.torres.lucas.dietbox_challenge.persistence.dao.MovieDao;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public final class MoviesRepository implements IMoviesRepository {

    private final INetworkState networkState;
    private final IMovieService service;
    private final MovieDao movieDao;

    public MoviesRepository(INetworkState networkState, IMovieService service, MovieDao movieDao) {
        this.networkState = networkState;
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
                Flow.execute(() -> movieDao.addMovie(data.getResults().toArray(new Movie[0])));
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return networkState.checkNetworkState();
            }

            @Override
            protected Flow<List<Movie>> loadData() {
                return new Flow<>((flow) -> {
                    int maxSize = 20;
                    int startingIndex = maxSize * (page - 1);
                    List<Movie> movies = movieDao.getMovies();
                    int maxIndex = movies.size();
                    int finalIndex = startingIndex + maxSize;
                    if (maxIndex < startingIndex) {
                        flow.emit(new ArrayList<>());
                        return;
                    }
                    flow.emit(movies.subList(startingIndex, Math.min(maxIndex, finalIndex)));
                });
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
                Flow.execute(() -> movieDao.addMovie(data.getResults().toArray(new Movie[0])));
            }

            @Override
            protected boolean shouldFetch(List<Movie> data) {
                return networkState.checkNetworkState();
            }

            @Override
            protected Flow<List<Movie>> loadData() {
                return new Flow<>((flow) -> {
                    int maxSize = 20;
                    int startingIndex = maxSize * (page - 1);
                    List<Movie> movies = new ArrayList<>();
                    for (Movie m : movieDao.getMovies()) {
                        if (m.getTitle().toLowerCase().contains(query)) {
                            movies.add(m);
                        }
                    }
                    int maxIndex = movies.size();
                    int finalIndex = startingIndex + maxSize;
                    if (maxIndex < startingIndex) {
                        flow.emit(new ArrayList<>());
                        return;
                    }
                    flow.emit(movies.subList(startingIndex, Math.min(maxIndex, finalIndex)));
                });
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
                Flow.execute(() -> movieDao.updateMovie(data));
            }

            @Override
            protected boolean shouldFetch(Movie data) {
                return networkState.checkNetworkState();
            }

            @Override
            protected Flow<Movie> loadData() {
                return new Flow<>((flow) -> {
                    flow.emit(movieDao.getMovie(id));
                });
            }

            @Override
            protected NetworkPromise<Movie> fetchData() {
                return service.getMovie(id);
            }
        }.execute();
    }
}
