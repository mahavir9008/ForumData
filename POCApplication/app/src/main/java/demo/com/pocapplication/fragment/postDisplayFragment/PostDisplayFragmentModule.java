package demo.com.pocapplication.fragment.postDisplayFragment;

import android.app.Fragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import demo.com.pocapplication.di.baseModules.BaseFragmentModule;
import demo.com.pocapplication.di.scopes.PerFragment;

@Module(includes = BaseFragmentModule.class)
public abstract class PostDisplayFragmentModule {

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    abstract Fragment fragment(PostDisplayFragment mainFragment);
}