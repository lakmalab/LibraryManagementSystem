package repository.custom.impl;

import dto.BorrowRecordDto;
import entity.BorrowRecordEntity;
import org.hibernate.Session;
import repository.custom.BorrowRecordRepository;
import util.HibernateUtil;

import java.util.List;

public class BorrowRecordRepositoryImpl implements BorrowRecordRepository {


    @Override
    public BorrowRecordEntity searchById(String id) {
        return null;
    }

    @Override
    public Boolean add(BorrowRecordEntity entity) {
        Session session = HibernateUtil.getSession();
        entity.setRecordId(null);
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<BorrowRecordEntity> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM BorrowRecordEntity", BorrowRecordEntity.class).list();
        }
    }

    @Override
    public Boolean update(BorrowRecordEntity entity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Boolean deleteById(Long recordID) {
        return null;
    }


}
