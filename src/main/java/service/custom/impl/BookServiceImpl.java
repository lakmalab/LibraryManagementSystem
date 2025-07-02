package service.custom.impl;

import dto.BookDto;
import dto.UserDto;
import entity.BookEntity;
import entity.UserEntity;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import repository.custom.BookRepository;
import repository.custom.UserRepository;
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
    public Boolean deleteById(String id) {
        return null;
    }
}
