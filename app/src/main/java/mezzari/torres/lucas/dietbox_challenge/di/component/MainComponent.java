package mezzari.torres.lucas.dietbox_challenge.di.component;

import javax.inject.Singleton;

import dagger.Component;
import mezzari.torres.lucas.dietbox_challenge.di.module.DatabaseModule;
import mezzari.torres.lucas.dietbox_challenge.di.module.NetworkModule;
import mezzari.torres.lucas.dietbox_challenge.di.module.RepositoryModule;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Singleton
@Component(modules = {NetworkModule.class, DatabaseModule.class, RepositoryModule.class})
public interface MainComponent {

}
