package repository.custom.impl;

import entity.AdminEntity;
import entity.UserEntity;
import org.hibernate.Session;
import repository.custom.AdminRepository;
import repository.custom.UserRepository;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {

    @Override
    public AdminEntity searchByUserName(String name) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM AdminEntity WHERE userName = :name", AdminEntity.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    @Override
    public boolean add(AdminEntity entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(AdminEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }

    @Override
    public AdminEntity searchById(Integer integer) {
        return null;
    }

    @Override
    public List<AdminEntity> getAll() throws SQLException {
        return List.of();
    }
}
