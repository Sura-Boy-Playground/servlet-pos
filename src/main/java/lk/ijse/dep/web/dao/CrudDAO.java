package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO<T extends SuperEntity, K extends Serializable> extends SuperDAO {

    public void save(T entity) throws Exception;

    public void update(T entity) throws Exception;

    public void delete(K key) throws Exception;

    public List<T> getAll() throws Exception;

    public T get(K key) throws Exception;
}
