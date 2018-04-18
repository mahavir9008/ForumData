package demo.com.pocapplication.di.scopes;

import android.app.Activity;

import javax.inject.Inject;

@PerActivity
public final class PerActivityUtil {

    private final Activity activity;

    @Inject
    PerActivityUtil(Activity activity) {
        this.activity = activity;
    }


}
