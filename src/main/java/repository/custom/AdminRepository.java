package repository.custom;

import entity.AdminEntity;
import repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity,Integer> {
    AdminEntity searchByUserName(String name);
}
