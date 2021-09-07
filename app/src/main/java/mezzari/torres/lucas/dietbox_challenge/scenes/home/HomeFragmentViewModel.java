package mezzari.torres.lucas.dietbox_challenge.scenes.home;

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
public final class HomeFragmentViewModel extends ViewModel {

    private final IMoviesRepository repository;

    private final MutableLiveData<Resource<List<Movie>>> movieResource = new MutableLiveData<>();
    private final LiveData<List<Movie>> movies = Transformations.map(movieResource, Resource::getData);
    private final LiveData<Boolean> isLoading = Transformations.map(movieResource, Resource::isLoading);
    private final LiveData<String> error = Transformations.map(movieResource, Resource::getMessage);

    public HomeFragmentViewModel(IMoviesRepository repository) {
        this.repository = repository;
    }

    public void loadMovies(int page) {
        repository.getTrendingMovies(page).collect((flow, value) -> movieResource.postValue(value));
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
}
