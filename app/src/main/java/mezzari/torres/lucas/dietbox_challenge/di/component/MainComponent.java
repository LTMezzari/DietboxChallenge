package mezzari.torres.lucas.dietbox_challenge.di.component;

import javax.inject.Singleton;

import dagger.Component;
import mezzari.torres.lucas.dietbox_challenge.di.module.DatabaseModule;
import mezzari.torres.lucas.dietbox_challenge.di.module.NetworkModule;
import mezzari.torres.lucas.dietbox_challenge.di.module.RepositoryModule;
import mezzari.torres.lucas.dietbox_challenge.di.module.ViewModelModule;
import mezzari.torres.lucas.dietbox_challenge.scenes.detail.DetailFragment;
import mezzari.torres.lucas.dietbox_challenge.scenes.home.HomeFragment;
import mezzari.torres.lucas.dietbox_challenge.scenes.search.SearchFragment;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Singleton
@Component(modules = {NetworkModule.class, DatabaseModule.class, RepositoryModule.class, ViewModelModule.class})
public interface MainComponent {

    // --------------------------- Home Fragment

    void inject(HomeFragment fragment);

    // --------------------------- Search Fragment

    void inject(SearchFragment fragment);

    // --------------------------- Detail Fragment

    void inject(DetailFragment fragment);
}
