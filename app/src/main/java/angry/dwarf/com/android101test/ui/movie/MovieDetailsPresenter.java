package angry.dwarf.com.android101test.ui.movie;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import javax.inject.Inject;

import angry.dwarf.com.android101test.data.RxBus;
import angry.dwarf.com.android101test.data.model.Movie;
import io.reactivex.disposables.Disposable;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter, LifecycleObserver {

    private Disposable disposable;

    private MovieDetailsContract.View view;

    @Inject
    MovieDetailsPresenter(MovieDetailsContract.View view) {
        this.view = view;
        view.getLifecycle().addObserver(this);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onAttach() {
        disposable = RxBus.subscribe(o -> {
            if (o instanceof Movie) {
                Movie movie = (Movie) o;
                view.showMovies(movie);
            }
        });
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDetach() {
        disposable.dispose();
    }

}
