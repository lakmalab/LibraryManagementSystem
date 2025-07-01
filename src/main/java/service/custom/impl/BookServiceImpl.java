package service.custom.impl;

import dto.BookDto;
import service.custom.BookService;

import java.sql.SQLException;
import java.util.List;


public class BookServiceImpl implements BookService {
    @Override
    public Boolean addBook(BookDto Book) {
        return null;
    }

    @Override
    public Boolean updateBook(BookDto Book) {
        return null;
    }

    @Override
    public BookDto searchById(String id) throws SQLException {
        return null;
    }

    @Override
    public List<BookDto> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }
}
