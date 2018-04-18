package demo.com.pocapplication.Utility;

import java.util.List;

import demo.com.pocapplication.api.NetworkService;
import demo.com.pocapplication.pojo.CombineData;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.pojo.Posts;
import demo.com.pocapplication.pojo.User;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Service {
    private final NetworkService networkService;
    private Disposable disposable;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * @param getForumListCallback - update the data to the presenter
     * @return - all forum data
     */
    public Disposable getforumDataList(final GetForumListCallback getForumListCallback) {
        Observable.just(networkService)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .flatMap(res -> {
                    Observable<List<User>> userListObservable = networkService.getUserList();
                    userListObservable.subscribeOn(Schedulers.computation());
                    Observable<List<Posts>> postsListObservable = networkService.getPostList();
                    postsListObservable.subscribeOn(Schedulers.computation());
                    Observable<List<Comment>> commentListObservable = networkService.getCommentList();
                    commentListObservable.subscribeOn(Schedulers.computation());
                    return Observable.zip(userListObservable, postsListObservable, commentListObservable, (users, posts, comments) -> {
                        return new CombineData(Util.getFilterPostData(users, posts), comments);

                    }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());


                }).subscribe(new Observer<CombineData>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;

            }

            @Override
            public void onNext(CombineData combineData) {
                getForumListCallback.onSuccess(combineData);

            }

            @Override
            public void onError(Throwable e) {
                getForumListCallback.onError(new NetworkError(e));
            }

            @Override
            public void onComplete() {
            }
        });
        return disposable;
    }

    /**
     * listener which update the data to presenter
     */
    public interface GetForumListCallback {
        void onSuccess(CombineData combineData);
        void onError(NetworkError networkError);

    }
}