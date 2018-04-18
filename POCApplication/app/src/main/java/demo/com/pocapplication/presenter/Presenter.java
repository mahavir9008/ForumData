package demo.com.pocapplication.presenter;

import java.util.ArrayList;
import java.util.List;

import demo.com.pocapplication.Utility.NetworkError;
import demo.com.pocapplication.Utility.Service;
import demo.com.pocapplication.model.ForumData;
import demo.com.pocapplication.model.RealmService;
import demo.com.pocapplication.pojo.CombineData;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.view.ViewListener;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class Presenter {
    private final Service service;
    private final ViewListener viewListener;
    private final CompositeDisposable disposable;
    private final RealmService realmService;

    /**
     *
     * @param service - api call
     * @param viewListener - listener to update the activity
     * @param realmService - add to the database
     * @param compositeDisposable - dispose the rx thread.
     */
    public Presenter(Service service, ViewListener viewListener, RealmService realmService, CompositeDisposable compositeDisposable) {
        this.service = service;
        this.viewListener = viewListener;
        this.disposable = compositeDisposable;
        this.realmService = realmService;

    }

    /**
     * get the data form the server on worker thread and update the database.
     * update the UI on main thread
     */
    public void getUserPostCommentList() {
        viewListener.showWait();
        Disposable disposableStream = service.getforumDataList(new Service.GetForumListCallback() {
            @Override
            public void onSuccess(CombineData combineData) {
                viewListener.removeWait();
                realmService.addCommentAsync(combineData.getCommentsData());
                realmService.addPostsAsync(combineData.getForumData());
                viewListener.getForumListSuccess(combineData.getForumData());
            }

            @Override
            public void onError(NetworkError networkError) {
                viewListener.removeWait();
                viewListener.onFailure(networkError.getAppErrorMessage());

            }
        });

        disposable.add(disposableStream);

    }

    /**
     *
     * @param id - postId of the user
     *           get the comment on particular post and update the listView
     *           if no data is available show the pop-up message
     */
    public void getUserComment(Integer id) {
        List<Comment> comments = realmService.getForumCommentByUserId(id);
        if (comments.size() > 0)
            viewListener.getCommentListData(comments);
        else
            viewListener.onEmptyCommentList();
    }

    /**
     *
     * @param comment - comment data
     *                add the comment data to the database
     */
    public void addUserComment(Comment comment) {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        realmService.addCommentAsync(comments);

    }

    /**
     * check if the offline data is present & update the UI
     *
     */
    public void checkOfflineData() {
        viewListener.showWait();
        List<ForumData> forumData = realmService.getForumData();
        if (forumData.size() > 0)
            viewListener.getForumListSuccess(forumData);
        else
            viewListener.onOfflineDataStatus();

        viewListener.removeWait();
    }

    /**
     * close the database and dispose the thread on app closing.
     */
    public void onStop() {
        disposable.clear();
        realmService.closeRealm();
    }


}