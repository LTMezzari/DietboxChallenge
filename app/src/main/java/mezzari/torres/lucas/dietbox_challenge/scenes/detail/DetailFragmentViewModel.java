package mezzari.torres.lucas.dietbox_challenge.scenes.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.Resource;
import mezzari.torres.lucas.dietbox_challenge.repository.IMoviesRepository;
import mezzari.torres.lucas.dietbox_challenge.util.DateUtils;

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

    private final LiveData<String> title = Transformations.map(movie, (value) -> value != null ? value.getTitle() : "");
    private final LiveData<String> releaseDate = Transformations.map(movie, (value) -> value != null ? DateUtils.formatDate(value.getReleaseDate(), "dd/MM/yyyy") : "");
    private final LiveData<Integer> duration = Transformations.map(movie, (value) -> value != null ? value.getRuntime() : 0);
    private final LiveData<String> overview = Transformations.map(movie, (value) -> value != null ? value.getOverview() : "");
    private final LiveData<Integer> appraisal = Transformations.map(movie, (value) -> value != null ? Math.round(value.getScore()) : 0);
    private final LiveData<String> cover = Transformations.map(movie, (value) -> value != null ? value.getPoster() : "");

    public DetailFragmentViewModel(IMoviesRepository repository) {
        this.repository = repository;
    }

    public void getMovieDetails(long id) {
        if (id < 0) {
            movieResource.postValue(new Resource.Error<>("Invalid movie id"));
            return;
        };
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

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getReleaseDate() {
        return releaseDate;
    }

    public LiveData<Integer> getDuration() {
        return duration;
    }

    public LiveData<String> getOverview() {
        return overview;
    }

    public LiveData<Integer> getAppraisal() {
        return appraisal;
    }

    public LiveData<String> getCover() {
        return cover;
    }
}
