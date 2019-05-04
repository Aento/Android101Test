package angry.dwarf.com.android101test.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import angry.dwarf.com.android101test.data.model.Movie;

@Database(entities = Movie.class, version = 1)
public abstract class MovieDb extends RoomDatabase {

    public abstract MovieDao movieDao();
}
