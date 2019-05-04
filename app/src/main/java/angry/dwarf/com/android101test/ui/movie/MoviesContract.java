package angry.dwarf.com.android101test.ui.movie;


import java.util.List;

import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.ui.base.BasePresenter;

public interface MoviesContract {
    interface View {
        void showMovies(List<Movie> movies);

        void clearMovies();

        void showNoDataMessage();

        void showErrorMessage(String error);

        void showMovieDetail(Movie movie);

        void stopLoadingIndicator();

    }

    interface Presenter extends BasePresenter<MoviesContract.View> {
        void loadMovies(boolean onlineRequired);

        void getMovie(long movieId);

    }
}
