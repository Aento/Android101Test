package angry.dwarf.com.android101test.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import angry.dwarf.com.android101test.data.Config;
import angry.dwarf.com.android101test.data.model.Movie;
import io.reactivex.Flowable;

@Dao
public interface MovieDao {
    @Query("select * from " + Config.MOVIE_TABLE_NAME)
    Flowable<List<Movie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("DELETE FROM " + Config.MOVIE_TABLE_NAME)
    void deleteAll();
}
