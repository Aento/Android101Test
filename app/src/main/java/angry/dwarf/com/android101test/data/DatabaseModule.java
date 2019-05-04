package angry.dwarf.com.android101test.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import angry.dwarf.com.android101test.data.database.MovieDao;
import angry.dwarf.com.android101test.data.database.MovieDb;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
  private static final String DATABASE = "database_name";

  @Provides
  @Named(DATABASE)
  String provideDatabaseName() {
    return Config.DATABASE_NAME;
  }

  @Provides
  @Singleton
  MovieDb provideMovieDbDao(Context context, @Named(DATABASE) String databaseName) {
    return Room.databaseBuilder(context, MovieDb.class, databaseName).build();
  }

  @Provides
  @Singleton
  MovieDao provideMovieDao(MovieDb movieDb) {
    return movieDb.movieDao();
  }
}
