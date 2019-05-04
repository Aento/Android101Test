package angry.dwarf.com.android101test.ui.movie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import angry.dwarf.com.android101test.R;
import angry.dwarf.com.android101test.data.model.Movie;

public class MainActivity extends FragmentActivity implements MoviesFragment.OnItemClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFragments(savedInstanceState);

    }

    private void setUpFragments(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.left_fragment, new MoviesFragment()).commit();
        }
        int i = getSupportFragmentManager().getBackStackEntryCount();
        if(getSupportFragmentManager().getBackStackEntryCount() >= 1)
            getSupportFragmentManager().popBackStack();

        if (findViewById(R.id.right_fragment) != null) {
            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.right_fragment, movieDetailsFragment).commit();
        }
    }

    @Override
    public void onClick(Movie movie) {
        if (findViewById(R.id.right_fragment) != null){
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .add(R.id.right_fragment, new MovieDetailsFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .add(R.id.left_fragment, new MovieDetailsFragment()).commit();
        }
    }
}
