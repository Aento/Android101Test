package angry.dwarf.com.android101test;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import angry.dwarf.com.android101test.data.DaggerMovieRepositoryComponent;
import angry.dwarf.com.android101test.data.MovieRepositoryComponent;
import timber.log.Timber;

public class AndroidApplication  extends Application {

    private MovieRepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDependencies();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initializeDependencies() {
        repositoryComponent = DaggerMovieRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public MovieRepositoryComponent getMovieRepositoryComponent() {
        return repositoryComponent;
    }

}
