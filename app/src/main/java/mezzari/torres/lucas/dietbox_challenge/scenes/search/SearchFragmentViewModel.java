package mezzari.torres.lucas.dietbox_challenge.scenes.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.Resource;
import mezzari.torres.lucas.dietbox_challenge.repository.IMoviesRepository;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class SearchFragmentViewModel extends ViewModel {

    private final IMoviesRepository repository;

    private final MutableLiveData<Resource<List<Movie>>> searchResource = new MutableLiveData<>();
    private final LiveData<List<Movie>> movies = Transformations.map(searchResource, Resource::getData);
    private final LiveData<Boolean> isLoading = Transformations.map(searchResource, Resource::isLoading);
    private final LiveData<String> error = Transformations.map(searchResource, Resource::getMessage);

    private final MutableLiveData<String> query = new MutableLiveData<>();

    public SearchFragmentViewModel(IMoviesRepository repository) {
        this.repository = repository;
    }

    public void searchMovies(int page) {
        String query = this.query.getValue();
        if (query == null || query.trim().isEmpty()) {
            return;
        };
        repository.getSearchMovies(query, page).collect((flow, value) -> searchResource.postValue(value));
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public MutableLiveData<String> getQuery() {
        return query;
    }
}
