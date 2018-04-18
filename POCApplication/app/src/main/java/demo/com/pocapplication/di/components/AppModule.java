package demo.com.pocapplication.di.components;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import demo.com.pocapplication.di.scopes.PerActivity;
import demo.com.pocapplication.activity.PostDisplayActivity;
import demo.com.pocapplication.activity.PostDisplayActivityModule;

@Module(includes = AndroidInjectionModule.class)
public abstract class AppModule {

    @PerActivity
    @ContributesAndroidInjector(modules = PostDisplayActivityModule.class)
    abstract PostDisplayActivity postDisplayActivityInjector();

}