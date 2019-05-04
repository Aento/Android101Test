package angry.dwarf.com.android101test.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.v4.app.Fragment;

import java.util.Objects;

import angry.dwarf.com.android101test.AndroidApplication;
import angry.dwarf.com.android101test.data.MovieRepositoryComponent;

public class BaseFragment extends Fragment {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    protected MovieRepositoryComponent getMovieRepositoryComponent() {
        return ((AndroidApplication) Objects.requireNonNull(getActivity()).getApplication())
                .getMovieRepositoryComponent();
    }

    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
