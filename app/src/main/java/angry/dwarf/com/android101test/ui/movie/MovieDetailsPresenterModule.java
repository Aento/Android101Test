package angry.dwarf.com.android101test.ui.movie;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsPresenterModule {
    private MovieDetailsContract.View view;

    public MovieDetailsPresenterModule(MovieDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    public MovieDetailsContract.View provideView() {
        return view;
    }
}
