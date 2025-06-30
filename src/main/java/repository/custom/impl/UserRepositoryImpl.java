package repository.custom.impl;

import entity.UserEntity;
import org.hibernate.Session;
import repository.custom.UserRepository;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public boolean add(UserEntity entity) throws SQLException {
        Session session = HibernateUtil.getSession();
        entity.setUserId(null);
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(UserEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    @Override
    public UserEntity searchById(Integer integer) {
        return null;
    }
    @Override
    public UserEntity searchById(String id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM UserEntity WHERE idNumber = :id", UserEntity.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public List<UserEntity> getAll() throws SQLException {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM UserEntity", UserEntity.class).list();
        }
    }
}
