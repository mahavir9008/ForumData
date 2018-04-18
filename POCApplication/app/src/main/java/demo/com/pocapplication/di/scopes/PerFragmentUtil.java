package demo.com.pocapplication.di.scopes;

import android.app.Fragment;

import javax.inject.Inject;
import javax.inject.Named;

import demo.com.pocapplication.di.baseModules.BaseFragmentModule;

@PerFragment
public final class PerFragmentUtil {

    private final Fragment fragment;

    @Inject
    PerFragmentUtil(@Named(BaseFragmentModule.FRAGMENT) Fragment fragment) {
        this.fragment = fragment;
    }
  

}