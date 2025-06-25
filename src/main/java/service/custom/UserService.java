package service.custom;

import dto.UserDto;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface UserService extends SuperService {
    Boolean addUser(UserDto User) throws SQLException;
    Boolean updateUser(UserDto User);
    UserDto searchById(String id) throws SQLException;
    List<UserDto> getAll() throws SQLException;

    List<String> getUserIds() throws SQLException;

}
