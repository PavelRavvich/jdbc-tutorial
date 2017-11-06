package ru.javavision.jdbc;

public interface DAO<Entity, Key> {
    Entity get(Key key);
    boolean update(Entity model);
    boolean delete(Entity model);
    boolean add(Entity model);
}
