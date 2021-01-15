package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.SuperEntity;

import java.sql.Connection;
import java.util.List;

public interface CrudDAO<T extends SuperEntity, K> extends SuperDAO {

    public boolean save(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean delete(K key) throws Exception;

    public List<T> getAll() throws Exception;

    public T get(K key) throws Exception;
}
