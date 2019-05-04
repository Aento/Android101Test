package angry.dwarf.com.android101test.ui.movie;

import android.arch.lifecycle.Lifecycle;

import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.ui.base.BasePresenter;

public interface MovieDetailsContract {
    interface View {
        void showMovies(Movie movie);

        Lifecycle getLifecycle();
    }

    interface Presenter extends BasePresenter<MovieDetailsContract.View> {

    }
}
