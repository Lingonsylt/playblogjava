import java.util.*;

import models.Post;
import models.Tag;
import org.junit.*;

import play.db.ebean.Model;
import play.mvc.*;
import play.test.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test 
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }
    
    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }

    /**
     * Test that Application.createPost returns a temporary redirect and saves a correct Post in DB
     */
    @Test
    public void testCreatePost () {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                String testCaption = "Test caption";
                String testBody = "Test body";
                String testTags = "tag1 tag2 tag3";

                Map<String, String> requestParams = new HashMap<>();
                requestParams.put("caption", testCaption);
                requestParams.put("body", testBody);
                requestParams.put("tags", testTags);

                // Create a fake request with certain request parameters
                // The target url "/" will be ignored since it's used in a callAction()
                FakeRequest request = fakeRequest(POST, "/").withFormUrlEncodedBody(requestParams);

                // Issue a callAction to the Application.createPost()-method with our requestParams,
                // generating a Response
                Result result = callAction(
                        controllers.routes.ref.Application.createPost(),
                        request
                );

                // Assert that the response is a temporary redirect
                assertThat(status(result)).isEqualTo(TEMPORARY_REDIRECT);

                // Assert that there is exactly one Post-object in the DB
                List finderResult = new Model.Finder(String.class, Post.class).all();
                assertThat(finderResult.size()).isEqualTo(1);

                // Assert that the post is saved and contains the correct caption and body
                Post post = (Post)finderResult.get(0);
                assertThat(post).isNotNull();
                assertThat(post.caption).isEqualTo(testCaption);
                assertThat(post.body).isEqualTo(testBody);

                // Assert that the posts contains the correct tags
                Tag tag1 = new Tag();
                tag1.name = "tag1";
                Tag tag2 = new Tag();
                tag2.name = "tag2";
                Tag tag3 = new Tag();
                tag3.name = "tag3";
                List<Tag> expectedTags = new LinkedList<>();
                expectedTags.add(tag1);
                expectedTags.add(tag2);
                expectedTags.add(tag3);
                assertThat(post.tags).isEqualTo(expectedTags);
            }
        });

    }

    /**
     * Test that Application.getParsedTags() behaves correctly on a number of inputs
     */
    @Test
    public void testGetParsedTags () {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Map<String, String[]> requestParams = new HashMap<>();
                requestParams.put("tags", new String[]{"tag1 tag2 tag3"});
                Tag tag1 = new Tag();
                tag1.name = "tag1";
                Tag tag2 = new Tag();
                tag2.name = "tag2";
                Tag tag3 = new Tag();
                tag3.name = "tag3";
                List<Tag> expectedTags = new LinkedList<>();
                expectedTags.add(tag1);
                expectedTags.add(tag2);
                expectedTags.add(tag3);

                // Assert that the correct flow works
                List<Tag> parsedTags = controllers.Application.getParsedTags(requestParams);
                assertThat(parsedTags).isEqualTo(expectedTags);

                // Assert that extra spaces are treated correctly
                requestParams.put("tags", new String[]{" tag1  tag2   tag3  "});
                parsedTags = controllers.Application.getParsedTags(requestParams);
                assertThat(parsedTags).isEqualTo(expectedTags);

                // Assert that another input doesn't generate the same result
                requestParams.put("tags", new String[]{"tag1tag2 tag3"});
                parsedTags = controllers.Application.getParsedTags(requestParams);
                assertThat(parsedTags).isNotEqualTo(expectedTags);

                // Assert that an empty string generates and empty list
                requestParams.put("tags", new String[]{""});
                parsedTags = controllers.Application.getParsedTags(requestParams);
                assertThat(parsedTags.size()).isEqualTo(0);

                // Assert that the number of new tags in DB is correct
                assertThat(new Model.Finder(String.class, Tag.class).all().size()).isEqualTo(4);
            }
        });
    }
  
   
}
