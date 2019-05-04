package angry.dwarf.com.android101test.data.repository;

import java.util.List;

import angry.dwarf.com.android101test.data.model.Movie;
import io.reactivex.Flowable;

public interface MovieDataSource {
    Flowable<List<Movie>> loadMovie(boolean forceRemote);

    void addMovie(Movie movie);

    void clearData();
}
