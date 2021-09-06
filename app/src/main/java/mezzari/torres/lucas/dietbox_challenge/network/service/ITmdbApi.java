package mezzari.torres.lucas.dietbox_challenge.network.service;

import mezzari.torres.lucas.dietbox_challenge.model.Movie;
import mezzari.torres.lucas.dietbox_challenge.model.ListWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public interface ITmdbApi {
    @GET("trending/{type}/{interval}")
    Call<ListWrapper<Movie>> getTrendingMovies(
            @Path("type") String type,
            @Path("interval") String interval,
            @Query("page") int page
    );

    @GET("movie/{id}")
    Call<Movie> getMovie(
            @Path("id") long id
    );

    @GET("search/movie")
    Call<ListWrapper<Movie>> getSearchMovies(
            @Query("query") String query,
            @Query("page") int page
    );
}
