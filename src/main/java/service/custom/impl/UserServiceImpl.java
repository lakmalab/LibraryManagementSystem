package service.custom.impl;

import dto.UserDto;
import entity.UserEntity;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import repository.custom.UserRepository;
import service.custom.UserService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository repository;

    @Override
    public Boolean addUser(UserDto User) throws SQLException {
        UserEntity entity = new ModelMapper().map(User, UserEntity.class);
        return repository.add(entity);
    }

    @Override
    public Boolean updateUser(UserDto User) {
        return null;
    }

    @Override
    public UserDto searchById(String id) throws SQLException {
        UserEntity entity = repository.searchById(id);
        if (entity == null) {
            return null;
        }
        UserDto userDto = new ModelMapper().map(entity, UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() throws SQLException {
        List<UserEntity> all = repository.getAll();
        ArrayList<UserDto> userList = new ArrayList<>();

        all.forEach(userEntity -> {
            userList.add(new ModelMapper().map(userEntity, UserDto.class));
        });

        return userList;
    }

    @Override
    public List<String> getUserIds() throws SQLException {
        return List.of();
    }
}
