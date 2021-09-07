package mezzari.torres.lucas.dietbox_challenge.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mezzari.torres.lucas.dietbox_challenge.repository.IMoviesRepository;
import mezzari.torres.lucas.dietbox_challenge.scenes.detail.DetailFragmentViewModel;
import mezzari.torres.lucas.dietbox_challenge.scenes.home.HomeFragmentViewModel;
import mezzari.torres.lucas.dietbox_challenge.scenes.search.SearchFragmentViewModel;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
@Module
public final class ViewModelModule {
    @Provides
    @Singleton
    public HomeFragmentViewModel providesHomeViewModel(IMoviesRepository repository) {
        return new HomeFragmentViewModel(repository);
    }

    @Provides
    @Singleton
    public SearchFragmentViewModel providesSearchViewModel(IMoviesRepository repository) {
        return new SearchFragmentViewModel(repository);
    }

    @Provides
    @Singleton
    public DetailFragmentViewModel providesDetailViewModel(IMoviesRepository repository) {
        return new DetailFragmentViewModel(repository);
    }
}
