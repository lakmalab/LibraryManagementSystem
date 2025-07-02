package repository.custom.impl;

import entity.BookEntity;
import entity.UserEntity;
import org.hibernate.Session;
import repository.custom.BookRepository;
import service.custom.BookService;
import util.HibernateUtil;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    @Override
    public BookEntity searchById(String id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM BookEntity WHERE title = :id", BookEntity.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public BookEntity searchById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM BookEntity WHERE bookID = :id", BookEntity.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public Boolean add(BookEntity entity) {
        Session session = HibernateUtil.getSession();
        entity.setBookID(null);
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<BookEntity> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM BookEntity", BookEntity.class).list();
        }
    }

    @Override
    public Boolean update(BookEntity entity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return true;

    }

    @Override
    public Boolean deleteById(Long bookID) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(searchById(bookID));
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
