package demo.com.pocapplication.activity;

import android.app.Activity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import demo.com.pocapplication.BuildConfig;
import demo.com.pocapplication.Url.URL;
import demo.com.pocapplication.Utility.Service;
import demo.com.pocapplication.api.NetworkService;
import demo.com.pocapplication.di.baseModules.BaseActivityModule;
import demo.com.pocapplication.di.scopes.PerActivity;
import demo.com.pocapplication.di.scopes.PerFragment;
import demo.com.pocapplication.fragment.commentDisplayFragment.CommentDisplayFragment;
import demo.com.pocapplication.fragment.commentDisplayFragment.CommentDisplayFragmentListener;
import demo.com.pocapplication.fragment.commentDisplayFragment.CommentDisplayFragmentModule;
import demo.com.pocapplication.fragment.postDisplayFragment.PostDisplayFragment;
import demo.com.pocapplication.fragment.postDisplayFragment.PostDisplayFragmentListener;
import demo.com.pocapplication.fragment.postDisplayFragment.PostDisplayFragmentModule;
import demo.com.pocapplication.model.RealmService;
import demo.com.pocapplication.view.ViewListener;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * describe module for dagger2
 */

@Module(includes = BaseActivityModule.class)
public abstract class PostDisplayActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = PostDisplayFragmentModule.class)
    abstract PostDisplayFragment postDisplayFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector(modules = CommentDisplayFragmentModule.class)
    abstract CommentDisplayFragment commentDisplayFragmentInjector();

    @Binds
    @PerActivity
    abstract Activity activity(PostDisplayActivity mainActivity);

    @Binds
    @PerActivity
    abstract PostDisplayFragmentListener mainFragmentListener(PostDisplayActivity postDisplayActivity);

    @Binds
    @PerActivity
    abstract ViewListener mainViewListener(PostDisplayActivity mainViewListener);

    @Binds
    @PerFragment
    abstract CommentDisplayFragmentListener commentDisplayFragmentListener(PostDisplayActivity commentDisplayFragmentListener);

    @Provides
    static Retrofit provideCall() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    // Customize the request
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                            .build();
                    okhttp3.Response response = chain.proceed(request);
                    response.cacheResponse();
                    // Customize or return the response
                    return response;
                })

                .build();


        return new Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .build();
    }

    @Provides
    @SuppressWarnings("unused")
    public static NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @SuppressWarnings("unused")
    public static Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }

    @Provides
    static Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    static RealmService provideRealmService(final Realm realm) {
        return new RealmService(realm);
    }

    @Provides
    static CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

}