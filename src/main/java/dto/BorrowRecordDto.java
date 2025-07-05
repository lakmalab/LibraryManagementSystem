package dto;

import entity.BookEntity;
import entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecordDto {
    private Integer recordId;
    private UserDto user;
    private BookDto book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private double fine;
    private boolean paidFine;
}
