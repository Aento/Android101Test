package angry.dwarf.com.android101test.data;

import javax.inject.Singleton;

import angry.dwarf.com.android101test.data.repository.Local;
import angry.dwarf.com.android101test.data.repository.MovieDataSource;
import angry.dwarf.com.android101test.data.repository.Remote;
import angry.dwarf.com.android101test.data.repository.local.MovieLocalDataSource;
import angry.dwarf.com.android101test.data.repository.remote.MovieRemoteDataSource;
import dagger.Module;
import dagger.Provides;

@Module
public class MovieRepositoryModule {
    @Provides
    @Local
    @Singleton
    public MovieDataSource provideLocalDataSource(MovieLocalDataSource movieLocalDataSource) {
        return movieLocalDataSource;
    }

    @Provides
    @Remote
    @Singleton
    public MovieDataSource provideRemoteDataSource(MovieRemoteDataSource movieRemoteDataSource) {
        return movieRemoteDataSource;
    }

}
