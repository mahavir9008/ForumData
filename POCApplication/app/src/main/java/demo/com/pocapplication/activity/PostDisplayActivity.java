package demo.com.pocapplication.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.com.pocapplication.R;
import demo.com.pocapplication.Utility.Service;
import demo.com.pocapplication.di.baseModules.BaseActivity;
import demo.com.pocapplication.fragment.commentDisplayFragment.CommentDisplayFragment;
import demo.com.pocapplication.fragment.commentDisplayFragment.CommentDisplayFragmentListener;
import demo.com.pocapplication.fragment.postDisplayFragment.PostDisplayFragment;
import demo.com.pocapplication.fragment.postDisplayFragment.PostDisplayFragmentListener;
import demo.com.pocapplication.model.ForumData;
import demo.com.pocapplication.model.RealmService;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.presenter.Presenter;
import demo.com.pocapplication.view.ViewListener;
import io.reactivex.disposables.CompositeDisposable;


public class PostDisplayActivity extends BaseActivity implements ViewListener, PostDisplayFragmentListener, CommentDisplayFragmentListener {


    @Inject
    public Service service;
    @Inject
    public RealmService realmService;
    @Inject
    public CompositeDisposable compositeDisposable;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private PostDisplayFragment postDisplayFragment;
    private CommentDisplayFragment commentDisplayFragment;
    private Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActionBar().show();
        super.onCreate(savedInstanceState);
        renderView();
        if (savedInstanceState == null) {
            postDisplayFragment = new PostDisplayFragment();
            commentDisplayFragment = new CommentDisplayFragment();
            addFragment(R.id.main_content, commentDisplayFragment);
            addFragment(R.id.main_content, postDisplayFragment);
        }
        hideFragment(commentDisplayFragment);
        presenter = new Presenter(service, this, realmService, compositeDisposable);
        presenter.getUserPostCommentList();
    }

    public void renderView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * @param appErrorMessage - notify the user internet is not available &
     *                        check the offline data
     */
    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(this, appErrorMessage, Toast.LENGTH_SHORT).show();
        presenter.checkOfflineData();
    }

    @Override
    public void getForumListSuccess(List<ForumData> forumData) {
        postDisplayFragment.addData(forumData);
    }

    @Override
    public void onItemClicked(Integer userId) {
        presenter.getUserComment(userId);

    }

    /**
     * @param comments - got data from  online or offline & display to user
     */

    @Override
    public void getCommentListData(List<Comment> comments) {
        hideFragment(postDisplayFragment);
        showFragment(commentDisplayFragment);
        commentDisplayFragment.addCommentData(comments);
    }


    @Override
    public void onBackPressed() {
        onBackPressedListener();
    }

    /**
     * handle back press according to the fragment
     * postDisplayFragment - exit from the application
     * commentDisplayFragment - show the postDisplayFragment
     */
    private void onBackPressedListener() {
        if (postDisplayFragment.isVisible()) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            super.onBackPressed();
        } else {
            hideFragment(commentDisplayFragment);
            showFragment(postDisplayFragment);
        }
    }

    /**
     * @param fragment - hide fragment
     *                 hide the fragment from the screen
     */

    private void hideFragment(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    /**
     * @param fragment - fragment instance
     *                 show the fragment to user
     */

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    /**
     * show message to user if no comment available on post
     */

    @Override
    public void onEmptyCommentList() {
        Toast.makeText(this, R.string.no_comment_list, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param comment - user comment
     *                add user comment to database.
     */
    @Override
    public void onCommentDisplayed(Comment comment) {
        presenter.addUserComment(comment);

    }

    /**
     * show message to user that offline data is unavailable
     */
    @Override
    public void onOfflineDataStatus() {
        Toast.makeText(this, R.string.offline_data, Toast.LENGTH_SHORT).show();
    }

    /**
     * show message to user to fill all the column in comment form
     */
    @Override
    public void onCommentFormEmpty() {
        Toast.makeText(this, R.string.empty_form_message, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();

    }
}
