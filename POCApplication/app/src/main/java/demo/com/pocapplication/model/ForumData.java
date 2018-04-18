package demo.com.pocapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ForumData extends RealmObject {

    private Integer userId;

    @PrimaryKey
    private Integer id;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    private String title;

    private String body;

    private String authorName;


    public ForumData() {

    }

    public ForumData(Integer userId, Integer id, String title, String body, String authorName) {
        setUserId(userId);
        setId(id);
        setTitle(title);
        setBody(body);
        setAuthorName(authorName);
    }
}
