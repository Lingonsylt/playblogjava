package controllers;

import models.Comment;
import models.Post;
import models.Tag;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;

import views.html.*;

import java.util.List;

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

    public static Result createPost(String tags) {
        Post post = Form.form(Post.class).bindFromRequest().get();
        post.save();
        for (String tagString : tags.replace(" ", "").split(",")) {
            Tag tag = (Tag)new Model.Finder(String.class, Tag.class).byId(tagString);
            if (tag == null) {
                tag = new Tag();
            }
        }
        return redirect(routes.Application.viewPosts());
    }

    public static Result createComment() {
        Comment comment = Form.form(Comment.class).bindFromRequest().get();
        comment.save();
        return redirect(routes.Application.viewPosts());
    }

}
