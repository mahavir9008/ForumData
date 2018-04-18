package demo.com.pocapplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import demo.com.pocapplication.Utility.Util;
import demo.com.pocapplication.model.RealmService;
import demo.com.pocapplication.pojo.Comment;
import io.realm.Realm;
import io.realm.log.RealmLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 17)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmLog.class})
public class RealmUnitTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    Realm mockRealm;

    @Before
    public void setup() {
        PowerMockito.mockStatic(RealmLog.class);
        PowerMockito.mockStatic(Realm.class);
        Realm mockRealm = PowerMockito.mock(Realm.class);
        PowerMockito.when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        this.mockRealm = mockRealm;
    }

    @Test
    public void shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
    }
    @Test
    public void shouldBeAbleToMockRealmMethods() {
        PowerMockito.when(mockRealm.isEmpty()).thenReturn(true);
        assertThat(mockRealm.isEmpty(), is(true));

        PowerMockito.when(mockRealm.isAutoRefresh()).thenReturn(false);
        assertThat(mockRealm.isEmpty(), is(false));
    }


    @Test
    public void shouldBeAbleToCreateAForumObject() {
        Comment comment = new Comment();
        PowerMockito.when(mockRealm.createObject(Comment.class)).thenReturn(comment);
        Comment commentOutput = mockRealm.createObject(Comment.class);
        assertThat(commentOutput, is(comment));
    }

    /**
     * This test verifies the behavior in the {@link demo.com.pocapplication.api.NetworkService} class.
     */
    @Test
    public void shouldVerifyThatCommentWasCreated() {

        PowerMockito.doCallRealMethod().when(mockRealm).executeTransaction(Mockito.any(Realm.Transaction.class));

        Comment comment = PowerMockito.mock(Comment.class);
        PowerMockito.when(mockRealm.createObject(Comment.class)).thenReturn(comment);

        RealmService realmService = PowerMockito.mock(RealmService.class);
        List<Comment> comments = new ArrayList<>();
        comment.setId((int)UUID.randomUUID().getMostSignificantBits());
        comment.setBody("Hello, Grate Article");
        comment.setPostId(1);
        comment.setName("Mahabir");
        comment.setCreatedAt(Util.getCurrentDateTime());
        comment.setEmail("mahavir9008@gmmail.com");
        realmService.addCommentAsync(comments);

        // Verify that Realm#createObject was called only once
        Mockito.verify(mockRealm, Mockito.times(1)).createObject(Comment.class); // Verify that a Dog was in fact created.

        // Verify that Dog#setName() is called only once
        Mockito.verify(comment, Mockito.times(1)).setName(Mockito.anyString()); // Any string will do

        // Verify that the Realm was closed only once.
        Mockito.verify(mockRealm, Mockito.times(1)).close();
    }

    @Test
    public void shouldVerifyThatTransactionWasExecuted() {
        Comment comment = PowerMockito.mock(Comment.class);
        RealmService realmService = PowerMockito.mock(RealmService.class);
        List<Comment> comments = new ArrayList<>();
        comment.setId((int)UUID.randomUUID().getMostSignificantBits());
        comment.setBody("Hello, Grate Article");
        comment.setPostId(1);
        comment.setName("Mahabir");
        comment.setCreatedAt(Util.getCurrentDateTime());
        comment.setEmail("mahavir9008@gmmail.com");
        realmService.addCommentAsync(comments);
        // Verify that the begin transaction was called only once
        Mockito.verify(mockRealm, Mockito.times(1)).executeTransaction(Mockito.any(Realm.Transaction.class));

        // Verify that the Realm was closed only once.
        Mockito.verify(mockRealm, Mockito.times(1)).close();
    }


}