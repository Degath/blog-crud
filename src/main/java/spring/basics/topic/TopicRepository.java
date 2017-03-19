package spring.basics.topic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zales on 14.03.2017.
 */
public interface TopicRepository extends CrudRepository<Topic, String> {
    Topic findById(String id);
    List<Topic> findByIdAndName(String id, String name);

    @Query("select t from Topic t where t.name like %?1")
    List<Topic> findByName(String name);

    @Query(value = "SELECT * FROM TOPICS WHERE DISCRIPTION = ?1", nativeQuery = true)
    Topic findByDiscription(String discription);
}
