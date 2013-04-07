package controllers;

import models.Comment;
import models.Post;
import models.Tag;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;

import views.html.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result viewPosts() {
        List<Post> posts = new Model.Finder(Integer.class, Post.class).all();
        return ok(viewposts.render(posts));
    }

    public static Result newPost () {
        return ok(newpost.render());
    }

    /**
     * Create a new Post and save it in DB, based on form-data
     */
    public static Result createPost() {
        //Post post = Form.form(Post.class).bindFromRequest().get();
        Post post = new Post();
        Map<String, String[]> postParams = request().body().asFormUrlEncoded();
        if (!postParams.containsKey("caption") || !postParams.containsKey("body")) {
            return badRequest("Missing mandatory fields!");
        }
        post.caption = postParams.get("caption")[0];
        post.body = postParams.get("body")[0];
        post.tags.addAll(getParsedTags(postParams));
        post.save();
        return temporaryRedirect(routes.Application.viewPosts());
    }

    /**
     * Return a list of Tags based on the "tags" parameter in the request parameters
     * Split tags based on space-characters and creates new Tags in DB if not already present (side effect)
     * Returns an empty list if no tags are found
     */
    public static List<Tag> getParsedTags (Map <String, String[]> requestParams) {
        List<Tag> tags = new LinkedList<>();
        if (requestParams.containsKey("tags")) {
            String tagStrings = requestParams.get("tags")[0];
            for (String tagString : tagStrings.split(" ")) {
                if (tagString.equals("")) {
                    continue;
                }
                Tag tag = (Tag)new Model.Finder(String.class, Tag.class).byId(tagString);
                if (tag == null) {
                    tag = new Tag();
                    tag.name = tagString;
                    tag.save();
                }
                tags.add(tag);
            }
        }
        return tags;
    }

    /**
     * Create a new Comment for a specific Post based on form-data
     */
    public static Result createComment() {
        Comment comment = Form.form(Comment.class).bindFromRequest().get();
        comment.save();
        return temporaryRedirect(routes.Application.viewPosts());
    }

}
