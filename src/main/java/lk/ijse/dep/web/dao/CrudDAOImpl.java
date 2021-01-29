package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class CrudDAOImpl<T extends SuperEntity, K extends Serializable> implements CrudDAO<T, K> {

    private Session session;
    private Class<T> entityClass;

    public CrudDAOImpl() {
        entityClass = (Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    protected Session getSession() {
        return this.session;
    }

    @Override
    public void setSession(Session session) throws Exception {
        this.session = session;
    }

    @Override
    public void save(T entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(K key) throws Exception {
        session.delete(session.load(entityClass, key));
    }

    @Override
    public List<T> getAll() throws Exception {
        return session.createQuery("FROM " + entityClass.getName()).list();
    }

    @Override
    public T get(K key) throws Exception {
        return session.get(entityClass, key);
    }

}
