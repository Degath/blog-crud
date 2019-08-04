package pl.degath.blog.port;

import org.springframework.data.repository.CrudRepository;
import pl.degath.blog.infrastucture.EntityRoot;

import java.util.UUID;

public interface Repository<T extends EntityRoot> extends CrudRepository<T, UUID> {
}
