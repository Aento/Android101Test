package angry.dwarf.com.android101test.ui.movie;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.security.InvalidParameterException;
import java.util.List;

import angry.dwarf.com.android101test.R;
import angry.dwarf.com.android101test.data.Config;
import angry.dwarf.com.android101test.data.model.Movie;
import angry.dwarf.com.android101test.ui.base.BaseRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

public class MovieAdapter extends BaseRecyclerViewAdapter<MovieAdapter.MovieViewHolder> {


    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_movie) ImageView movieImage;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<Movie> movies;

    public MovieAdapter(@NonNull List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / 2;
        view.setLayoutParams(lp);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MovieViewHolder vh = (MovieViewHolder)holder;
        Movie movie = movies.get(position);
        Glide.with(vh.movieImage).load(Config.IMAGE_BASE_URL + "" + movie.getPosterPath()).into(vh.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void replaceData(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public Movie getItem(int position) {
        if (position < 0 || position >= movies.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return movies.get(position);
    }

    public void clearData() {
        movies.clear();
        notifyDataSetChanged();
    }
}
