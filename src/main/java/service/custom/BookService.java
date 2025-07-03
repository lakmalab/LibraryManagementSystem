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
    Boolean deleteById(Long id);

    Boolean updateUser(BookDto newUser);

    Boolean deleteBook(BookDto newBook);

    List<String> getBookNames() throws SQLException;

    void updateBookAvailability(Long bookID, boolean b);
}
