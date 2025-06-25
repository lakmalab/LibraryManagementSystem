package repository;

import repository.custom.impl.UserRepositoryImpl;
import util.RepositoryType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }
    public <T extends SuperRepository>T getRepositoryType(RepositoryType type){

        switch (type){
            case USER: return (T) new UserRepositoryImpl();
        }
        return null;

    }
}
