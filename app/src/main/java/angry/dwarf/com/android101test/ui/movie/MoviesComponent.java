package angry.dwarf.com.android101test.ui.movie;

import angry.dwarf.com.android101test.data.MovieRepositoryComponent;
import angry.dwarf.com.android101test.ui.base.ActivityScope;
import angry.dwarf.com.android101test.utils.SchedulerModule;
import dagger.Component;

@ActivityScope
@Component(modules = {MoviesPresenterModule.class, SchedulerModule.class}, dependencies = {
        MovieRepositoryComponent.class
})
public interface MoviesComponent {
    void inject(MoviesFragment movieListFragment);
}
