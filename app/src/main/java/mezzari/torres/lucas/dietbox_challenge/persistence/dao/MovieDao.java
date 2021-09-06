package mezzari.torres.lucas.dietbox_challenge.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
@Dao
public interface MovieDao {
    @Transaction
    @Query("SELECT * FROM movie")
    List<Movie> getMovies();

    @Transaction
    @Query("SELECT * FROM movie WHERE movieId = :id")
    Movie getMovie(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Movie... movies);
}
