package demo.com.pocapplication.pojo;

import java.util.List;

import demo.com.pocapplication.model.ForumData;

/**
 * use to combine the post and comment data
 */

public class CombineData {
    public List<ForumData> getForumData() {
        return forumData;
    }

    public void setForumData(List<ForumData> forumData) {
        this.forumData = forumData;
    }

    public List<Comment> getCommentsData() {
        return commentsData;
    }

    public void setCommentsData(List<Comment> commentsData) {
        this.commentsData = commentsData;
    }

    private List<ForumData> forumData;
    private List<Comment> commentsData;

    public CombineData(List<ForumData> forumData, List<Comment> commentsData) {
        setForumData(forumData);
        setCommentsData(commentsData);

    }
}
