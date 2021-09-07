package mezzari.torres.lucas.dietbox_challenge.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import mezzari.torres.lucas.dietbox_challenge.model.Converters;
import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.persistence.dao.MovieDao;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Database(version = 1, exportSchema = false, entities = {Movie.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
