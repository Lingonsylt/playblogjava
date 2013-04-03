package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Post: lingon
 * Date: 4/3/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Post extends Model {

    @Id
    public int id;

    @OneToMany
    public List<Comment> comments;

    public String caption;
    public String body;
}
