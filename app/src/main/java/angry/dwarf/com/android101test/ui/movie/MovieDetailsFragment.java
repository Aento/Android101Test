package angry.dwarf.com.android101test.ui.movie;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import angry.dwarf.com.android101test.R;
import angry.dwarf.com.android101test.data.Config;
import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.ui.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends BaseFragment implements MovieDetailsContract.View {

    @BindView(R.id.image_movie) ImageView movieImage;
    @BindView(R.id.movie_title_tv) TextView movieTitle;
    @BindView(R.id.movie_date_tv) TextView movieDate;
    @BindView(R.id.movie_overview_tv) TextView movieOverview;

    private LifecycleRegistry lifecycleRegistry;

    @Inject
    MovieDetailsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inputFragmentView = inflater
                .inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, inputFragmentView);
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        initializePresenter();
        return inputFragmentView;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle(){
        return lifecycleRegistry;
    }

    private void initializePresenter() {
        DaggerMoviedDetailsComponent.builder()
                .movieDetailsPresenterModule(new MovieDetailsPresenterModule(this))
                .movieRepositoryComponent(getMovieRepositoryComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public void showMovies(Movie movie) {
        movieTitle.setText(movie.getTitle());
        movieDate.setText(movie.getReleaseDate());
        movieOverview.setText(movie.getOverview());
        Glide.with(movieImage).load(Config.IMAGE_BASE_URL + "" + movie.getPosterPath())
                .into(movieImage);
    }
}
