package repository.custom;

import entity.BookEntity;

import java.util.List;

public interface BookRepository {
    BookEntity searchById(String id);

    Boolean add(BookEntity entity);

    List<BookEntity> getAll();
}
