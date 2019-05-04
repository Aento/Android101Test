package angry.dwarf.com.android101test.ui.movie;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import angry.dwarf.com.android101test.R;
import angry.dwarf.com.android101test.data.RxBus;
import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.ui.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends BaseFragment implements MoviesContract.View {

    @BindView(R.id.recycler_movie)
    RecyclerView movieRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.text_notification)
    TextView notificationText;
    private MovieAdapter adapter;
    private OnItemClickListener mListener;
    @Inject
    MoviesPresenter presenter;

    private static final int BASE_COLUMN_NUMBER = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inputFragmentView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, inputFragmentView);
        initializePresenter();
        setupWidgets();
        return inputFragmentView;
    }



    private void setupWidgets() {
        adapter = new MovieAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(adapter);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), calculateNumberOfColumns(BASE_COLUMN_NUMBER)));
        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(
                Objects.requireNonNull(getContext()), R.dimen.item_offset);
        movieRecyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(
                (view, position) -> presenter.getMovie(adapter.getItem(position).getId()));

        refreshLayout.setOnRefreshListener(() -> presenter.loadMovies(true));
        notificationText.setVisibility(View.GONE);
    }


    private void initializePresenter() {
        DaggerMoviesComponent.builder()
                .moviesPresenterModule(new MoviesPresenterModule(this))
                .movieRepositoryComponent(getMovieRepositoryComponent())
                .build()
                .inject(this);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        notificationText.setVisibility(View.GONE);
        adapter.replaceData(movies);
        if(movies.size() > 0)
            RxBus.publish(movies.get(0));
    }

    @Override
    public void clearMovies() { adapter.clearData(); }

    @Override
    public void showNoDataMessage() {
        showNotification(getString(R.string.no_data));
    }

    @Override
    public void showErrorMessage(String error) {
        showNotification(error);
    }

    @Override
    public void showMovieDetail(Movie movie) {
        RxBus.publish(movie);
        this.mListener.onClick(movie);
    }

    @Override
    public void stopLoadingIndicator() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void showNotification(String message) {
        notificationText.setVisibility(View.VISIBLE);
        notificationText.setText(message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            try {
                this.mListener = (OnItemClickListener) context;
            }
            catch (final ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onAttach();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onDetach();
    }

    protected int calculateNumberOfColumns(int base){
        int columns = base;
        String screenSize = getScreenSizeCategory();

        switch (screenSize) {
            case "small":
                if (base != 1) {
                    columns = columns - 1;
                }
                break;
            case "normal":
                // Do nothing
                break;
            case "large":
                columns += 1;
                break;
            case "xlarge":
                columns += 1;
                break;
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            columns = (int) (columns * 1.5);
        }
        return columns;
    }

    protected String getScreenSizeCategory(){
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenLayout){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // small screens are at least 426dp x 320dp
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // normal screens are at least 470dp x 320dp
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                // large screens are at least 640dp x 480dp
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                // xlarge screens are at least 960dp x 720dp
                return "xlarge";
            default:
                return "undefined";
        }
    }

    public interface OnItemClickListener {
        void onClick(Movie movie);
    }

}
