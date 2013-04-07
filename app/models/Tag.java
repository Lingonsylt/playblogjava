package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Tag extends Model {
    @Id
    public String name;

    @ManyToMany
    public List<Post> posts;

    @Override
    public boolean equals(Object other) {
        if (other.getClass() == Tag.class) {
            return name.equals(((Tag)other).name);
        }  else {
            return super.equals(other);
        }
    }

    @Override
    public String toString () {
        return "Tag[" + name + "]";
    }
}
