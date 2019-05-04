package angry.dwarf.com.android101test.utils;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static angry.dwarf.com.android101test.utils.SchedulerType.IO;
import static angry.dwarf.com.android101test.utils.SchedulerType.UI;

/**
 * Provides common Schedulers used by RxJava
 */
@Module
public class SchedulerModule {

    @Provides
    @RunOn(IO)
    Scheduler provideIo(){
        return Schedulers.io();
    }

    @Provides
    @RunOn(UI)
    Scheduler provideUi() {
        return AndroidSchedulers.mainThread();
    }
}
