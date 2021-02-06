package lk.ijse.dep.web.dao;

import lk.ijse.dep.web.entity.SuperEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class CrudDAOImpl<T extends SuperEntity, K extends Serializable> implements CrudDAO<T, K> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> entityClass;

    public CrudDAOImpl() {
        entityClass = (Class<T>) (((ParameterizedType)
                (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(T entity) throws Exception {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        getSession().update(entity);
    }

    @Override
    public void delete(K key) throws Exception {
        getSession().delete(getSession().load(entityClass, key));
    }

    @Override
    public List<T> getAll() throws Exception {
        return getSession().createQuery("FROM " + entityClass.getName()).list();
    }

    @Override
    public T get(K key) throws Exception {
        return getSession().get(entityClass, key);
    }

}
