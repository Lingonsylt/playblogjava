package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tag extends Model {
    @Id
    public String name;

    @OneToMany
    public Post post;
}
