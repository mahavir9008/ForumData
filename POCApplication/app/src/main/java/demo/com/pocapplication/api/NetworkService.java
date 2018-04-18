package demo.com.pocapplication.api;

import java.util.List;

import demo.com.pocapplication.Url.URL;
import demo.com.pocapplication.pojo.Comment;
import demo.com.pocapplication.pojo.Posts;
import demo.com.pocapplication.pojo.User;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * define api for network call
 */
public interface NetworkService {

    @GET(URL.APPEND_USER)
     Observable<List<User>> getUserList();

    @GET(URL.APPEND_POST)
    Observable<List<Posts>> getPostList();

    @GET(URL.APPEND_COMMENTS)
    Observable<List<Comment>> getCommentList();
}