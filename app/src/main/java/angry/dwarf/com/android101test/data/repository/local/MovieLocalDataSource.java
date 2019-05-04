package angry.dwarf.com.android101test.data.repository.local;

import java.util.List;

import javax.inject.Inject;

import angry.dwarf.com.android101test.data.database.MovieDao;
import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.data.repository.MovieDataSource;
import io.reactivex.Flowable;

public class MovieLocalDataSource implements MovieDataSource {

    private MovieDao movieDao;

    @Inject
    public MovieLocalDataSource(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Flowable<List<Movie>> loadMovie(boolean forceRemote) {
        return movieDao.getAllMovies();
    }

    @Override
    public void addMovie(Movie movie) {
        movieDao.insert(movie);
    }

    @Override
    public void clearData() {
        movieDao.deleteAll();
    }
}
