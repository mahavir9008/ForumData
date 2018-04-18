package demo.com.pocapplication.di.baseModules;

import android.app.Fragment;
import android.app.FragmentManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import demo.com.pocapplication.di.scopes.PerFragment;

@Module
public abstract class BaseFragmentModule {

    public static final String FRAGMENT = "BaseFragmentModule.fragment";

    static final String CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager";

    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @PerFragment
    static FragmentManager childFragmentManager(@Named(FRAGMENT) Fragment fragment) {
        return fragment.getChildFragmentManager();
    }
}