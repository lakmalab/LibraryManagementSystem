package repository.custom;

import entity.BookEntity;

import java.util.List;

public interface BookRepository {
    BookEntity searchById(String id);

    BookEntity searchById(Long id);

    Boolean add(BookEntity entity);

    List<BookEntity> getAll();

    Boolean update(BookEntity entity);

    Boolean deleteById(Long bookID);
}
