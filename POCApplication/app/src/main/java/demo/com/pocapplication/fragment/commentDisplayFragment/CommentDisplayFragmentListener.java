package demo.com.pocapplication.fragment.commentDisplayFragment;

import demo.com.pocapplication.pojo.Comment;



public interface CommentDisplayFragmentListener {
     void onCommentDisplayed(Comment comment);
     void onCommentFormEmpty();
}
