package demo.com.pocapplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import demo.com.pocapplication.Utility.Service;
import demo.com.pocapplication.Utility.Util;
import demo.com.pocapplication.api.NetworkService;
import demo.com.pocapplication.model.RealmService;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.pojo.Posts;
import demo.com.pocapplication.pojo.User;
import demo.com.pocapplication.view.ViewListener;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.PublishSubject;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 17)

public class PresenterTest {


    @Mock
    Service service;
    @Mock
    ViewListener viewListener;
    @Mock
    RealmService realmService;
    @Mock
    CompositeDisposable compositeDisposable;
    @Mock
    NetworkService networkService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPublisherData() {
        TestScheduler scheduler = new TestScheduler();
        PublishSubject<Integer> ps = PublishSubject.create();
        TestObserver<Integer> ts = ps.delay(1000, TimeUnit.MILLISECONDS, scheduler)
                .test();
        ts.assertEmpty();
        ps.onNext(1);
        scheduler.advanceTimeBy(999, TimeUnit.MILLISECONDS);
        ts.assertEmpty();
        scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);
        ts.assertValue(1);
    }

    @Test
    public void verifyCommentData() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setBody("Hello, The article explained very well");
        comment.setCreatedAt(Util.getCurrentDateTime());
        comment.setName("Mr Victor");
        List<Comment> commentList = Arrays.asList(comment);
        Assert.assertEquals(Mockito.when(networkService.getCommentList()).thenReturn(Observable.just(commentList)), commentList);

    }

    @Test
    public void verifyUserData() {
        User user = new User();
        user.setName("Mahabir");
        user.setId(1);
        user.setEmail("mahavie9008@gmail.com");
        user.setPhone("1234556666");
        List<User> userList = Arrays.asList(user);
        Mockito.when(networkService.getUserList()).thenReturn(Observable.just(userList));
        Assert.assertEquals(Mockito.when(networkService.getUserList()).thenReturn(Observable.just(userList)), userList);
    }

    @Test
    public void verifyPostData() {
        Posts posts1 = new Posts();
        posts1.setBody("It was a nice post");
        posts1.setId(1);
        posts1.setUserId(1);
        posts1.setTitle("Mr Jacob coding");
        List<Posts> postList = Arrays.asList(posts1);
        Mockito.when(networkService.getPostList()).thenReturn(Observable.just(postList));
        Assert.assertEquals(Mockito.when(networkService.getPostList()).thenReturn(Observable.just(postList)), postList);

    }
}

