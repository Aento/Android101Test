package angry.dwarf.com.android101test.ui.movie;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesPresenterModule {
    private MoviesContract.View view;

    public MoviesPresenterModule(MoviesContract.View view) {
        this.view = view;
    }

    @Provides
    public MoviesContract.View provideView() {
        return view;
    }
}

