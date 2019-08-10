package pl.degath.blog;

import pl.degath.blog.infrastucture.EntityRoot;
import pl.degath.blog.infrastucture.exception.NotImplementedException;
import pl.degath.blog.port.SpringRepository;

import java.util.*;

public class InMemorySpringRepository<T extends EntityRoot> implements SpringRepository<T> {

    private final Map<UUID, T> entities;

    public InMemorySpringRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public <S extends T> S save(S s) {
        entities.put(s.getId(), s);
        return s;
    }

    @Override
    public Optional<T> findById(UUID uuid) {
        return Optional.ofNullable(entities.get(uuid));
    }

    @Override
    public Iterable<T> findAll() {
        return List.copyOf(entities.values());
    }

    @Override
    public void deleteById(UUID uuid) {
        entities.remove(uuid);
    }

    @Override
    public void delete(T t) {
        entities.remove(t.getId());
    }

    //region yet not implemented
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public boolean existsById(UUID uuid) {
        throw new NotImplementedException();
    }


    @Override
    public Iterable<T> findAllById(Iterable<UUID> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        throw new NotImplementedException();
    }


    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException();
    }
    //endregion
}
