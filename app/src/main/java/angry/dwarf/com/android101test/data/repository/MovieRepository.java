package angry.dwarf.com.android101test.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import angry.dwarf.com.android101test.data.model.Movie;
import io.reactivex.Flowable;

public class MovieRepository implements MovieDataSource {

    private MovieDataSource remoteDataSource;
    private MovieDataSource localDataSource;

    private List<Movie> caches;


    @Inject
    public MovieRepository(@Local MovieDataSource localDataSource,
                           @Remote MovieDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;

        caches = new ArrayList<>();
    }

    @Override
    public Flowable<List<Movie>> loadMovie(boolean forceRemote) {
        if (forceRemote) {
            return refreshData();
        } else {
            if (caches.size() > 0) {
                return Flowable.just(caches);
            } else {
                return localDataSource.loadMovie(false)
                        .take(1)
                        .flatMap(Flowable::fromIterable)
                        .doOnNext(movie -> caches.add(movie))
                        .toList()
                        .toFlowable()
                        .filter(list -> !list.isEmpty())
                        .switchIfEmpty(
                                refreshData());
            }
        }
    }

    Flowable<List<Movie>> refreshData() {
        return remoteDataSource.loadMovie(true).doOnNext(list -> {
            caches.clear();
            localDataSource.clearData();
        }).flatMap(Flowable::fromIterable).doOnNext(movie -> {
            caches.add(movie);
            localDataSource.addMovie(movie);
        }).toList().toFlowable();
    }


    public Flowable<Movie> getMovie(long movieId) {
        return Flowable.fromIterable(caches).filter(movie -> movie.getId() == movieId);
    }

    @Override
    public void addMovie(Movie movie) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public void clearData() {
        caches.clear();
        localDataSource.clearData();
    }
}
