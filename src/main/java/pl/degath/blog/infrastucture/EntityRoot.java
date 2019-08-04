package pl.degath.blog.infrastucture;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class EntityRoot {

    @Id
    private final UUID id;

    protected EntityRoot() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
