package controllers;

import models.Comment;
import models.Post;
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

    public static Result createPost() {
        Post post = Form.form(Post.class).bindFromRequest().get();
        post.save();
        return redirect(routes.Application.viewPosts());
    }

    public static Result createComment() {
        Comment comment = Form.form(Comment.class).bindFromRequest().get();
        //comment.post_id
        comment.save();
        return redirect(routes.Application.viewPosts());
    }

}
