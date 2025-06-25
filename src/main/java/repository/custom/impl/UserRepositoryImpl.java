package repository.custom.impl;

import entity.UserEntity;
import repository.custom.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public boolean add(UserEntity entity) throws SQLException {
        return false;
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
    public List<UserEntity> getAll() throws SQLException {
        return List.of();
    }
}
