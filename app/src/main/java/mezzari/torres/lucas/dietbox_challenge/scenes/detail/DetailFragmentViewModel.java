package mezzari.torres.lucas.dietbox_challenge.scenes.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.Resource;
import mezzari.torres.lucas.dietbox_challenge.repository.IMoviesRepository;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class DetailFragmentViewModel extends ViewModel {

    private final IMoviesRepository repository;

    private final MutableLiveData<Resource<Movie>> movieResource = new MutableLiveData<>();

    private final LiveData<Movie> movie = Transformations.map(movieResource, Resource::getData);
    private final LiveData<Boolean> isLoading = Transformations.map(movieResource, Resource::isLoading);
    private final LiveData<String> error = Transformations.map(movieResource, Resource::getMessage);

    public DetailFragmentViewModel(IMoviesRepository repository) {
        this.repository = repository;
    }

    public void getMovieDetails(long id) {
        if (id < 0) return;
        repository.getMovie(id).collect((flow, value) -> movieResource.postValue(value));
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
}
