package angry.dwarf.com.android101test.data;

import javax.inject.Singleton;

import angry.dwarf.com.android101test.AppModule;
import angry.dwarf.com.android101test.data.repository.MovieRepository;
import dagger.Component;

@Singleton
@Component(modules = { MovieRepositoryModule.class, AppModule.class, ApiServiceModule.class,
        DatabaseModule.class})
public interface MovieRepositoryComponent {
    MovieRepository provideMovieRepository();
}
