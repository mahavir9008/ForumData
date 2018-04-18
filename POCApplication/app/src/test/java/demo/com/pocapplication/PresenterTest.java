package demo.com.pocapplication;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import demo.com.pocapplication.Utility.Service;
import demo.com.pocapplication.model.RealmService;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.presenter.Presenter;
import demo.com.pocapplication.view.ViewListener;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 17)

public class PresenterTest {

    @Mock
    Comment model;
    @Mock
    Service service;
    @Mock
    ViewListener viewListener;
    @Mock
    RealmService realmService;
    @Mock
    CompositeDisposable compositeDisposable;
    private Presenter presenter;
    private Scheduler testScheduler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Mock scheduler using RxJava TestScheduler.
        testScheduler = new TestScheduler();
        presenter = new Presenter(service, viewListener, realmService, compositeDisposable);
    }

}

