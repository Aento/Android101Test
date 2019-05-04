package angry.dwarf.com.android101test.ui.movie;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import javax.inject.Inject;

import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.data.repository.MovieRepository;
import angry.dwarf.com.android101test.utils.RunOn;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static angry.dwarf.com.android101test.utils.SchedulerType.IO;
import static angry.dwarf.com.android101test.utils.SchedulerType.UI;

public class MoviesPresenter implements MoviesContract.Presenter, LifecycleObserver  {

    private MovieRepository repository;

    private MoviesContract.View view;

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    private CompositeDisposable disposeBag;

    @Inject MoviesPresenter(MovieRepository repository, MoviesContract.View view,
                   @RunOn(IO) Scheduler ioScheduler, @RunOn(UI) Scheduler uiScheduler) {
        this.repository = repository;
        this.view = view;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        disposeBag = new CompositeDisposable();
    }

    @Override
    public void loadMovies(boolean onlineRequired) {
        view.clearMovies();

        Disposable disposable = repository.loadMovie(onlineRequired)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
        disposeBag.add(disposable);
    }



    @Override
    public void getMovie(long movieId) {
        Disposable disposable = repository.getMovie(movieId)
                .filter(movie -> movie != null)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(movie -> view.showMovieDetail(movie));
        disposeBag.add(disposable);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadMovies(false);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    private void handleReturnedData(List<Movie> list) {
        view.stopLoadingIndicator();
        if (list != null && !list.isEmpty()) {
            view.showMovies(list);
        } else {
            view.showNoDataMessage();
        }
    }

    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        view.showErrorMessage(error.getLocalizedMessage());
    }
}
