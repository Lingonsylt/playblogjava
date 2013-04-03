package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: lingon
 * Date: 4/3/13
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Comment extends Model {
    @Id
    public int id;

    @Constraints.Required
    @ManyToOne
    public Post post;

    public String commenter;
    public String body;
}
