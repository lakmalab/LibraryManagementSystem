package service.custom;

import dto.AdminDto;
import dto.UserDto;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface AdminService extends SuperService {
    AdminDto searchByName (String name) throws SQLException ;
}
