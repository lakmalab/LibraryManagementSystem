package service.custom.impl;

import dto.UserDto;
import entity.UserEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserRepository;
import service.custom.UserService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserRepository repository = DaoFactory.getInstance().getRepositoryType(RepositoryType.USER);

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
