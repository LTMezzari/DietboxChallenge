package mezzari.torres.lucas.dietbox_challenge.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mezzari.torres.lucas.dietbox_challenge.repository.IMoviesRepository;
import mezzari.torres.lucas.dietbox_challenge.scenes.home.HomeFragmentViewModel;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
@Module
public final class ViewModelModule {
    @Provides
    @Singleton
    public HomeFragmentViewModel providesViewModel(IMoviesRepository repository) {
        return new HomeFragmentViewModel(repository);
    }
}
