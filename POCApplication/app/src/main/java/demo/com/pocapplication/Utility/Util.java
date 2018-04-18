package demo.com.pocapplication.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import demo.com.pocapplication.model.ForumData;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.pojo.Posts;
import demo.com.pocapplication.pojo.User;


public class Util {
    /**
     *
     * @param users - user data
     * @param posts - post data
     * @return - combine user and post data
     */
    public static List<ForumData> getFilterPostData(List<User> users, List<Posts> posts) {
        List<ForumData> forumData = new ArrayList<>();
        for (Posts post : posts) {
            for (User person : users) {
                if (person.getId() == post.getUserId()) {
                    forumData.add(new ForumData(post.getUserId(), post.getId(), post.getTitle(), post.getBody(), person.getName()));
                }
            }
        }
        return forumData;
    }

    /**
     *
     * @param userName - user name
     * @param userEmail - user email
     * @param userComment - user comment
     * @param userId - post id
     * @return - comment object which will link to specified post id
     */
    public static Comment createCommentObject(String userName, String userEmail, String userComment, Integer userId) {
        Comment comment = new Comment();
        comment.setId((int) UUID.randomUUID().getMostSignificantBits());
        comment.setPostId(userId);
        comment.setName(userName);
        comment.setEmail(userEmail);
        comment.setBody(userComment);
        comment.setCreatedAt(getCurrentDateTime());
        return comment;
    }

    /**
     *
     * @return - get the current data and time
     */
    public static Date getCurrentDateTime() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException ignored) {

        }
        return null;
    }
}
