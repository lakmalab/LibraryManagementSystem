package repository.custom;

import entity.UserEntity;
import repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    UserEntity searchById(String id);
}
