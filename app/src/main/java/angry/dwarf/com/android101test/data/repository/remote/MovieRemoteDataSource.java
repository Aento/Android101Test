package angry.dwarf.com.android101test.data.repository.remote;

import java.util.List;

import javax.inject.Inject;

import angry.dwarf.com.android101test.data.Config;
import angry.dwarf.com.android101test.data.api.MovieService;
import angry.dwarf.com.android101test.data.api.MoviesResponse_m;
import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.data.repository.MovieDataSource;
import io.reactivex.Flowable;

public class MovieRemoteDataSource implements MovieDataSource {

    private MovieService movieService;

    @Inject
    public MovieRemoteDataSource(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Flowable<List<Movie>> loadMovie(boolean forceRemote) {
        return movieService.getPopularMovies(Config.API_KEY, Config.LANGUAGE, 1)
                .map(MoviesResponse_m::getMovies);
    }

    @Override
    public void addMovie(Movie movie) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public void clearData() {
        throw new UnsupportedOperationException("Unsupported");
    }
}
