package service.custom;

import dto.BookDto;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface BookService extends SuperService {
    Boolean addBook(BookDto Book);
    Boolean updateBook(BookDto Book);
    BookDto searchById(String id) throws SQLException;
    List<BookDto> getAll() throws SQLException;
    Boolean deleteById(String id);

}
