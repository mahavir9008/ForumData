package demo.com.pocapplication.view;

import java.util.List;

import demo.com.pocapplication.model.ForumData;
import demo.com.pocapplication.pojo.Comment;


public interface ViewListener {

    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
    void getForumListSuccess(List<ForumData> forumData);
    void getCommentListData(List<Comment> comments);
    void onEmptyCommentList();
    void onOfflineDataStatus();

}