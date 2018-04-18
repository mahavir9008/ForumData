package demo.com.pocapplication.di.components;

import javax.inject.Singleton;

import dagger.Component;
import demo.com.pocapplication.baseApp.App;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(App app);
}