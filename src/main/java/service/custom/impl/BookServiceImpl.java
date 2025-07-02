package service.custom.impl;

import dto.BookDto;
import entity.BookEntity;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import repository.custom.BookRepository;
import service.custom.BookService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookServiceImpl implements BookService {
    @Inject
    private BookRepository repository;
    @Override
    public Boolean addBook(BookDto Book) {
        BookEntity entity = new ModelMapper().map(Book, BookEntity.class);
        return repository.add(entity);
    }

    @Override
    public Boolean updateBook(BookDto Book) {
        return null;
    }

    @Override
    public BookDto searchById(String id) throws SQLException {
        BookEntity entity = repository.searchById(id);
        if (entity == null) {
            return null;
        }
        BookDto bookDto = new ModelMapper().map(entity, BookDto.class);
        return bookDto;
    }

    @Override
    public List<BookDto> getAll() throws SQLException {

        List<BookEntity> all = repository.getAll();
        ArrayList<BookDto> bookDtoArrayList = new ArrayList<>();

        all.forEach(bookEntity -> {
            bookDtoArrayList.add(new ModelMapper().map(bookEntity, BookDto.class));
        });

        return bookDtoArrayList;
    }

    @Override
    public Boolean deleteById(Long id) {
        return  repository.deleteById(id);
    }

    @Override
    public Boolean updateUser(BookDto newUser) {
        BookEntity entity = new ModelMapper().map(newUser, BookEntity.class);
        return repository.update(entity);
    }

    @Override
    public Boolean deleteBook(BookDto newBook) {
        BookEntity entity = new ModelMapper().map(newBook, BookEntity.class);
        return repository.deleteById(entity.getBookID());
    }
}
