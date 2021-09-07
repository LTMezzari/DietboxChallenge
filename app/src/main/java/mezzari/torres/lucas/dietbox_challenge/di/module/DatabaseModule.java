package mezzari.torres.lucas.dietbox_challenge.di.module;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mezzari.torres.lucas.dietbox_challenge.persistence.AppDatabase;
import mezzari.torres.lucas.dietbox_challenge.persistence.dao.MovieDao;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Module
public final class DatabaseModule {

    private final Application application;

    public DatabaseModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public AppDatabase providesAppDatabase() {
        return Room.databaseBuilder(application, AppDatabase.class, "my-db").build();
    }

    @Provides
    @Singleton
    public MovieDao providesMovieDao(AppDatabase database) {
        return database.getMovieDao();
    }
}
