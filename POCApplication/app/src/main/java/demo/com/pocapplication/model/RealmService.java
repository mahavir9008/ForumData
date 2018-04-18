package demo.com.pocapplication.model;

import java.util.List;

import demo.com.pocapplication.pojo.Comment;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    /**
     * close the database
     */
    public void closeRealm() {
        mRealm.close();
    }

    /**
     *
     * @return - get the forum data when application is offline
     */
    public RealmResults<ForumData> getForumData() {
        return mRealm.where(ForumData.class).findAll();
    }

    /**
     *
     * @param userId - userId of the post user
     * @return - list of comment linked with post
     */
    public RealmResults<Comment> getForumCommentByUserId(Integer userId) {
        return mRealm.where(Comment.class).equalTo("postId", userId).findAll().sort("createdAt", Sort.DESCENDING);
    }

    /**
     *
     * @param forumData - forum data
     *                  add to the forum table
     */
    public void addPostsAsync(List<ForumData> forumData) {

        mRealm.executeTransactionAsync(realm -> {
            RealmList<ForumData> forumDataRealmList = new RealmList<>();
            forumDataRealmList.addAll(forumData);
            realm.insertOrUpdate(forumDataRealmList);
        });
    }

    /**
     *
     * @param comments - comment data
     *                 add to the comment table
     */
    public void addCommentAsync(List<Comment> comments) {
        mRealm.executeTransactionAsync(realm -> {
            RealmList<Comment> commentRealmList = new RealmList<>();
            commentRealmList.addAll(comments);
            realm.insertOrUpdate(commentRealmList);
        });

    }

}