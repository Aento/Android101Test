package angry.dwarf.com.android101test.data.api;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MovieService {

    @GET("movie/popular")
    Flowable<MoviesResponse_m> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
