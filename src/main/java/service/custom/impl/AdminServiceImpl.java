package service.custom.impl;

import dto.AdminDto;
import dto.UserDto;
import entity.AdminEntity;
import entity.UserEntity;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import repository.custom.AdminRepository;
import repository.custom.UserRepository;
import service.custom.AdminService;
import service.custom.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    @Inject
    private AdminRepository repository;

    public AdminDto searchByName (String name) throws SQLException {
        AdminEntity entity = repository.searchByUserName(name);
        if (entity == null) {
            return null;
        }
        return new ModelMapper().map(entity, AdminDto.class);
    }

}
